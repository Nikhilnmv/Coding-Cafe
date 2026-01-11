package com.smartlearn.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.smartlearn.app.R;
import com.smartlearn.app.firebase.FirebaseAuthHelper;

/**
 * SplashActivity
 * 
 * This is the first screen users see when they open the app.
 * 
 * Purpose:
 * 1. Show app logo/branding
 * 2. Check if user is already logged in
 * 3. Navigate to LoginActivity or MainActivity
 * 
 * Why Splash Screen?
 * - Professional app design
 * - Gives app time to initialize
 * - Better user experience
 */
public class SplashActivity extends AppCompatActivity {
    
    private static final int SPLASH_DELAY = 2000; // 2 seconds
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        // Check if user is logged in
        FirebaseAuthHelper authHelper = new FirebaseAuthHelper();
        
        // Handler is used to delay navigation
        // postDelayed runs code after a delay
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (authHelper.isUserLoggedIn()) {
                    // User is logged in - go to MainActivity
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    // User is not logged in - go to LoginActivity
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish(); // Close splash screen
            }
        }, SPLASH_DELAY);
    }
}
