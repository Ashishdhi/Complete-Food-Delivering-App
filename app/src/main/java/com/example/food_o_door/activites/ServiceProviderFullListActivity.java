package com.example.food_o_door.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.food_o_door.R;
import com.example.food_o_door.adapters.ServiceProviderFullListAdapter;
import com.example.food_o_door.databinding.ActivityServiceProviderFullListBinding;
import com.example.food_o_door.models.fooditemsobject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderFullListActivity extends AppCompatActivity {
    private String providerId = "null";
    private  String providerName, providerAddress,providerPhone;


    ActivityServiceProviderFullListBinding binding;
    private List<fooditemsobject> serviceProviderModelList = new ArrayList<>();
    private ServiceProviderFullListAdapter serviceProviderAdapter = new ServiceProviderFullListAdapter(serviceProviderModelList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_provider_full_list);
        providerId = getIntent().getStringExtra("idBusiness");
        providerName = getIntent().getStringExtra("providerName");
        providerAddress = getIntent().getStringExtra("providerAddress");
        providerPhone = getIntent().getStringExtra("providerPhone");
        getList();

    }

    private void getList() {
        FirebaseDatabase.getInstance().getReference().child("serviceproviderlist").child(providerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    fooditemsobject serviceProvider = ds.getValue(fooditemsobject.class);
                    serviceProviderModelList.add(serviceProvider);

                }
                initView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ServiceProviderFullListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });




    }

    private void initView() {
        binding.tvOrderId.setText(providerName);
        binding.textView2.setText(providerAddress);
        binding.textView3.setText(providerPhone);
        if (serviceProviderModelList != null) {
            binding.rvfull.setAdapter(serviceProviderAdapter);
        }
    }
    public void onClickBack(View v){
        onBackPressed();
    }

}