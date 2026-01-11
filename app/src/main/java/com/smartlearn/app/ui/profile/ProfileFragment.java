package com.smartlearn.app.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smartlearn.app.R;
import com.smartlearn.app.firebase.FirebaseAuthHelper;
import com.smartlearn.app.firebase.FirestoreHelper;
import com.smartlearn.app.models.User;

/**
 * ProfileFragment
 * 
 * Displays user profile information.
 */
public class ProfileFragment extends Fragment {
    
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewStats;
    
    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        textViewName = view.findViewById(R.id.textViewName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewStats = view.findViewById(R.id.textViewStats);
        
        authHelper = new FirebaseAuthHelper();
        firestoreHelper = new FirestoreHelper();
        
        loadProfile();
        
        return view;
    }
    
    private void loadProfile() {
        com.google.firebase.auth.FirebaseUser firebaseUser = authHelper.getCurrentUser();
        if (firebaseUser == null) {
            return;
        }
        
        textViewEmail.setText(firebaseUser.getEmail());
        
        firestoreHelper.getUser(firebaseUser.getUid(), new FirestoreHelper.FirestoreCallback<User>() {
            @Override
            public void onSuccess(User user) {
                textViewName.setText(user.getName());
                textViewStats.setText(
                    "Lessons: " + user.getTotalLessonsCompleted() + "\n" +
                    "Sessions: " + user.getTotalFocusSessions() + "\n" +
                    "Study Time: " + user.getTotalStudyTime() + " min"
                );
            }
            
            @Override
            public void onFailure(String errorMessage) {
                textViewStats.setText("Error loading stats");
            }
        });
    }
}
