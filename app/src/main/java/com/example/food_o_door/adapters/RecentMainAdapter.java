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
import com.example.food_o_door.activites.ProductDetailActivity;
import com.example.food_o_door.databinding.ItemRecentOrdersBinding;
import com.example.food_o_door.models.fooditemsobject;

import java.util.List;

public class RecentMainAdapter extends RecyclerView.Adapter<RecentMainAdapter.ProductMainViewHolder> {
//    private List<HomePageRoot.CategoryWithProductItem> categoryWithProduct;
    private final List<fooditemsobject> categoryWithProduct;
    private Context context;

    public RecentMainAdapter(List<fooditemsobject> homeMainModelList) {
        this.categoryWithProduct = homeMainModelList;
    }


    @NonNull
    @Override
    public ProductMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ProductMainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_orders, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMainViewHolder holder, int position) {

        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return categoryWithProduct.size();
    }

//    public void addData(List<HomePageRoot.CategoryWithProductItem> categoryWithProduct) {
//
//        this.categoryWithProduct = categoryWithProduct;
//    }

    public class ProductMainViewHolder extends RecyclerView.ViewHolder {
        ItemRecentOrdersBinding binding;
//        ProductAdapter productMainAdapter = new ProductAdapter();

        public ProductMainViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRecentOrdersBinding.bind(itemView);
//            productMainAdapter.initAdapter(context);
        }

        public void setData(int position) {
//            HomePageRoot.CategoryWithProductItem dataItem = categoryWithProduct.get(position);
            fooditemsobject dataItem = categoryWithProduct.get(position);
            binding.tvName.setText(dataItem.getName());
            binding.tvWeight.setText(dataItem.getDescription());
            binding.tvPrice.setText(String.format(" %s", dataItem.getPrice()));
            Glide.with(context).load(dataItem.getImage()).placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(binding.imgCategory);
            if (dataItem.getCategory().equals("Non-Veg")){
                binding.nonvegrecentorder.setVisibility(View.VISIBLE);
                binding.vegrecentorder.setVisibility(View.GONE);
                binding.tvName.setTextColor(context.getResources().getColor(R.color.white));
                binding.tvWeight.setTextColor(context.getResources().getColor(R.color.white));
                binding.recentordercardlist.setCardBackgroundColor(context.getResources().getColor(R.color.dark_gray));

            }
            else {
                binding.vegrecentorder.setVisibility(View.VISIBLE);
                binding.nonvegrecentorder.setVisibility(View.GONE);
                binding.recentordercardlist.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                binding.tvName.setTextColor(context.getResources().getColor(R.color.black));
                binding.tvWeight.setTextColor(context.getResources().getColor(R.color.gray));
            }

            binding.getRoot().setOnClickListener(v -> ProductDetailActivity.open(context, dataItem.getProductID()));

//            productMainAdapter.addData(dataItem.getProducts());
//            binding.rvProducts.setAdapter(productMainAdapter);

//            binding.tvSeeAll.setOnClickListener(v -> context.startActivity(new Intent(context, SearchActivity.class).putExtra(Const.STR_CNAME, dataItem.getTitle()).putExtra(Const.STR_CID, String.valueOf(dataItem.getId()))));
        }
    }
}
