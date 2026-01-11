package com.smartlearn.app.models;

/**
 * User Model Class
 * 
 * This class represents a user in our app.
 * It stores user information like name, email, and learning progress.
 * 
 * Why this class exists:
 * - To structure user data in a clean way (OOP principle: Encapsulation)
 * - To easily pass user data between activities and ViewModels
 * - To match the Firestore document structure
 */
public class User {
    private String userId;
    private String name;
    private String email;
    private int totalLessonsCompleted;
    private int totalFocusSessions;
    private long totalStudyTime; // in minutes
    
    // Default constructor (required for Firestore)
    public User() {
    }
    
    // Constructor with parameters
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.totalLessonsCompleted = 0;
        this.totalFocusSessions = 0;
        this.totalStudyTime = 0;
    }
    
    // Getters and Setters
    // These methods allow controlled access to private fields (Encapsulation)
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getTotalLessonsCompleted() {
        return totalLessonsCompleted;
    }
    
    public void setTotalLessonsCompleted(int totalLessonsCompleted) {
        this.totalLessonsCompleted = totalLessonsCompleted;
    }
    
    public int getTotalFocusSessions() {
        return totalFocusSessions;
    }
    
    public void setTotalFocusSessions(int totalFocusSessions) {
        this.totalFocusSessions = totalFocusSessions;
    }
    
    public long getTotalStudyTime() {
        return totalStudyTime;
    }
    
    public void setTotalStudyTime(long totalStudyTime) {
        this.totalStudyTime = totalStudyTime;
    }
}
