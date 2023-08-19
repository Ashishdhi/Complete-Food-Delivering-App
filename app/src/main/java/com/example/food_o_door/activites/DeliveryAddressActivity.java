package com.example.food_o_door.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.food_o_door.R;
import com.example.food_o_door.adapters.AddressAdapter;
import com.example.food_o_door.databinding.ActivityDeliveryAddressBinding;
import com.example.food_o_door.models.DeliveryAddress;
import com.example.food_o_door.popups.ConfirmationPopup;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAddressActivity extends BaseActivity {
    ActivityDeliveryAddressBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_address);
        initAddress();
        binding.swipe.setOnRefreshListener(() -> initAddress());
        binding.fabAdd.setOnClickListener(v -> startActivity(new Intent(DeliveryAddressActivity.this,AddAddressActivity.class)));
    }

    private void initAddress() {
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
                    }
                    else {
                        binding.lyt404.setVisibility(View.GONE);
                        AddressAdapter addressAdapter = new AddressAdapter(list);
                        binding.rvAddress.setAdapter(addressAdapter);
                            addressAdapter.setOnClickDeleveryAddress(datum -> new ConfirmationPopup(DeliveryAddressActivity.this, getString(R.string.deleteaddress), getString(R.string.deleteconfiraddress), new ConfirmationPopup.OnPopupClickListner() {
                                @Override
                                public void onPositive() {
                                    deleteAddress(datum,addressAdapter);
                                }


                                @Override
                                public void onNegative() {
//ll
                                }
                            }, "Yes", "No"));
                        }
                    binding.shimmer.setVisibility(View.GONE);
                    binding.swipe.setRefreshing(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DeliveryAddressActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            });

    }



    private void deleteAddress(DeliveryAddress datum, AddressAdapter addressAdapter) {
        binding.pBar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address").child(datum.getAddressid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
//                addressAdapter.delete(datum);
                binding.pBar.setVisibility(View.GONE);
                        Toast.makeText(DeliveryAddressActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void onClickBack(View v){
        onBackPressed();
    }



}