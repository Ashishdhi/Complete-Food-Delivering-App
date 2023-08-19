package com.example.food_o_door.activites;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.food_o_door.R;
import com.example.food_o_door.databinding.ActivityComplainBinding;

public class ComplainActivity extends BaseActivity {
    ActivityComplainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complain);


        initView();
    }

    private void initView() {
        binding.tablayout.removeAllTabs();
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Open"));
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Resolved"));
        binding.tablayout.setupWithViewPager(binding.viewpager);

        binding.viewpager.setVisibility(View.VISIBLE);

    }



}