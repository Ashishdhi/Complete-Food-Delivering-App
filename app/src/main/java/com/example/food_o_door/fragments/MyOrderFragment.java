package com.example.food_o_door.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.food_o_door.Const;
import com.example.food_o_door.R;
import com.example.food_o_door.activites.BaseActivity;
import com.example.food_o_door.adapters.MyOrderAdapter;
import com.example.food_o_door.databinding.FragmentOrdersBinding;
import com.example.food_o_door.interfaces.LoginListnraer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyOrderFragment extends BaseFragment {

    FragmentOrdersBinding binding;



    public MyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.swipe.setOnRefreshListener(this::onRefresh);
        checkLogedin();
        return binding.getRoot();
    }

    private void checkLogedin() {
        if (sessionManager.getBooleanValue(Const.IS_LOGIN)) {
            fetchOrderData();
        } else {
            binding.lyt404.setVisibility(View.VISIBLE);
            if (getActivity() != null) {

                ((BaseActivity) getActivity()).openLoginSheet(new LoginListnraer() {
                    @Override
                    public void onLoginSuccess() {
                        checkLogedin();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(context, "Login Failure", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onDismiss() {
                        Toast.makeText(context, "Login First", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

    }

    private void fetchOrderData() {
            List<OrderObject> list = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        OrderObject orderObject = ds.getValue(OrderObject.class);
                        list.add(orderObject);
                    }
                    if (list.size()==0) {
                        binding.lyt404.setVisibility(View.VISIBLE);
                    }
                    else {
                        binding.lyt404.setVisibility(View.GONE);

                        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(list);
                        binding.rvOrders.setAdapter(myOrderAdapter);
                    }

                    binding.shimmer.setVisibility(View.GONE);
                    binding.swipe.setRefreshing(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            });


    }

    private void onRefresh() {
            binding.shimmer.setVisibility(View.VISIBLE);
            checkLogedin();

    }




//    private void getData() {
//        {
//            {
//
//                disposable.add(RetrofitBuilder.create(getContext())
//                        .getOrderList()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnSubscribe(disposable1 ->{
//                            binding.shimmer.setVisibility(View.GONE);
//                            binding.swipe.setRefreshing(true);})
//                        .doOnDispose(() -> {
//
//                        })
//                        .subscribe((orderMainModelroot, throwable) -> {
//
//                            if (orderMainModelroot != null && orderMainModelroot.getStatus().equals("success")) {
//                                orderList.addAll(orderMainModelroot.getOrder());
//                                if (orderMainModelroot.getResults() == 0) {
//                                    binding.lyt404.setVisibility(View.VISIBLE);
//                                }
//                                else {
//                                    binding.lyt404.setVisibility(View.GONE);
//                                    binding.rvOrders.setAdapter(myOrderAdapter);
//                                }
//                            } else {
//                                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                            }
//
//                            binding.shimmer.setVisibility(View.GONE);
//                            binding.swipe.setRefreshing(false);
//
//                        })
//                );
//
//            }
//        }
//
//        myOrderAdapter.setOnOrderClickListnear(new MyOrderAdapter.OnOrderClickListnear() {
//            @Override
//            public void onClickOpen(OrderDetail order) {
//                startActivity(new Intent(getContext(), OrderDetailActivity.class).putExtra("orderId", order.getOrderId()));
//
//            }
//
//            @Override
//            public void onClickCancel(OrderDetail order) {
////                openCancelSheet(order);
//            }
//        });
//    }

//    private void openCancelSheet(OrderDetail order1) {
//        new ConfirmationPopup(this, "Cancel this order ?", "Do you really want to delete this order? \nHope you know that this action can't be undone.", new ConfirmationPopup.OnPopupClickListner() {
//            @Override
//            public void onPositive() {
//                cancelOrder(order1);
//            }
//
//            @Override
//            public void onNegative() {
////l
//            }
//        }, "Yes", "No");
//
//    }

//    private void cancelOrder(OrderDetail order1) {
//        disposable.add(RetrofitBuilder.create().cancelOrder(Const.DEVKEY, getToken(), order1.getOrderId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable1 -> binding.pBar.setVisibility(View.VISIBLE))
//                .doOnDispose(() -> {
//
//                })
//                .subscribe((restResponse, throwable) -> {
//                    binding.pBar.setVisibility(View.GONE);
//
//                    if (restResponse != null && restResponse.isStatus()) {
//                        Toast.makeText(this, "Order Cancel  Successfully", Toast.LENGTH_SHORT).show();
//
//                        onRefresh();
//
//                    } else {
//                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//
//                }));
//
//    }



}