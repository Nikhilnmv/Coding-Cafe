package com.smartlearn.app.models;

/**
 * Lesson Model Class
 * 
 * Represents a single lesson within a course.
 * A lesson can contain text content, video URL, or both.
 */
public class Lesson {
    private String lessonId;
    private String courseId;
    private String title;
    private String content; // Text content of the lesson
    private String videoUrl; // Optional video URL
    private int duration; // Duration in minutes
    private int order; // Order in the course (1, 2, 3...)
    private boolean isCompleted;
    
    // Default constructor (required for Firestore)
    public Lesson() {
    }
    
    public Lesson(String lessonId, String courseId, String title, String content) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.isCompleted = false;
    }
    
    // Getters and Setters
    
    public String getLessonId() {
        return lessonId;
    }
    
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
    
    public String getCourseId() {
        return courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getVideoUrl() {
        return videoUrl;
    }
    
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public int getOrder() {
        return order;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
