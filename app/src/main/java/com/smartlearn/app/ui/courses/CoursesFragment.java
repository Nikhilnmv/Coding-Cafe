package com.smartlearn.app.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartlearn.app.R;
import com.smartlearn.app.adapters.CourseAdapter;
import com.smartlearn.app.models.Course;
import com.smartlearn.app.viewmodel.CourseViewModel;

import java.util.List;

/**
 * CoursesFragment
 * 
 * Displays list of available courses.
 * Uses RecyclerView to show courses efficiently.
 */
public class CoursesFragment extends Fragment {
    
    private RecyclerView recyclerViewCourses;
    private CourseAdapter courseAdapter;
    private CourseViewModel courseViewModel;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        
        recyclerViewCourses = view.findViewById(R.id.recyclerViewCourses);
        
        // Setup RecyclerView
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        courseAdapter = new CourseAdapter();
        recyclerViewCourses.setAdapter(courseAdapter);
        
        // Setup ViewModel
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        
        // Observe courses data
        courseViewModel.getCourses().observe(getViewLifecycleOwner(), courses -> {
            if (courses != null) {
                courseAdapter.setCourses(courses);
            }
        });
        
        // Observe errors
        courseViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        
        // Load courses
        courseViewModel.loadCourses();
        
        return view;
    }
}
