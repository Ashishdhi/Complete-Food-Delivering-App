package com.example.food_o_door.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.food_o_door.models.fooditemsobject;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.food_o_door.R;
import com.example.food_o_door.adapters.SearchAdapter;
import com.example.food_o_door.databinding.ActivitySearchBinding;
import com.example.food_o_door.databinding.BottomsheetSortingBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ActivitySearchBinding binding;

    SearchAdapter searchAdapter = new SearchAdapter();
    BottomsheetSortingBinding sortingBinding;
    private int start = 0;
    private String keyword = "";
//    private List<ProductItem> products = new ArrayList<>();
    private String categoryId;
    private String allitem;
    private String allitemis ="null";
    BottomSheetDialog bottomSheetDialog;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        sortingBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.bottomsheet_sorting, null, false);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(sortingBinding.getRoot());

        Intent intent = getIntent();
        categoryId = intent.getStringExtra("categoryselected");
        if (categoryId != null && !categoryId.isEmpty()) {
            binding.tvTitle.setText(categoryId);
            allitemis ="no";
        }
        allitem = intent.getStringExtra("allitem");
        if (allitem != null && !allitem.isEmpty() &&allitem.equals("yes")) {
            binding.tvTitle.setText("Food Items");
            allitemis="yes";
        }
        initView();

        searchAdapter.initAdapter(this);
        initListnear();
        binding.swipe.setOnRefreshListener(this);
    }

    private void initListnear() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//ll
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//ll
            }

            @Override
            public void afterTextChanged(Editable s) {
                initView();
            }
        });
        binding.btnSearch.setOnClickListener(v -> initView());

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchAdapter.notifyDataSetChanged();
    }

    private void initView() {
        start = 0;
        searchAdapter.clear();
        keyword = binding.etSearch.getText().toString();
        binding.shimmer.setVisibility(View.VISIBLE);
        if (allitemis.equals("no")){
            getSearchData();

        }
        else{
            getFooditems();
        }
        binding.rvSearch.setAdapter(searchAdapter);
    }

    @Override
    public void onRefresh() {
        initView();
    }

    private void getSearchData() {
//        binding.lyt404.setVisibility(View.VISIBLE);
        if (categoryId==null &&categoryId.isEmpty()){
            return;
        }
            FirebaseDatabase.getInstance().getReference().child("categoriesitems").child(categoryId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<fooditemsobject> products = new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        fooditemsobject categoryitem = ds.getValue(fooditemsobject.class);
                        products.add(categoryitem);
                    }

                    binding.shimmer.setVisibility(View.GONE);
                    binding.swipe.setRefreshing(false);
                    isLoading = false;
                    if (!products.isEmpty()){
                        searchAdapter.addData(products);

                    }
                    else {
                        binding.lyt404.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SearchActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            });


    }    private void getFooditems() {
//        binding.lyt404.setVisibility(View.VISIBLE);
            FirebaseDatabase.getInstance().getReference().child("fooditems").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<fooditemsobject> products = new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        fooditemsobject categoryitem = ds.getValue(fooditemsobject.class);
                        products.add(categoryitem);
                    }

                    binding.shimmer.setVisibility(View.GONE);
                    binding.swipe.setRefreshing(false);
                    isLoading = false;
                    if (!products.isEmpty()){
                        searchAdapter.addData(products);

                    }
                    else {
                        binding.lyt404.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SearchActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            });


    }
    public void onClickBack(View v){
        onBackPressed();
    }


}