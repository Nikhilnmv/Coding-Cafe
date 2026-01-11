package com.smartlearn.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.smartlearn.app.models.Course;
import com.smartlearn.app.models.Lesson;
import com.smartlearn.app.firebase.FirestoreHelper;

import java.util.List;

/**
 * CourseViewModel
 * 
 * Part of MVVM Architecture (Model-View-ViewModel)
 * 
 * What is MVVM?
 * - Model: Data (Course, Lesson classes)
 * - View: UI (Activities, Fragments)
 * - ViewModel: Business logic and data management
 * 
 * Why ViewModel?
 * - Separates UI from business logic
 * - Survives configuration changes (screen rotation)
 * - Can be tested independently
 * - Uses LiveData to notify UI of changes
 */
public class CourseViewModel extends ViewModel {
    
    private FirestoreHelper firestoreHelper;
    private MutableLiveData<List<Course>> coursesLiveData;
    private MutableLiveData<List<Lesson>> lessonsLiveData;
    private MutableLiveData<String> errorLiveData;
    
    public CourseViewModel() {
        firestoreHelper = new FirestoreHelper();
        coursesLiveData = new MutableLiveData<>();
        lessonsLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }
    
    /**
     * Get all courses
     * LiveData allows UI to observe changes automatically
     */
    public LiveData<List<Course>> getCourses() {
        return coursesLiveData;
    }
    
    /**
     * Get lessons for a course
     */
    public LiveData<List<Lesson>> getLessons() {
        return lessonsLiveData;
    }
    
    /**
     * Get error messages
     */
    public LiveData<String> getError() {
        return errorLiveData;
    }
    
    /**
     * Load all courses from Firestore
     */
    public void loadCourses() {
        firestoreHelper.getAllCourses(new FirestoreHelper.FirestoreCallback<List<Course>>() {
            @Override
            public void onSuccess(List<Course> result) {
                coursesLiveData.setValue(result);
            }
            
            @Override
            public void onFailure(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }
    
    /**
     * Load lessons for a specific course
     */
    public void loadLessons(String courseId) {
        firestoreHelper.getLessonsForCourse(courseId, new FirestoreHelper.FirestoreCallback<List<Lesson>>() {
            @Override
            public void onSuccess(List<Lesson> result) {
                lessonsLiveData.setValue(result);
            }
            
            @Override
            public void onFailure(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }
    
    /**
     * Mark a lesson as completed
     */
    public void markLessonCompleted(String userId, String lessonId) {
        firestoreHelper.markLessonCompleted(userId, lessonId, new FirestoreHelper.FirestoreCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Success - could update UI if needed
            }
            
            @Override
            public void onFailure(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }
}
