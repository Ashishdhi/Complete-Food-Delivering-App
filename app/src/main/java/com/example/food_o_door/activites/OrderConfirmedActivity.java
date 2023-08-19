package com.example.food_o_door.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.food_o_door.R;

public class OrderConfirmedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);
        String orderid = getIntent().getStringExtra("orderid");
        TextView tv = findViewById(R.id.textView7);
        tv.setText("Order Id "+orderid);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(OrderConfirmedActivity.this, MainActivity.class));
            finish();
        },5000);
    }
}