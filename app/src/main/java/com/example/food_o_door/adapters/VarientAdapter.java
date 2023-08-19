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

import com.example.food_o_door.Const;
import com.example.food_o_door.R;
import com.example.food_o_door.dao.AppDatabase;
import com.example.food_o_door.dao.CartUtils;
import com.example.food_o_door.databinding.ItemProductPriceBinding;
import com.example.food_o_door.models.fooditemsobject;

import java.text.DecimalFormat;
import java.util.List;

public class VarientAdapter extends RecyclerView.Adapter<VarientAdapter.VarientViewHolder> {
    private fooditemsobject product;
    Context context;
    AppDatabase db;
    private CartUtils cartUtils;


    @NonNull
    @Override
    public VarientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        db = Room.databaseBuilder(context,
                AppDatabase.class, Const.DB_NAME).allowMainThreadQueries().build();

        cartUtils = new CartUtils(context);
        return new VarientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_price, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VarientViewHolder holder, int position) {
        holder.setData(position);
    }

    public void setData( fooditemsobject product) {

        this.product = product;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class VarientViewHolder extends RecyclerView.ViewHolder {
        ItemProductPriceBinding binding;

        public VarientViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemProductPriceBinding.bind(itemView);
        }

        public void setData(int pos) {
            binding.tvProductPrice.setText(Const.getCurrency1() + product.getPrice());
            binding.tvProductweight.setText(product.getPack());
            checkCartData(product, pos);
            setCartData(product, pos);
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
                binding.tvAdd.setVisibility(View.VISIBLE);
                binding.lytCount.setVisibility(View.GONE);
            } else {
                long quantity = cartUtils.getCartdata(String.valueOf(product.getProductID()));
                binding.tvCount.setText(String.valueOf(quantity));
                binding.tvAdd.setVisibility(View.GONE);
                binding.lytCount.setVisibility(View.VISIBLE);
            }

        }

    }
}
