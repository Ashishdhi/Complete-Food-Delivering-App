package com.example.food_o_door.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_o_door.R;
import com.example.food_o_door.activites.ProductDetailActivity;
import com.example.food_o_door.databinding.ItemWishlistBinding;

import com.example.food_o_door.models.fooditemsobject;
import com.example.food_o_door.Const;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistVIewHolder> {


    private Context contex;
    private List<fooditemsobject> list;


    @NonNull
    @Override
    public WishlistVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        contex = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist, parent, false);

        return new WishlistVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistVIewHolder holder, int position) {
        holder.setWishlistData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<fooditemsobject> list) {

        this.list = list;
        notifyDataSetChanged();
    }

    OnWishlistClickListnear onWishlistClickListnear;

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public OnWishlistClickListnear getOnWishlistClickListnear() {
        return onWishlistClickListnear;
    }

    public void setOnWishlistClickListnear(OnWishlistClickListnear onWishlistClickListnear) {
        this.onWishlistClickListnear = onWishlistClickListnear;
    }

    public interface OnWishlistClickListnear {
        void onDeleteClick(fooditemsobject product, int position);
    }

    public class WishlistVIewHolder extends RecyclerView.ViewHolder {
        ItemWishlistBinding binding;

        public WishlistVIewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemWishlistBinding.bind(itemView);


        }


        public void setWishlistData(int position) {
            fooditemsobject product = list.get(position);
            binding.tvProductName.setText(product.getName());
            binding.tvProductPrice.setText(Const.getCurrency1() + product.getPrice());
//            binding.tvProductWeight.setText(product.getPrice().get(0).getUnit() + " " + product.getPrice().get(0).getUnits().getTitle());
            Glide.with(contex).load(product.getImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(binding.imgProduct);


            binding.getRoot().setOnClickListener(v -> ProductDetailActivity.open(contex, String.valueOf(product.getProductID())));
            binding.imgDelete.setOnClickListener(v -> onWishlistClickListnear.onDeleteClick(product, position));
        }
    }
}
