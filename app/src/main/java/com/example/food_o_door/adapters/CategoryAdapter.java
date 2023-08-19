package com.example.food_o_door.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_o_door.R;
import com.example.food_o_door.activites.SearchActivity;
import com.example.food_o_door.databinding.ItemCategoriesBinding;

import com.example.food_o_door.models.CategoryModel;
import com.example.food_o_door.Const;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<CategoryModel> category;
    private Context context;

    public CategoryAdapter(List<CategoryModel> categoryMainModelList) {
        this.category = categoryMainModelList;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.binding.tvName.setText(category.get(position).getName());
        Glide.with(context).load(category.get(position).getImage()).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).into(holder.binding.imgCategory);
        holder.binding.getRoot().setOnClickListener(v -> context.startActivity(new Intent(context, SearchActivity.class).putExtra("categoryselected", category.get(position).getName())));

    }

    @Override
    public int getItemCount() {
        return category.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoriesBinding binding;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCategoriesBinding.bind(itemView);
        }
    }
}
