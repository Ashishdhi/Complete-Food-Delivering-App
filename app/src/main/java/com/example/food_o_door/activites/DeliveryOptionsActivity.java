package com.example.food_o_door.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.food_o_door.R;
import com.example.food_o_door.adapters.DeliveryAddressOptionsAdapter;
import com.example.food_o_door.databinding.ActivityDeliveryOptionsBinding;
import com.example.food_o_door.models.DeliveryAddress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DeliveryOptionsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    ActivityDeliveryOptionsBinding binding;


    private DeliveryAddressOptionsAdapter deliveryAddressOptionsAdapter = new DeliveryAddressOptionsAdapter();

    private String addressId;


    private DeliveryAddress addressObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_options);

        initView();

binding.swipe.setOnRefreshListener(this);
        initListnear();
    }


    private void initListnear() {
        deliveryAddressOptionsAdapter.setOnAddressSelectListnear(address -> {
            addressId = String.valueOf(address.getPincode());

            addressObj = address;
        });




    }

    private void initView() {
        binding.shimmer.setVisibility(View.VISIBLE);
        List<DeliveryAddress> list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    DeliveryAddress serviceProvider = ds.getValue(DeliveryAddress.class);
                    list.add(serviceProvider);
                }
                if (list.size()==0) {
                    binding.lyt404.setVisibility(View.VISIBLE);
                    binding.tvPay.setText("Add Delivery Address");
                    binding.tvPay.setOnClickListener(v -> {
                        startActivity(new Intent(DeliveryOptionsActivity.this, AddAddressActivity.class));
                    });

                }
                else {
                    binding.lyt404.setVisibility(View.GONE);
                    deliveryAddressOptionsAdapter.addData(list);
                    binding.rvAddress.setAdapter(deliveryAddressOptionsAdapter);
                    addressObj = list.get(0);
                    binding.tvPay.setText("Proceed To Pay");
                    binding.tvPay.setOnClickListener(v -> {
                            Intent intent = new Intent(DeliveryOptionsActivity.this, PaymentSummaryActivity.class);
                            intent.putExtra("addressId", addressId);
                            intent.putExtra("address", new Gson().toJson(addressObj));
                            startActivity(intent);
                    });
                }
                    binding.shimmer.setVisibility(View.GONE);
                binding.swipe.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeliveryOptionsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onRefresh() {
        initView();
    }

    public void onClickBack(View v){
        onBackPressed();
    }

}