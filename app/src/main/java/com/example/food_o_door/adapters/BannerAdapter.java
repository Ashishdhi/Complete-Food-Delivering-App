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
import com.example.food_o_door.databinding.ItemBannerBinding;
import com.example.food_o_door.models.BannerModel;
import com.example.food_o_door.Const;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private List<BannerModel> banner;
    private Context context;

    public BannerAdapter(List<BannerModel> banner) {
        this.banner = banner;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        Glide.with(context).load(banner.get(position).getImage()).centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).into(holder.bannerBinding.img);
        holder.bannerBinding.img.setOnClickListener(v -> ProductDetailActivity.open(context, banner.get(position).getProductid()));


    }

    @Override
    public int getItemCount() {
        return banner.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        ItemBannerBinding bannerBinding;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerBinding = ItemBannerBinding.bind(itemView);
        }
    }
}
