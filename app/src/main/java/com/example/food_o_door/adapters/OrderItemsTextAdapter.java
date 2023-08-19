package com.example.food_o_door.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_o_door.R;
import com.example.food_o_door.dao.CartOffline;
import com.example.food_o_door.databinding.ItemOrdersTextBinding;
import com.example.food_o_door.Const;

import java.util.List;

public class OrderItemsTextAdapter extends RecyclerView.Adapter<OrderItemsTextAdapter.OrderItemTextViewHOlder> {


    private List<CartOffline> list;

    public OrderItemsTextAdapter(List<CartOffline> list) {

        this.list = list;
    }

    @NonNull
    @Override
    public OrderItemTextViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_text, parent, false);
        return new OrderItemTextViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemTextViewHOlder holder, int position) {

        CartOffline item = list.get(position);
        holder.binding.tvProductName.setText(item.getName());
        holder.binding.tvPrice.setText(Const.getCurrency1() + item.getPrice());
        holder.binding.tvQuantity.setText(item.getQuantity() + " Item");
        holder.binding.tvWeight.setText("("+item.getPriceUnitName()+")* "+item.getQuantity());

        Glide.with(holder.binding.getRoot().getContext())
                .load(item.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.binding.imgproduct);

//ll
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderItemTextViewHOlder extends RecyclerView.ViewHolder {
        ItemOrdersTextBinding binding;

        public OrderItemTextViewHOlder(@NonNull View itemView) {
            super(itemView);
            binding = ItemOrdersTextBinding.bind(itemView);
        }
    }
}
