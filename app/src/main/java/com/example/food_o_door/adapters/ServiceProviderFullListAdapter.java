package com.example.food_o_door.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.food_o_door.Const;
import com.example.food_o_door.R;
import com.example.food_o_door.activites.ProductDetailActivity;
import com.example.food_o_door.dao.AppDatabase;
import com.example.food_o_door.dao.CartUtils;
import com.example.food_o_door.databinding.ItemServiceProviderFulllistBinding;
import com.example.food_o_door.models.fooditemsobject;

import java.util.List;

public class ServiceProviderFullListAdapter extends RecyclerView.Adapter<ServiceProviderFullListAdapter.ServiceProviderFullListViewHolder> {
//    private List<HomePageRoot.CategoryWithProductItem> categoryWithProduct;
    private List<fooditemsobject> ItemData;
    private Context context;
    AppDatabase db;
    private CartUtils cartUtils;

    public ServiceProviderFullListAdapter(List<fooditemsobject> homeMainModelList) {
        this.ItemData = homeMainModelList;
    }


    @NonNull
    @Override
    public ServiceProviderFullListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        db = Room.databaseBuilder(context,
                AppDatabase.class, Const.DB_NAME).allowMainThreadQueries().build();

        cartUtils = new CartUtils(context);
        return new ServiceProviderFullListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_provider_fulllist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderFullListViewHolder holder, int position) {

        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return ItemData.size();
    }

//    public void addData(List<HomePageRoot.CategoryWithProductItem> categoryWithProduct) {
//
//        this.categoryWithProduct = categoryWithProduct;
//    }

    public class ServiceProviderFullListViewHolder extends RecyclerView.ViewHolder {
        ItemServiceProviderFulllistBinding binding;
//        ProductAdapter productMainAdapter = new ProductAdapter();

        public ServiceProviderFullListViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemServiceProviderFulllistBinding.bind(itemView);
//            productMainAdapter.initAdapter(context);
        }

        public void setData(int position) {
//            HomePageRoot.CategoryWithProductItem dataItem = categoryWithProduct.get(position);
            fooditemsobject dataItem = ItemData.get(position);
            binding.textView11.setText(dataItem.getName());
            binding.textView10.setText("₹" + dataItem.getPrice());
            binding.textView50.setText(dataItem.getDescription());
            binding.textView51.setText("Qty Avl-" + dataItem.getQuantity());
            Glide.with(binding.getRoot().getContext())
                    .load(dataItem.getImage())

                    .placeholder(R.drawable.user_place_holder)
                    .error(R.drawable.user_place_holder).circleCrop()
                    //  .placeholder(R.drawable.delivery_placeholder)
                    //  .error(R.drawable.delivery_placeholder)


                    .into(binding.imgFull);
//            productMainAdapter.addData(dataItem.getProducts());
//            binding.rvProducts.setAdapter(productMainAdapter);
            binding.imgFull.setOnClickListener(v -> ProductDetailActivity.open(context,dataItem.getProductID()));
            checkCartData(dataItem, position);
            setCartData(dataItem, position);
        }

        private void setCartData(fooditemsobject product, int pos) {
            binding.tvAdd.setOnClickListener(v -> {
                cartUtils.add(product);
                long quantity = cartUtils.getCartdata(String.valueOf(product.getProductID()));
                binding.tvCount.setText(String.valueOf(quantity));
                checkCartData(product, pos);
            });
            binding.btnPlus.setOnClickListener(v -> {
                cartUtils.add(product);
                long quantity = cartUtils.getCartdata(String.valueOf(product.getProductID()));
                binding.tvCount.setText(String.valueOf(quantity));
                checkCartData(product, pos);
            });
            binding.btnMinus.setOnClickListener(v -> {
                long quantity = cartUtils.getCartdata(String.valueOf(product.getProductID()));
//                Toast.makeText(context, "quantity"+product.getProductID(), Toast.LENGTH_SHORT).show();
                if (quantity >= 1) {
                    quantity--;
                    cartUtils.less(quantity, product.getProductID());
                }
                binding.tvCount.setText(String.valueOf(quantity));
                checkCartData(product, pos);
            });
        }

        private void checkCartData(fooditemsobject product, int pos) {
            Log.d("TAG", "checkCartData: " + cartUtils.getCartdata(String.valueOf(product.getPrice())));
            if (cartUtils.getCartdata(String.valueOf(product.getProductID())) == 0) {
                binding.lytvarient.setVisibility(View.VISIBLE);
                binding.lytCount.setVisibility(View.GONE);
            } else {
                long quantity = cartUtils.getCartdata(String.valueOf(product.getProductID()));
                binding.tvCount.setText(String.valueOf(quantity));
                binding.lytvarient.setVisibility(View.GONE);
                binding.lytCount.setVisibility(View.VISIBLE);
            }

        }

    }
}
