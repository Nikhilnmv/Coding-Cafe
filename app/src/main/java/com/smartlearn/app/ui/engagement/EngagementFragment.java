package com.smartlearn.app.ui.engagement;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.smartlearn.app.R;
import com.smartlearn.app.ml.EngagementAnalyzer;
import com.smartlearn.app.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * EngagementFragment
 * 
 * Module 2: AI-Assisted Engagement Analyzer
 * 
 * Features:
 * - Camera preview using CameraX
 * - ML Kit face detection
 * - Real-time engagement analysis
 * - Privacy-first (camera off by default, opt-in)
 * 
 * Privacy Rules:
 * - Camera is OFF by default
 * - User must explicitly enable
 * - No images stored
 * - All processing on-device
 */
public class EngagementFragment extends Fragment {
    
    private PreviewView previewView;
    private TextView textViewEngagement;
    private Button buttonEnableCamera;
    private Button buttonDisableCamera;
    
    private ProcessCameraProvider cameraProvider;
    private EngagementAnalyzer engagementAnalyzer;
    private ExecutorService cameraExecutor;
    private boolean isCameraEnabled = false;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_engagement, container, false);
        
        previewView = view.findViewById(R.id.previewView);
        textViewEngagement = view.findViewById(R.id.textViewEngagement);
        buttonEnableCamera = view.findViewById(R.id.buttonEnableCamera);
        buttonDisableCamera = view.findViewById(R.id.buttonDisableCamera);
        
        engagementAnalyzer = new EngagementAnalyzer();
        cameraExecutor = Executors.newSingleThreadExecutor();
        
        // Initially hide camera preview
        previewView.setVisibility(View.GONE);
        buttonDisableCamera.setEnabled(false);
        
        buttonEnableCamera.setOnClickListener(v -> {
            if (checkCameraPermission()) {
                enableCamera();
            } else {
                requestCameraPermission();
            }
        });
        
        buttonDisableCamera.setOnClickListener(v -> disableCamera());
        
        return view;
    }
    
    /**
     * Check if camera permission is granted
     */
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }
    
    /**
     * Request camera permission
     */
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            new String[]{Manifest.permission.CAMERA},
            Constants.CAMERA_PERMISSION_REQUEST_CODE
        );
    }
    
    /**
     * Enable camera and start analysis
     */
    private void enableCamera() {
        if (isCameraEnabled) {
            return;
        }
        
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = 
            ProcessCameraProvider.getInstance(requireContext());
        
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider provider = cameraProviderFuture.get();
                bindCameraUseCases(provider);
                isCameraEnabled = true;
                
                // Update UI
                previewView.setVisibility(View.VISIBLE);
                buttonEnableCamera.setEnabled(false);
                buttonDisableCamera.setEnabled(true);
                
            } catch (Exception e) {
                Toast.makeText(getContext(), "Camera error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }
    
    /**
     * Bind camera use cases (Preview + ImageAnalysis)
     */
    private void bindCameraUseCases(ProcessCameraProvider provider) {
        // Preview use case - shows camera feed
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        
        // ImageAnalysis use case - processes frames for ML Kit
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build();
        
        imageAnalysis.setAnalyzer(cameraExecutor, imageProxy -> {
            // Convert ImageProxy to InputImage for ML Kit
            InputImage image = InputImage.fromMediaImage(
                imageProxy.getImage(),
                imageProxy.getImageInfo().getRotationDegrees()
            );
            
            // Analyze engagement
            engagementAnalyzer.analyzeEngagement(image, new EngagementAnalyzer.EngagementCallback() {
                @Override
                public void onEngagementDetected(String engagementState) {
                    // Update UI on main thread
                    requireActivity().runOnUiThread(() -> {
                        textViewEngagement.setText("Status: " + engagementState);
                        
                        // Show friendly suggestions
                        String suggestion = getSuggestion(engagementState);
                        if (!suggestion.isEmpty()) {
                            Toast.makeText(getContext(), suggestion, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                
                @Override
                public void onError(String error) {
                    requireActivity().runOnUiThread(() -> {
                        textViewEngagement.setText("Error: " + error);
                    });
                }
            });
            
            imageProxy.close();
        });
        
        // Select front camera
        CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;
        
        // Unbind previous use cases and bind new ones
        provider.unbindAll();
        provider.bindToLifecycle(
            this,
            cameraSelector,
            preview,
            imageAnalysis
        );
        
        cameraProvider = provider;
    }
    
    /**
     * Disable camera
     */
    private void disableCamera() {
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
            cameraProvider = null;
        }
        
        isCameraEnabled = false;
        previewView.setVisibility(View.GONE);
        buttonEnableCamera.setEnabled(true);
        buttonDisableCamera.setEnabled(false);
        textViewEngagement.setText("Camera disabled");
    }
    
    /**
     * Get friendly suggestion based on engagement state
     */
    private String getSuggestion(String engagementState) {
        switch (engagementState) {
            case Constants.ENGAGEMENT_TIRED:
                return "You seem tired. Take a break!";
            case Constants.ENGAGEMENT_DISTRACTED:
                return "Stay focused! You can do it!";
            case Constants.ENGAGEMENT_FOCUSED:
                return ""; // No suggestion needed when focused
            default:
                return "";
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableCamera();
            } else {
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disableCamera();
        if (engagementAnalyzer != null) {
            engagementAnalyzer.release();
        }
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
    }
}
