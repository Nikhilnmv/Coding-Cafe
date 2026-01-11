package com.smartlearn.app.firebase;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.smartlearn.app.models.Course;
import com.smartlearn.app.models.Lesson;
import com.smartlearn.app.models.User;
import com.smartlearn.app.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FirestoreHelper Class
 * 
 * This class handles all Firestore database operations.
 * Firestore is Firebase's NoSQL database (like MongoDB).
 * 
 * Firestore Structure:
 * - Collections (like tables): users, courses, lessons
 * - Documents (like rows): individual user, course, lesson
 * - Fields (like columns): name, email, etc.
 * 
 * Why this class exists:
 * - Centralizes all database operations
 * - Makes it easy to change database logic later
 * - Provides clean methods for CRUD operations
 */
public class FirestoreHelper {
    
    private static final String TAG = "FirestoreHelper";
    private FirebaseFirestore firestore;
    
    // Interface for callbacks
    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(String errorMessage);
    }
    
    // Constructor
    public FirestoreHelper() {
        // Get Firestore instance (singleton)
        firestore = FirebaseFirestore.getInstance();
    }
    
    /**
     * Save or update user data in Firestore
     * 
     * @param user User object to save
     * @param callback Called when operation completes
     */
    public void saveUser(User user, FirestoreCallback<Void> callback) {
        DocumentReference userRef = firestore.collection(Constants.COLLECTION_USERS)
            .document(user.getUserId());
        
        // Convert User object to Map (Firestore format)
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", user.getUserId());
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        userData.put("totalLessonsCompleted", user.getTotalLessonsCompleted());
        userData.put("totalFocusSessions", user.getTotalFocusSessions());
        userData.put("totalStudyTime", user.getTotalStudyTime());
        
        userRef.set(userData)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "User saved successfully");
                    callback.onSuccess(null);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.e(TAG, "Error saving user", e);
                    callback.onFailure(e.getMessage());
                }
            });
    }
    
    /**
     * Get user data from Firestore
     */
    public void getUser(String userId, FirestoreCallback<User> callback) {
        firestore.collection(Constants.COLLECTION_USERS)
            .document(userId)
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Convert Firestore document to User object
                            User user = document.toObject(User.class);
                            callback.onSuccess(user);
                        } else {
                            callback.onFailure("User not found");
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                }
            });
    }
    
    /**
     * Get all courses from Firestore
     */
    public void getAllCourses(FirestoreCallback<List<Course>> callback) {
        firestore.collection(Constants.COLLECTION_COURSES)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Course> courses = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            Course course = document.toObject(Course.class);
                            if (course != null) {
                                course.setCourseId(document.getId());
                                courses.add(course);
                            }
                        }
                        callback.onSuccess(courses);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                }
            });
    }
    
    /**
     * Get lessons for a specific course
     */
    public void getLessonsForCourse(String courseId, FirestoreCallback<List<Lesson>> callback) {
        firestore.collection(Constants.COLLECTION_LESSONS)
            .whereEqualTo("courseId", courseId)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Lesson> lessons = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            Lesson lesson = document.toObject(Lesson.class);
                            if (lesson != null) {
                                lesson.setLessonId(document.getId());
                                lessons.add(lesson);
                            }
                        }
                        callback.onSuccess(lessons);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                }
            });
    }
    
    /**
     * Mark a lesson as completed
     */
    public void markLessonCompleted(String userId, String lessonId, FirestoreCallback<Void> callback) {
        Map<String, Object> progressData = new HashMap<>();
        progressData.put("userId", userId);
        progressData.put("lessonId", lessonId);
        progressData.put("completed", true);
        progressData.put("timestamp", System.currentTimeMillis());
        
        firestore.collection(Constants.COLLECTION_PROGRESS)
            .document(userId + "_" + lessonId)
            .set(progressData)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    callback.onSuccess(null);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    callback.onFailure(e.getMessage());
                }
            });
    }
}
