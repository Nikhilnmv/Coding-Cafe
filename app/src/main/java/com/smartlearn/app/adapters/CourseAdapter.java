package com.smartlearn.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartlearn.app.R;
import com.smartlearn.app.models.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseAdapter
 * 
 * RecyclerView Adapter for displaying courses.
 * 
 * What is RecyclerView?
 * - Efficiently displays lists of items
 * - Reuses views (better performance)
 * - Standard Android component
 * 
 * What is an Adapter?
 * - Connects data (Course list) to UI (RecyclerView)
 * - Creates views for each item
 * - Binds data to views
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    
    private List<Course> courses;
    
    public CourseAdapter() {
        this.courses = new ArrayList<>();
    }
    
    /**
     * Update the list of courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged(); // Tell RecyclerView to refresh
    }
    
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view for each course item
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        // Bind course data to the view
        Course course = courses.get(position);
        holder.textViewTitle.setText(course.getTitle());
        holder.textViewDescription.setText(course.getDescription());
        holder.textViewInstructor.setText("Instructor: " + course.getInstructor());
        holder.textViewProgress.setText(
            "Progress: " + course.getCompletedLessons() + "/" + course.getTotalLessons()
        );
    }
    
    @Override
    public int getItemCount() {
        return courses.size();
    }
    
    /**
     * ViewHolder class
     * Holds references to views in each item
     * Improves performance by avoiding findViewById calls
     */
    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewInstructor;
        TextView textViewProgress;
        
        CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewCourseTitle);
            textViewDescription = itemView.findViewById(R.id.textViewCourseDescription);
            textViewInstructor = itemView.findViewById(R.id.textViewInstructor);
            textViewProgress = itemView.findViewById(R.id.textViewProgress);
        }
    }
}
