package com.example.food_o_door.activites;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.food_o_door.R;
import com.example.food_o_door.databinding.ActivityNotificationBinding;

public class NotificationActivity extends BaseActivity {
    ActivityNotificationBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        initView();
    }


    private void initView() {
        binding.tablayout.removeAllTabs();
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Notifications"));
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Orders Updates"));
        binding.tablayout.setupWithViewPager(binding.viewpager);

        binding.viewpager.setVisibility(View.VISIBLE);

    }

}