package com.example.food_o_door.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.food_o_door.R;
import com.example.food_o_door.activites.ProductDetailActivity;
import com.example.food_o_door.dao.AppDatabase;
import com.example.food_o_door.dao.CartUtils;
import com.example.food_o_door.databinding.ItemSearchBinding;
//import com.example.food_o_door.models.ProductItem;
import com.example.food_o_door.Const;
import com.example.food_o_door.models.fooditemsobject;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CartViewHolder> {
    private List<fooditemsobject> products = new ArrayList<>();
    private Context context;
    AppDatabase db;
    private CartUtils cartUtils;

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.setData(position);
    }

    public void initAdapter(Activity context) {
        this.context = context;
        db = Room.databaseBuilder(context,
                AppDatabase.class, Const.DB_NAME).allowMainThreadQueries().build();

        cartUtils = new CartUtils(context);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void addData(List<fooditemsobject> products) {
        this.products.addAll(products);
        notifyItemRangeInserted(this.products.size(), products.size());
    }

    public void clear() {
        products.clear();
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ItemSearchBinding binding;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSearchBinding.bind(itemView);
        }

        public void setData(int pos) {
            fooditemsobject product = products.get(pos);
            binding.tvName.setText(product.getName());
            binding.tvPrice.setText(Const.getCurrency1() + product.getPrice());
            binding.tvWeight.setText(product.getDescription());
            Glide.with(context).
                    load(product.getImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(binding.imgProduct);

            binding.getRoot().setOnClickListener(v -> ProductDetailActivity.open(context, String.valueOf(product.getProductID())));



        }



    }
}
