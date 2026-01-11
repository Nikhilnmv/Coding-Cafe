package com.smartlearn.app.food;

import java.util.ArrayList;
import java.util.List;

/**
 * FoodRepository Class
 * 
 * This class provides food data (mock implementation).
 * In a real app, this would fetch data from an API.
 * 
 * Why Repository Pattern?
 * - Separates data source from business logic
 * - Easy to switch between mock data and real API
 * - Can add caching later
 */
public class FoodRepository {
    
    /**
     * Get recommended food items based on time of day and user progress
     * 
     * Recommendation Logic:
     * - Morning (6-12): Coffee, breakfast items
     * - Afternoon (12-17): Light snacks
     * - Evening (17-22): Dinner items
     * - Night (22-6): Light snacks only
     */
    public List<FoodItem> getRecommendedFood(int focusSessions, int lessonsCompleted) {
        List<FoodItem> recommendations = new ArrayList<>();
        
        // Get time of day
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        
        // Morning recommendations
        if (hour >= 6 && hour < 12) {
            recommendations.add(createFoodItem("coffee1", "Espresso", "Strong coffee to boost focus", 3.99, "beverage"));
            recommendations.add(createFoodItem("breakfast1", "Avocado Toast", "Healthy breakfast option", 8.99, "breakfast"));
        }
        // Afternoon recommendations
        else if (hour >= 12 && hour < 17) {
            recommendations.add(createFoodItem("snack1", "Energy Bar", "Quick energy boost", 2.99, "snack"));
            recommendations.add(createFoodItem("beverage1", "Green Tea", "Calming and focused", 4.99, "beverage"));
        }
        // Evening recommendations
        else if (hour >= 17 && hour < 22) {
            recommendations.add(createFoodItem("dinner1", "Pasta Bowl", "Satisfying dinner", 12.99, "lunch"));
            recommendations.add(createFoodItem("snack2", "Fruit Bowl", "Healthy evening snack", 5.99, "snack"));
        }
        // Night recommendations
        else {
            recommendations.add(createFoodItem("snack3", "Light Snack", "Easy on the stomach", 3.99, "snack"));
        }
        
        // Apply discount based on progress
        // More focus sessions = better discount
        double discountPercent = Math.min(focusSessions * 2, 20); // Max 20% discount
        
        for (FoodItem item : recommendations) {
            double originalPrice = item.getPrice();
            double discountedPrice = originalPrice * (1 - discountPercent / 100);
            item.setPrice(discountedPrice);
        }
        
        return recommendations;
    }
    
    /**
     * Get all available food items (mock data)
     */
    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> items = new ArrayList<>();
        
        items.add(createFoodItem("coffee1", "Espresso", "Strong coffee", 3.99, "beverage"));
        items.add(createFoodItem("coffee2", "Cappuccino", "Creamy coffee", 4.99, "beverage"));
        items.add(createFoodItem("breakfast1", "Avocado Toast", "Healthy breakfast", 8.99, "breakfast"));
        items.add(createFoodItem("breakfast2", "Pancakes", "Sweet breakfast", 7.99, "breakfast"));
        items.add(createFoodItem("snack1", "Energy Bar", "Quick energy", 2.99, "snack"));
        items.add(createFoodItem("snack2", "Fruit Bowl", "Healthy snack", 5.99, "snack"));
        items.add(createFoodItem("dinner1", "Pasta Bowl", "Satisfying meal", 12.99, "lunch"));
        items.add(createFoodItem("dinner2", "Salad Bowl", "Light meal", 9.99, "lunch"));
        
        return items;
    }
    
    /**
     * Helper method to create FoodItem
     */
    private FoodItem createFoodItem(String id, String name, String description, double price, String category) {
        FoodItem item = new FoodItem();
        item.setFoodId(id);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        return item;
    }
}
