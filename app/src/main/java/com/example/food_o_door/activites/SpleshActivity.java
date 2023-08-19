package com.example.food_o_door.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_o_door.R;
import com.example.food_o_door.SessionManager;
import com.example.food_o_door.Const;

public class SpleshActivity extends AppCompatActivity {
    SessionManager sessionManager;
//    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh);

        sessionManager = new SessionManager(this);

      startActivity(new Intent(this, MainActivity.class));

    }

}