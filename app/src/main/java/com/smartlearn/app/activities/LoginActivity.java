package com.smartlearn.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartlearn.app.R;
import com.smartlearn.app.firebase.FirebaseAuthHelper;
import com.smartlearn.app.firebase.FirestoreHelper;
import com.smartlearn.app.models.User;

/**
 * LoginActivity
 * 
 * Handles user login and registration.
 * 
 * Features:
 * - Email/Password login
 * - New user registration
 * - Firebase Authentication integration
 * - Error handling
 */
public class LoginActivity extends AppCompatActivity {
    
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName; // Only shown during registration
    private Button buttonLogin;
    private Button buttonRegister;
    private TextView textViewToggle; // Toggle between login/register
    private ProgressBar progressBar;
    
    private boolean isLoginMode = true; // true = login, false = register
    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewToggle = findViewById(R.id.textViewToggle);
        progressBar = findViewById(R.id.progressBar);
        
        // Initialize helpers
        authHelper = new FirebaseAuthHelper();
        firestoreHelper = new FirestoreHelper();
        
        // Hide name field initially (login mode)
        editTextName.setVisibility(View.GONE);
        buttonRegister.setVisibility(View.GONE);
        
        // Login button click
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginMode) {
                    performLogin();
                } else {
                    switchToLoginMode();
                }
            }
        });
        
        // Register button click
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegister();
            }
        });
        
        // Toggle between login/register
        textViewToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginMode) {
                    switchToRegisterMode();
                } else {
                    switchToLoginMode();
                }
            }
        });
    }
    
    /**
     * Switch UI to login mode
     */
    private void switchToLoginMode() {
        isLoginMode = true;
        editTextName.setVisibility(View.GONE);
        buttonRegister.setVisibility(View.GONE);
        buttonLogin.setText(R.string.login);
        textViewToggle.setText("Don't have an account? Register");
    }
    
    /**
     * Switch UI to register mode
     */
    private void switchToRegisterMode() {
        isLoginMode = false;
        editTextName.setVisibility(View.VISIBLE);
        buttonRegister.setVisibility(View.VISIBLE);
        buttonLogin.setText("Back to Login");
        textViewToggle.setText("Already have an account? Login");
    }
    
    /**
     * Validate input fields
     */
    private boolean validateInput() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            return false;
        }
        
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            return false;
        }
        
        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            return false;
        }
        
        if (!isLoginMode) {
            String name = editTextName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                editTextName.setError("Name is required");
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Perform login
     */
    private void performLogin() {
        if (!validateInput()) {
            return;
        }
        
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        
        progressBar.setVisibility(View.VISIBLE);
        
        authHelper.loginUser(email, password, new FirebaseAuthHelper.AuthCallback() {
            @Override
            public void onSuccess(com.google.firebase.auth.FirebaseUser firebaseUser) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                
                // Navigate to MainActivity
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            
            @Override
            public void onFailure(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    /**
     * Perform registration
     */
    private void performRegister() {
        if (!validateInput()) {
            return;
        }
        
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        
        progressBar.setVisibility(View.VISIBLE);
        
        authHelper.registerUser(email, password, new FirebaseAuthHelper.AuthCallback() {
            @Override
            public void onSuccess(com.google.firebase.auth.FirebaseUser firebaseUser) {
                // Create user document in Firestore
                User user = new User(firebaseUser.getUid(), name, email);
                
                firestoreHelper.saveUser(user, new FirestoreHelper.FirestoreCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        
                        // Navigate to MainActivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    
                    @Override
                    public void onFailure(String errorMessage) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }
            
            @Override
            public void onFailure(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
