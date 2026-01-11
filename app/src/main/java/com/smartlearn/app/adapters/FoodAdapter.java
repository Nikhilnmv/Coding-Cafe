package com.smartlearn.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartlearn.app.R;
import com.smartlearn.app.food.FoodItem;

import java.util.ArrayList;
import java.util.List;

/**
 * FoodAdapter
 * 
 * RecyclerView Adapter for displaying food items.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    
    private List<FoodItem> foodItems;
    
    public FoodAdapter() {
        this.foodItems = new ArrayList<>();
    }
    
    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem item = foodItems.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewDescription.setText(item.getDescription());
        holder.textViewPrice.setText("$" + String.format("%.2f", item.getPrice()));
        holder.textViewCategory.setText(item.getCategory());
        
        holder.buttonOrder.setOnClickListener(v -> {
            Toast.makeText(
                v.getContext(),
                "Ordered: " + item.getName() + " (Mock Order)",
                Toast.LENGTH_SHORT
            ).show();
        });
    }
    
    @Override
    public int getItemCount() {
        return foodItems.size();
    }
    
    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewDescription;
        TextView textViewPrice;
        TextView textViewCategory;
        Button buttonOrder;
        
        FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewFoodName);
            textViewDescription = itemView.findViewById(R.id.textViewFoodDescription);
            textViewPrice = itemView.findViewById(R.id.textViewFoodPrice);
            textViewCategory = itemView.findViewById(R.id.textViewFoodCategory);
            buttonOrder = itemView.findViewById(R.id.buttonOrder);
        }
    }
}
