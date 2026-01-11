package com.smartlearn.app.food;

/**
 * FoodItem Model Class
 * 
 * Represents a food item in the ordering system.
 * Used in Module 3: Smart Food Ordering
 */
public class FoodItem {
    private String foodId;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category; // "breakfast", "lunch", "snack", "beverage"
    private int calories;
    
    // Default constructor
    public FoodItem() {
    }
    
    public FoodItem(String foodId, String name, String description, double price, String category) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
    
    // Getters and Setters
    
    public String getFoodId() {
        return foodId;
    }
    
    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getCalories() {
        return calories;
    }
    
    public void setCalories(int calories) {
        this.calories = calories;
    }
}
