package com.example.food_o_door.activites;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.example.food_o_door.R;
import com.example.food_o_door.databinding.ActivityAddAddressBinding;

import com.example.food_o_door.models.DeliveryAddress;

import java.util.HashMap;

public class AddAddressActivity extends BaseActivity {
    public static final int MAP_ACTIVITYCODE = 200;
    private static final String STR_ADDRESS = "address";
    ActivityAddAddressBinding binding;

//    CityAeraBottomsheet cityAeraBottomsheet;


    private boolean defaultFound;

    private DeliveryAddress address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);


        Intent intent = getIntent();
        if (intent != null) {
            defaultFound = intent.getBooleanExtra("defaultFound", true);
        }
        if (intent != null && intent.getStringExtra(STR_ADDRESS) != null && !intent.getStringExtra(STR_ADDRESS).equals("")) {
            address = new Gson().fromJson(intent.getStringExtra(STR_ADDRESS), DeliveryAddress.class);
            if (address != null) {

                binding.btnAddAddAddress.setText(R.string.updae);
                updateorAdd(true);

                setAddress(address);
            }

        }
        else {
            updateorAdd(false);

        }
        initListner();

        if (!defaultFound) {
            binding.chkAddressDefult.setChecked(true);
        }

    }

    private void updateorAdd(boolean b) {
        binding.btnAddAddAddress.setOnClickListener(v -> {
            binding.pd.setVisibility(View.VISIBLE);
            DatabaseReference rootref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address");
            String pushref = rootref.push().getKey();
            assert pushref != null;
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("address",binding.etAddressLine.getText().toString());
            hashMap.put("city",binding.etCity.getText().toString());
            hashMap.put("name",binding.etFName.getText().toString());
            hashMap.put("phoneno",binding.etMobile.getText().toString());
            hashMap.put("state",binding.etAera.getText().toString());
            hashMap.put("pincode",binding.etPin.getText().toString());
            if (binding.chkAddressDefult.isChecked()){
                hashMap.put("defaultis","yes");
            }
            else {
                hashMap.put("defaultis","no");

            }
            if (b){
                hashMap.put("addressid",address.getAddressid());
                rootref.child(address.getAddressid()).setValue(hashMap).addOnSuccessListener(unused -> {
                    binding.pd.setVisibility(View.GONE);
                    finish();
                });;
            }
            else{
                hashMap.put("addressid",pushref);
                rootref.child(pushref).setValue(hashMap).addOnSuccessListener(unused -> {
                    binding.pd.setVisibility(View.GONE);
                    finish();
                });

            }
        });

    }

    private void setAddress(DeliveryAddress address) {
        binding.etFName.setText(address.getName());
        binding.etMobile.setText(address.getPhoneno());
        binding.etAddressLine.setText(address.getAddress());
        binding.etCity.setText(address.getCity());
        binding.etAera.setText(address.getState());
        binding.etPin.setText(String.valueOf(address.getPincode()));
        if (address.getDefaultis().equals("yes")){
            binding.chkAddressDefult.setChecked(true);
        }
        else {
            binding.chkAddressDefult.setChecked(false);
        }
        selectAddressType(1);
    }





    private void initListner() {
        binding.lnrHome.setOnClickListener(v -> selectAddressType(1));
        binding.lnrWork.setOnClickListener(v -> selectAddressType(2));
        binding.lnrOther.setOnClickListener(v -> selectAddressType(3));
        binding.etAera.setOnClickListener(v -> {
        });


    }

    private void selectAddressType(int i) {

        if (i == 1) {
            binding.lnrHome.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange)));
            binding.lnrWork.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray)));
            binding.lnrOther.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray)));
            binding.ivHome.setColorFilter(ContextCompat.getColor(this, R.color.white));
            binding.ivWork.setColorFilter(ContextCompat.getColor(this, R.color.black));
            binding.tvHome.setTextColor(ContextCompat.getColor(this, R.color.white));
            binding.tvWork.setTextColor(ContextCompat.getColor(this, R.color.black));
            binding.tvOther.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
        if (i == 2) {
            binding.lnrWork.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange)));
            binding.lnrHome.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray)));
            binding.lnrOther.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray)));
            binding.ivWork.setColorFilter(ContextCompat.getColor(this, R.color.white));
            binding.ivHome.setColorFilter(ContextCompat.getColor(this, R.color.black));
            binding.tvHome.setTextColor(ContextCompat.getColor(this, R.color.black));
            binding.tvWork.setTextColor(ContextCompat.getColor(this, R.color.white));
            binding.tvOther.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
        if (i == 3) {
            binding.lnrHome.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray)));
            binding.lnrWork.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray)));
            binding.lnrOther.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange)));
            binding.ivWork.setColorFilter(ContextCompat.getColor(this, R.color.black));
            binding.ivHome.setColorFilter(ContextCompat.getColor(this, R.color.black));
            binding.tvHome.setTextColor(ContextCompat.getColor(this, R.color.black));
            binding.tvWork.setTextColor(ContextCompat.getColor(this, R.color.black));
            binding.tvOther.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }



    public void onClickBack(View v){
        onBackPressed();
    }


}