package com.smartlearn.app.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartlearn.app.R;
import com.smartlearn.app.adapters.FoodAdapter;
import com.smartlearn.app.database.AppDatabase;
import com.smartlearn.app.database.FocusSessionDao;
import com.smartlearn.app.firebase.FirebaseAuthHelper;
import com.smartlearn.app.firebase.FirestoreHelper;
import com.smartlearn.app.food.FoodItem;
import com.smartlearn.app.food.FoodRepository;
import com.smartlearn.app.models.User;

import java.util.List;

/**
 * FoodFragment
 * 
 * Module 3: Smart Food Recommendation & Ordering
 * 
 * Features:
 * - Time-based food recommendations
 * - Discount based on learning progress
 * - Mock ordering system
 */
public class FoodFragment extends Fragment {
    
    private RecyclerView recyclerViewFood;
    private FoodAdapter foodAdapter;
    private FoodRepository foodRepository;
    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        
        recyclerViewFood = view.findViewById(R.id.recyclerViewFood);
        
        // Setup RecyclerView
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext()));
        foodAdapter = new FoodAdapter();
        recyclerViewFood.setAdapter(foodAdapter);
        
        // Initialize
        foodRepository = new FoodRepository();
        authHelper = new FirebaseAuthHelper();
        firestoreHelper = new FirestoreHelper();
        
        // Load recommended food
        loadRecommendedFood();
        
        return view;
    }
    
    /**
     * Load recommended food based on user progress
     */
    private void loadRecommendedFood() {
        com.google.firebase.auth.FirebaseUser firebaseUser = authHelper.getCurrentUser();
        if (firebaseUser == null) {
            // Show all food items if not logged in
            List<FoodItem> allFood = foodRepository.getAllFoodItems();
            foodAdapter.setFoodItems(allFood);
            return;
        }
        
        // Get user progress
        firestoreHelper.getUser(firebaseUser.getUid(), new FirestoreHelper.FirestoreCallback<User>() {
            @Override
            public void onSuccess(User user) {
                // Get focus sessions count from Room database
                AppDatabase database = AppDatabase.getInstance(getContext());
                FocusSessionDao sessionDao = database.focusSessionDao();
                
                new Thread(() -> {
                    int focusSessions = sessionDao.getTotalSessions(firebaseUser.getUid());
                    
                    // Get recommended food
                    List<FoodItem> recommendedFood = foodRepository.getRecommendedFood(
                        focusSessions,
                        user.getTotalLessonsCompleted()
                    );
                    
                    // Update UI on main thread
                    requireActivity().runOnUiThread(() -> {
                        foodAdapter.setFoodItems(recommendedFood);
                    });
                }).start();
            }
            
            @Override
            public void onFailure(String errorMessage) {
                // Fallback to all food items
                List<FoodItem> allFood = foodRepository.getAllFoodItems();
                foodAdapter.setFoodItems(allFood);
            }
        });
    }
}
