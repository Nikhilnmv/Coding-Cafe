package com.smartlearn.app.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * FocusSession Model Class
 * 
 * Represents a Pomodoro focus session.
 * This class is used with Room database to store session history locally.
 * 
 * Why Room?
 * - Room is Android's recommended database library
 * - It's simple and works well with Java
 * - Data is stored locally (works offline)
 * - Perfect for logging focus sessions
 * 
 * @Entity annotation tells Room this is a database table
 */
@Entity(tableName = "focus_sessions")
public class FocusSession {
    @PrimaryKey(autoGenerate = true)
    private long id;
    
    private String userId; // Firebase user ID
    private Date startTime;
    private Date endTime;
    private long duration; // Duration in milliseconds
    private String sessionType; // "FOCUS" or "BREAK"
    private boolean isCompleted;
    
    // Default constructor
    public FocusSession() {
    }
    
    public FocusSession(String userId, Date startTime, String sessionType) {
        this.userId = userId;
        this.startTime = startTime;
        this.sessionType = sessionType;
        this.isCompleted = false;
    }
    
    // Getters and Setters
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    public String getSessionType() {
        return sessionType;
    }
    
    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
