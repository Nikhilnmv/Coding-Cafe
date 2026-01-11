package com.smartlearn.app.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.smartlearn.app.R;
import com.smartlearn.app.firebase.FirebaseAuthHelper;
import com.smartlearn.app.firebase.FirestoreHelper;
import com.smartlearn.app.models.User;
import com.smartlearn.app.viewmodel.CourseViewModel;

/**
 * DashboardFragment
 * 
 * This is the home screen showing user statistics and progress.
 * 
 * Displays:
 * - Total lessons completed
 * - Total focus sessions
 * - Total study time
 * - Recent activity
 */
public class DashboardFragment extends Fragment {
    
    private TextView textViewWelcome;
    private TextView textViewLessonsCompleted;
    private TextView textViewFocusSessions;
    private TextView textViewStudyTime;
    
    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        
        // Initialize views
        textViewWelcome = view.findViewById(R.id.textViewWelcome);
        textViewLessonsCompleted = view.findViewById(R.id.textViewLessonsCompleted);
        textViewFocusSessions = view.findViewById(R.id.textViewFocusSessions);
        textViewStudyTime = view.findViewById(R.id.textViewStudyTime);
        
        // Initialize helpers
        authHelper = new FirebaseAuthHelper();
        firestoreHelper = new FirestoreHelper();
        
        // Load user data
        loadUserData();
        
        return view;
    }
    
    /**
     * Load and display user statistics
     */
    private void loadUserData() {
        com.google.firebase.auth.FirebaseUser firebaseUser = authHelper.getCurrentUser();
        if (firebaseUser == null) {
            return;
        }
        
        String userId = firebaseUser.getUid();
        
        firestoreHelper.getUser(userId, new FirestoreHelper.FirestoreCallback<User>() {
            @Override
            public void onSuccess(User user) {
                // Update UI with user data
                textViewWelcome.setText("Welcome, " + user.getName() + "!");
                textViewLessonsCompleted.setText("Lessons Completed: " + user.getTotalLessonsCompleted());
                textViewFocusSessions.setText("Focus Sessions: " + user.getTotalFocusSessions());
                textViewStudyTime.setText("Total Study Time: " + user.getTotalStudyTime() + " minutes");
            }
            
            @Override
            public void onFailure(String errorMessage) {
                // Handle error
                textViewWelcome.setText("Welcome!");
            }
        });
    }
}
