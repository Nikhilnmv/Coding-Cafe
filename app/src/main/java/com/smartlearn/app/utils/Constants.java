package com.smartlearn.app.utils;

/**
 * Constants Class
 * 
 * Stores all constant values used throughout the app.
 * This is a best practice - keeps magic numbers and strings in one place.
 */
public class Constants {
    
    // Firebase Collections (Firestore database tables)
    public static final String COLLECTION_USERS = "users";
    public static final String COLLECTION_COURSES = "courses";
    public static final String COLLECTION_LESSONS = "lessons";
    public static final String COLLECTION_PROGRESS = "progress";
    
    // Focus Timer Constants (Pomodoro Technique)
    public static final long FOCUS_DURATION_MS = 25 * 60 * 1000; // 25 minutes in milliseconds
    public static final long BREAK_DURATION_MS = 5 * 60 * 1000; // 5 minutes in milliseconds
    
    // Session Types
    public static final String SESSION_TYPE_FOCUS = "FOCUS";
    public static final String SESSION_TYPE_BREAK = "BREAK";
    
    // Engagement States (from ML Kit analysis)
    public static final String ENGAGEMENT_FOCUSED = "FOCUSED";
    public static final String ENGAGEMENT_TIRED = "TIRED";
    public static final String ENGAGEMENT_DISTRACTED = "DISTRACTED";
    
    // Camera Permission
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    
    // SharedPreferences Keys (for storing simple data locally)
    public static final String PREF_USER_ID = "user_id";
    public static final String PREF_USER_NAME = "user_name";
    public static final String PREF_USER_EMAIL = "user_email";
}
