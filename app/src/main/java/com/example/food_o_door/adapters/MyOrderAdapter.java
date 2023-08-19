package com.example.food_o_door.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_o_door.R;
import com.example.food_o_door.SessionManager;
import com.example.food_o_door.activites.OrderDelivering;
import com.example.food_o_door.activites.OrderDetailActivity;
import com.example.food_o_door.databinding.ItemOrdersListBinding;
import com.example.food_o_door.fragments.OrderObject;


import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {

    private List<OrderObject> Orderlist;
    private Context context;

    public MyOrderAdapter(List<OrderObject> Orderlist) {
        this.Orderlist = Orderlist;
    }

//    OnOrderClickListnear onOrderClickListnear;
    SessionManager sessionManager;
//    private List<OrderDetail> data = new ArrayList<>();

//    public OnOrderClickListnear getOnOrderClickListnear() {
//        return onOrderClickListnear;
//    }
//
//    public void setOnOrderClickListnear(OnOrderClickListnear onOrderClickListnear) {
//        this.onOrderClickListnear = onOrderClickListnear;
//    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {

        holder.setOrderData(position);
    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        sessionManager = new SessionManager(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_list, parent, false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return Orderlist.size();
    }



    public class MyOrderViewHolder extends RecyclerView.ViewHolder {
        ItemOrdersListBinding binding;

        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemOrdersListBinding.bind(itemView);

        }

        public void setOrderData(int position) {
            OrderObject order = Orderlist.get(position);
            binding.tvOrderId.setText("refid"+order.getOrderid());
            binding.tvserviceProvider.setText(order.getName());
            binding.tvItemCount.setText(order.getItems());
            binding.tvItemPrice.setText(order.getPrice());
//            binding.tvDate.setText(order.getPhoneno());
            binding.tvAddress.setText(order.getOrderaddress());

            binding.getRoot().setOnClickListener(v -> context.startActivity(new Intent(context, OrderDelivering.class)));

        }
    }
}
