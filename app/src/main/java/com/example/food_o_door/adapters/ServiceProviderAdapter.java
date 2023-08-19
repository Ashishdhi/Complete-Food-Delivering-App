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
import com.example.food_o_door.activites.ServiceProviderFullListActivity;
import com.example.food_o_door.databinding.ItemServiceProviderNearbyBinding;
import com.example.food_o_door.models.ServiceProvider;


import java.util.List;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ServiceProvidedViewHolder> {
//    private List<HomePageRoot.CategoryWithProductItem> categoryWithProduct;
    private List<ServiceProvider> categoryWithProduct;
    private Context context;

    public ServiceProviderAdapter(List<ServiceProvider> homeMainModelList) {
        this.categoryWithProduct = homeMainModelList;
    }


    @NonNull
    @Override
    public ServiceProvidedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ServiceProvidedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_provider_nearby, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProvidedViewHolder holder, int position) {

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

    public class ServiceProvidedViewHolder extends RecyclerView.ViewHolder {
        ItemServiceProviderNearbyBinding binding;
//        ProductAdapter productMainAdapter = new ProductAdapter();

        public ServiceProvidedViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemServiceProviderNearbyBinding.bind(itemView);
//            productMainAdapter.initAdapter(context);
        }

        public void setData(int position) {
//            HomePageRoot.CategoryWithProductItem dataItem = categoryWithProduct.get(position);
            binding.textView11.setText(categoryWithProduct.get(position).getName());
            binding.textView6.setText(categoryWithProduct.get(position).getCategory());
            binding.textView10.setText(categoryWithProduct.get(position).getPlace());
            Glide.with(binding.getRoot().getContext())
                    .load(String.valueOf(categoryWithProduct.get(position).getImage()))

                    .placeholder(R.drawable.user_place_holder)
                    .error(R.drawable.user_place_holder).circleCrop()
                    //  .placeholder(R.drawable.delivery_placeholder)
                    //  .error(R.drawable.delivery_placeholder)


                    .into(binding.imgCategory);
//            productMainAdapter.addData(dataItem.getProducts());
//            binding.rvProducts.setAdapter(productMainAdapter);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, ServiceProviderFullListActivity.class)
                            .putExtra("idBusiness", categoryWithProduct.get(position).getProviderid())
                            .putExtra("providerName", categoryWithProduct.get(position).getName())
                            .putExtra("providerPhone", categoryWithProduct.get(position).getPhonenumber())
                            .putExtra("providerAddress", categoryWithProduct.get(position).getAddress())
                    );
                }
            });
//            binding.tvSeeAll.setOnClickListener(v -> context.startActivity(new Intent(context, SearchActivity.class).putExtra(Const.STR_CNAME, dataItem.getTitle()).putExtra(Const.STR_CID, String.valueOf(dataItem.getId()))));
        }
    }
}
