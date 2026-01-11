package com.smartlearn.app.firebase;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * FirebaseAuthHelper Class
 * 
 * This is a utility class that wraps Firebase Authentication.
 * It provides simple methods for login, register, and logout.
 * 
 * Why this class exists:
 * - Separates Firebase code from Activity code (Separation of Concerns)
 * - Makes it easier to test and maintain
 * - Provides a clean interface for authentication operations
 * 
 * Firebase Auth Explanation:
 * - Firebase Auth handles user authentication (login/register)
 * - It creates a session that persists until logout
 * - No need to manage tokens manually - Firebase does it
 */
public class FirebaseAuthHelper {
    
    private static final String TAG = "FirebaseAuthHelper";
    private FirebaseAuth firebaseAuth;
    
    // Interface for callbacks (used to notify when operations complete)
    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(String errorMessage);
    }
    
    // Constructor
    public FirebaseAuthHelper() {
        // Get Firebase Auth instance
        // This is a singleton - same instance everywhere in the app
        firebaseAuth = FirebaseAuth.getInstance();
    }
    
    /**
     * Get current logged-in user
     * @return FirebaseUser if logged in, null otherwise
     */
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
    
    /**
     * Check if user is logged in
     * @return true if user is logged in
     */
    public boolean isUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }
    
    /**
     * Register a new user with email and password
     * 
     * @param email User's email
     * @param password User's password
     * @param callback Called when registration completes (success or failure)
     */
    public void registerUser(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Log.d(TAG, "User registered: " + user.getEmail());
                        callback.onSuccess(user);
                    } else {
                        // Registration failed
                        String errorMessage = task.getException() != null 
                            ? task.getException().getMessage() 
                            : "Registration failed";
                        Log.e(TAG, "Registration failed: " + errorMessage);
                        callback.onFailure(errorMessage);
                    }
                }
            });
    }
    
    /**
     * Login existing user with email and password
     * 
     * @param email User's email
     * @param password User's password
     * @param callback Called when login completes
     */
    public void loginUser(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Log.d(TAG, "User logged in: " + user.getEmail());
                        callback.onSuccess(user);
                    } else {
                        // Login failed
                        String errorMessage = task.getException() != null 
                            ? task.getException().getMessage() 
                            : "Login failed";
                        Log.e(TAG, "Login failed: " + errorMessage);
                        callback.onFailure(errorMessage);
                    }
                }
            });
    }
    
    /**
     * Logout current user
     */
    public void logout() {
        firebaseAuth.signOut();
        Log.d(TAG, "User logged out");
    }
}
