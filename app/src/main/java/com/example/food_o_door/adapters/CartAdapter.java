package com.example.food_o_door.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.food_o_door.R;
import com.example.food_o_door.activites.ProductDetailActivity;
import com.example.food_o_door.dao.AppDatabase;
import com.example.food_o_door.dao.CartOffline;
import com.example.food_o_door.dao.CartUtils;
import com.example.food_o_door.databinding.ItemCartBinding;
import com.example.food_o_door.Const;
import com.example.food_o_door.models.fooditemsobject;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartOffline> list ;
    private Context context;
    AppDatabase db;
    private CartUtils cartUtils;

    public CartAdapter(List<CartOffline> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    public void initAdapter(Activity context) {
        this.context = context;
        db = Room.databaseBuilder(context,
                AppDatabase.class, Const.DB_NAME).allowMainThreadQueries().build();

        cartUtils = new CartUtils(context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<CartOffline> list) {

        this.list = list;
        notifyDataSetChanged();
    }

    OnCartClickListnear onCartClickListnear;

    public OnCartClickListnear getOnCartClickListnear() {
        return onCartClickListnear;
    }

    public void setOnCartClickListnear(OnCartClickListnear onCartClickListnear) {
        this.onCartClickListnear = onCartClickListnear;
    }

    public void removeItem(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public interface OnCartClickListnear {
        void onNotify();

        void onDelete(CartOffline product, int pos);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }

        public void setData(int pos) {
            CartOffline product = list.get(pos);
            binding.tvName.setText(product.getName());
            binding.tvPrice.setText(Const.getCurrency1() + product.getPrice());
            binding.tvWeight.setText(product.getPriceUnitName());
            Glide.with(context.getApplicationContext()).load(product.getImageUrl())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(binding.imgProduct);

            binding.getRoot().setOnClickListener(v -> ProductDetailActivity.open(context, String.valueOf(product.getPriceUnitId())));
            binding.tvRemove.setOnClickListener(v -> onCartClickListnear.onDelete(product, pos));
            checkCartData(product);
            setCartData(product);

        }

        private void setCartData(CartOffline product) {
            binding.tvAdd.setOnClickListener(v -> {
                cartUtils.add(product);

                long quantity = cartUtils.getCartdata(String.valueOf(product.getPriceUnitId()));
                binding.tvCount.setText(String.valueOf(quantity));
                checkCartData(product);
                onCartClickListnear.onNotify();
            });
            binding.btnPlus.setOnClickListener(v -> {
                cartUtils.add(product);
                long quantity = cartUtils.getCartdata(String.valueOf(product.getPriceUnitId()));
                binding.tvCount.setText(String.valueOf(quantity));
                checkCartData(product);
                onCartClickListnear.onNotify();
            });
            binding.btnMinus.setOnClickListener(v -> {
                long quantity = cartUtils.getCartdata(String.valueOf(product.getPriceUnitId()));
                if (quantity >= 1) {
                    quantity--;
                    cartUtils.less(quantity, product.getPriceUnitId());
                }
                binding.tvCount.setText(String.valueOf(quantity));
                checkCartData(product);
                onCartClickListnear.onNotify();
            });
        }


        private void checkCartData(CartOffline product) {
//            Log.d("TAG", "checkCartData: " + cartUtils.getCartdata(String.valueOf(product.getPriceUnitId())));
            if (cartUtils.getCartdata(String.valueOf(product.getPriceUnitId())) == 0) {
                binding.tvAdd.setVisibility(View.VISIBLE);
                binding.lytCount.setVisibility(View.GONE);
                removeItem(list.indexOf(product));
            } else {
                long quantity = cartUtils.getCartdata(String.valueOf(product.getPriceUnitId()));
                binding.tvCount.setText(String.valueOf(quantity));
                binding.tvAdd.setVisibility(View.GONE);
                binding.lytCount.setVisibility(View.VISIBLE);
            }

        }

    }
}
