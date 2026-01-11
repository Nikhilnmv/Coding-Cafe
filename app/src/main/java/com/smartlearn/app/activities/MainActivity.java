package com.smartlearn.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smartlearn.app.R;
import com.smartlearn.app.firebase.FirebaseAuthHelper;
import com.smartlearn.app.ui.dashboard.DashboardFragment;
import com.smartlearn.app.ui.courses.CoursesFragment;
import com.smartlearn.app.ui.timer.TimerFragment;
import com.smartlearn.app.ui.profile.ProfileFragment;

/**
 * MainActivity
 * 
 * This is the main screen after login.
 * It uses Bottom Navigation to switch between different sections:
 * - Dashboard (home)
 * - Courses
 * - Focus Timer
 * - Profile
 * 
 * Architecture: Uses Fragments for each section
 * Why Fragments?
 * - Reusable UI components
 * - Better organization
 * - Easier navigation
 */
public class MainActivity extends AppCompatActivity {
    
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuthHelper authHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        authHelper = new FirebaseAuthHelper();
        
        // Check if user is logged in
        if (!authHelper.isUserLoggedIn()) {
            // User not logged in - redirect to login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        // Setup bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                selectedFragment = new DashboardFragment();
            } else if (itemId == R.id.nav_courses) {
                selectedFragment = new CoursesFragment();
            } else if (itemId == R.id.nav_timer) {
                selectedFragment = new TimerFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }
            
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, selectedFragment)
                    .commit();
                return true;
            }
            return false;
        });
        
        // Load default fragment (Dashboard)
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragmentContainer, new DashboardFragment())
            .commit();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            authHelper.logout();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
