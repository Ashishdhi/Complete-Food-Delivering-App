package com.example.food_o_door.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_o_door.R;
import com.example.food_o_door.activites.SearchActivity;
import com.example.food_o_door.adapters.BannerAdapter;
import com.example.food_o_door.adapters.CategoryAdapter;
import com.example.food_o_door.adapters.DotAdapter;
import com.example.food_o_door.adapters.RecentMainAdapter;
import com.example.food_o_door.adapters.ServiceProviderAdapter;
import com.example.food_o_door.databinding.FragmentHomeBinding;
import com.example.food_o_door.models.BannerModel;
import com.example.food_o_door.models.CategoryModel;
import com.example.food_o_door.models.ServiceProvider;
import com.example.food_o_door.models.fooditemsobject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;


    DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();


    public HomeFragment() {
        // Required empty public constructor
    }

    private List<fooditemsobject> fooditemslist = new ArrayList<>();
    private RecentMainAdapter foodItemsAdapter = new RecentMainAdapter(fooditemslist);

    private List<ServiceProvider> serviceProviderModelList = new ArrayList<>();
    private ServiceProviderAdapter serviceProviderAdapter = new ServiceProviderAdapter(serviceProviderModelList);
//
//    private List<FoodMainModel> foodMainModelList = new ArrayList<>();
//    private FoodItemsAdapter foodItemsAdapter = new FoodItemsAdapter(foodMainModelList);

    private List<CategoryModel> categoryMainModelList = new ArrayList<>();
    private CategoryAdapter categoryAdapter = new CategoryAdapter(categoryMainModelList);

    private List<BannerModel> bannerMainModelList = new ArrayList<>();
    private BannerAdapter bannerAdapter = new BannerAdapter(bannerMainModelList);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.lytSearch.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)));
        checklogedin();
        getCategorylist();
        getBannerList();
        getServiceproviderList();
        getFoodItemList();
        return binding.getRoot();
    }

    private void checklogedin() {
        if (getActivity() == null) return;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser!=null){
            binding.welcomeusertv.setText(String.format("Hi, Welcome %s", currentUser.getDisplayName()));


        }
    }

    private void getBannerList() {
        rootref.child("banner").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    BannerModel bannerModel = ds.getValue(BannerModel.class);
                    bannerMainModelList.add(bannerModel);
                }
                initBannerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getCategorylist() {
        binding.scrollview.setVisibility(View.GONE);
        binding.shimmer.setVisibility(View.VISIBLE);
        rootref.child("categoryitem").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.scrollview.setVisibility(View.VISIBLE);
                binding.shimmer.setVisibility(View.GONE);
                for(DataSnapshot ds:snapshot.getChildren()){
                    CategoryModel category = ds.getValue(CategoryModel.class);
                    categoryMainModelList.add(category);
                }
                binding.rvcategorymain.setAdapter(categoryAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getFoodItemList() {
        binding.seeall.setOnClickListener(v -> getContext().startActivity(new Intent(getActivity(), SearchActivity.class).putExtra("allitem","yes")));

        rootref.child("fooditems").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){
                    fooditemsobject fooditems = ds.getValue(fooditemsobject.class);
                    fooditemslist.add(fooditems);
                }
                binding.rvfooditems.setAdapter(foodItemsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getServiceproviderList() {
        rootref.child("serviceproviders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ServiceProvider serviceProvider = ds.getValue(ServiceProvider.class);
                    serviceProviderModelList.add(serviceProvider);
                }
                binding.rvserviceprovider.setAdapter(serviceProviderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();
//        productMainAdapter.notifyDataSetChanged();
    }

    private void initBannerView() {
        if (bannerMainModelList != null) {

            binding.imageSlider.setAdapter(bannerAdapter);
            setupLogicAutoSlider();
        }
        binding.scrollview.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.canScrollVertically(1)) {
                Log.d("TAG", "onScrollChange:  yesss 1111111");
            } else {
                Log.d("TAG", "onScrollChange: nooo  11111");
            }

            if (scrollY > oldScrollY) {
                Log.d("TAG", "Scroll DOWN");

//                ((MainActivity) getActivity()).showBottomMenu(true);
            }
            if (scrollY < oldScrollY) {
                Log.d("TAG", "Scroll UP");
//                ((MainActivity) getActivity()).showBottomMenu(false);
            }
        });
    }

    private void setupLogicAutoSlider() {

        DotAdapter dotAdapter = new DotAdapter(bannerMainModelList.size());
        binding.rvdots.setAdapter(dotAdapter);
        binding.imageSlider.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager myLayoutManager = (LinearLayoutManager) binding.imageSlider.getLayoutManager();
                assert myLayoutManager != null;
                int scrollPosition = myLayoutManager.findFirstVisibleItemPosition();
                dotAdapter.changeDot(scrollPosition);
            }
        });


        final Handler handler = new Handler(Looper.myLooper());
        final Runnable runnable = new Runnable() {
            int pos = 0;
            boolean flag = true;

            @Override
            public void run() {

                if (pos == bannerAdapter.getItemCount() - 1) {
                    flag = false;
                } else if (pos == 0) {
                    flag = true;
                }
                if (flag) {
                    pos++;
                } else {
                    pos--;
                }

//                binding.imageSlider.smoothScrollToPosition(pos);
                handler.postDelayed(this, 2000);

            }
        };

        handler.postDelayed(runnable, 2000);



    }

}