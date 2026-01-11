package com.smartlearn.app;

import android.app.Application;

/**
 * SmartLearnApplication Class
 * 
 * This is the main application class that runs when the app starts.
 * 
 * Why this class exists:
 * - Initialize global resources
 * - Setup app-wide configurations
 * - Can be used to initialize Firebase (though Firebase auto-initializes)
 * 
 * Note: You need to register this in AndroidManifest.xml
 */
public class SmartLearnApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        // App initialization code can go here
    }
}
