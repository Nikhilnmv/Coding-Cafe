package com.smartlearn.app.models;

import java.util.List;

/**
 * Course Model Class
 * 
 * Represents a learning course in the app.
 * A course contains multiple lessons.
 * 
 * Example: "Java Basics" course contains lessons like:
 * - "Introduction to Java"
 * - "Variables and Data Types"
 * - "Control Structures"
 */
public class Course {
    private String courseId;
    private String title;
    private String description;
    private String instructor;
    private String imageUrl;
    private int totalLessons;
    private int completedLessons;
    private List<String> lessonIds; // IDs of lessons in this course
    
    // Default constructor (required for Firestore)
    public Course() {
    }
    
    public Course(String courseId, String title, String description, String instructor) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.totalLessons = 0;
        this.completedLessons = 0;
    }
    
    // Getters and Setters
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public int getTotalLessons() {
        return totalLessons;
    }
    
    public void setTotalLessons(int totalLessons) {
        this.totalLessons = totalLessons;
    }
    
    public int getCompletedLessons() {
        return completedLessons;
    }
    
    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = completedLessons;
    }
    
    public List<String> getLessonIds() {
        return lessonIds;
    }
    
    public void setLessonIds(List<String> lessonIds) {
        this.lessonIds = lessonIds;
    }
    
    /**
     * Calculate progress percentage
     * @return Progress as percentage (0-100)
     */
    public int getProgressPercentage() {
        if (totalLessons == 0) return 0;
        return (completedLessons * 100) / totalLessons;
    }
}
