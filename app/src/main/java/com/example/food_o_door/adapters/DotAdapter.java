package com.example.food_o_door.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_o_door.R;
import com.example.food_o_door.databinding.ItemDotsBinding;

import java.util.List;

public class DotAdapter extends RecyclerView.Adapter<DotAdapter.DotViewHolder> {
    private Context context;
    private int seleccted = 0;
    List<String> productImage;
    private int size;

    public DotAdapter(int size) {


        this.size = size;
    }

    @NonNull
    @Override
    public DotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new DotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dots, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DotViewHolder holder, int position) {

        if (seleccted == position) {
            holder.binding.imgdot.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.orange));
        } else {
            holder.binding.imgdot.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color_gray_lightbtn));
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public void changeDot(int scrollPosition) {
        seleccted = scrollPosition;
        notifyDataSetChanged();
    }

    public class DotViewHolder extends RecyclerView.ViewHolder {
        ItemDotsBinding binding;

        public DotViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDotsBinding.bind(itemView);
        }
    }
}
