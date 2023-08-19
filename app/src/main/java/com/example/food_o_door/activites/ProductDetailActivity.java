package com.example.food_o_door.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.food_o_door.R;
import com.example.food_o_door.adapters.DotAdapter;
import com.example.food_o_door.adapters.ImageAdapter;
import com.example.food_o_door.adapters.VarientAdapter;
import com.example.food_o_door.models.fooditemsobject;
import com.example.food_o_door.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductDetailActivity extends BaseActivity {
    com.example.food_o_door.databinding.ActivityProductDetailBinding binding;
    private fooditemsobject product;
    private final List<fooditemsobject> datalist = new ArrayList<>();
    VarientAdapter varientAdapter = new VarientAdapter();

    public static void open(Context context, String pid) {
        context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra(Const.P_ID, pid));
    }

    private void getProductData(String productId) {
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.lyt1.setVisibility(View.GONE);
        FirebaseDatabase.getInstance().getReference().child("fooditems").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.add(snapshot.getValue(fooditemsobject.class));
                product = snapshot.getValue(fooditemsobject.class);
                initView();
                binding.shimmer.setVisibility(View.GONE);
                binding.lyt1.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                binding.shimmer.setVisibility(View.GONE);
                binding.lyt1.setVisibility(View.VISIBLE);
            }
        });





    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);

        Intent intent = getIntent();
        String productId = intent.getStringExtra(Const.P_ID);
        if (productId != null && !productId.isEmpty()) {
            getProductData(productId);
        }
        binding.lytGotoCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        varientAdapter.notifyDataSetChanged();
    }

    private void initView() {
        List<String> imageuriList = new ArrayList<>();
        imageuriList.add(product.getImage());
        DotAdapter dotAdapter = new DotAdapter(imageuriList.size());
        binding.rvdots.setAdapter(dotAdapter);
        binding.rvphotos.setAdapter(new ImageAdapter(imageuriList));
        varientAdapter.setData(datalist.get(0));
        binding.rvProductOption.setAdapter(varientAdapter);

        binding.tvName.setText(product.getName());
        binding.tvQty.setText(String.valueOf(product.getQuantity()));
        binding.priceTV.setText(String.valueOf(product.getPrice()));
        binding.tvCat.setText(product.getCategory());
        binding.DescTV.setText(product.getFulldescription());

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvphotos);

        binding.rvphotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager myLayoutManager = (LinearLayoutManager) binding.rvphotos.getLayoutManager();
                int scrollPosition = myLayoutManager.findFirstVisibleItemPosition();
                dotAdapter.changeDot(scrollPosition);
            }
        });


        initWishlistLogic();
        binding.lytAddwishlist.setOnClickListener(v -> {
//            sessionManager.toggleWishlist(datalist.get(0));
            sessionManager.toggleWishlist(product);
            initWishlistLogic();
        });


        binding.tvReadmore.setVisibility(View.VISIBLE);
        binding.DescTV.setMaxLines(3);

        binding.tvReadmore.setOnClickListener(v -> {


            if (binding.DescTV.getMaxLines() == 3) {
                binding.tvReadmore.setText(getString(R.string.readless));
                binding.DescTV.setMaxLines(20000);
            } else {
                binding.tvReadmore.setText(getString(R.string.read_more));
                binding.DescTV.setMaxLines(3);
            }

        });


    }

    private void initWishlistLogic() {
        List<fooditemsobject> list = sessionManager.getWishlist();
        binding.tvAddremoveToWishlist.setText("Add To Wishlist");
        binding.imgwishlist.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_heartborder));
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(list.get(i).getProductID(), product.getProductID())) {
                binding.tvAddremoveToWishlist.setText("Remove");
                binding.imgwishlist.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_heart));
            }
        }


    }
    public void onClickBack(View v){
        onBackPressed();
    }


}