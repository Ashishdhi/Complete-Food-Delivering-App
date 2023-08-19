package com.example.food_o_door.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.food_o_door.R;
import com.example.food_o_door.databinding.ActivityOrderDetailBinding;
import com.example.food_o_door.databinding.BottomsheetHaveAnIssueBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

public class OrderDetailActivity extends BaseActivity {
    ActivityOrderDetailBinding binding;

    BottomsheetHaveAnIssueBinding bottomsheetHaveAnIssueBinding;
    private BottomSheetDialog bottomSheetDialog;
//    private String orderId;
//    private OrderDetail orderDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);

        Intent intent = getIntent();
//        orderId = intent.getStringExtra("orderId");
//        if (orderId != null && !orderId.isEmpty()) {
            getOrderDetails();

//        }

    }

    private void getOrderDetails() {
//        disposable.add(RetrofitBuilder.create().getOrderDetail(Const.DEVKEY, getToken(), orderId).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable1 -> binding.pBar.setVisibility(View.VISIBLE))
//                .doOnDispose(() -> {
//
//                })
//                .subscribe((orderDetailRoot, throwable) -> {
//                    binding.pBar.setVisibility(View.GONE);
//                    if (orderDetailRoot != null && orderDetailRoot.isStatus() && orderDetailRoot.getOrderDetail() != null) {
//                        orderDetail = orderDetailRoot.getOrderDetail();

                        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));
                        initView();
//                        binding.tvOrderId.setText("orderId");
//                        binding.lytBottom.setVisibility(orderDetail.getStatus() == 4 ? View.VISIBLE : View.GONE);
                        binding.lytBottom.setVisibility(View.VISIBLE);
//                        if (orderDetailRoot.getOrderDetail().getHasComplaint() == 1) {
                            binding.lytBottom.setVisibility(View.GONE);
//                        }


                        binding.tvNewIssue.setOnClickListener(v -> openBottomSheet());
                    }
//                    else {
//                        binding.lyt404.setVisibility(View.VISIBLE);
//                    }
//                    if (throwable != null) {
//                        Log.i("TAG", "getOrderDetails: err " + throwable.toString());
//                    }
//                }));



    private void openBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = (FrameLayout) d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet)
                    .setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        bottomsheetHaveAnIssueBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.bottomsheet_have_an_issue, null, false);
        bottomSheetDialog.setContentView(bottomsheetHaveAnIssueBinding.getRoot());
        bottomSheetDialog.show();
//        bottomsheetHaveAnIssueBinding.tvOrderId.setText(orderId);
        bottomsheetHaveAnIssueBinding.btnClose.setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomsheetHaveAnIssueBinding.lytBottom.setOnClickListener(view -> {
            if (bottomsheetHaveAnIssueBinding.etDes.getText().toString().length() >= 100) {
                setndComplaint();
            } else {
                Toast.makeText(this, "Please describe your issue briefly", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setndComplaint() {

        String title = bottomsheetHaveAnIssueBinding.etTitle.getText().toString();
        String des = bottomsheetHaveAnIssueBinding.etDes.getText().toString();
        String mobile = bottomsheetHaveAnIssueBinding.etMobile.getText().toString();
        if (title.equals("")) {
            Toast.makeText(this, getString(R.string.writetitlefirst), Toast.LENGTH_SHORT).show();
            return;
        }
        if (des.equals("")) {
            Toast.makeText(this, getString(R.string.writedescriptionfirst), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobile.equals("")) {
            Toast.makeText(this, getString(R.string.entermobilenumber), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobile.length() > 11) {
            Toast.makeText(this, getString(R.string.entervalidmobilenumber), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobile.length() < 10) {
            Toast.makeText(this, getString(R.string.entervalidmobilenumber), Toast.LENGTH_SHORT).show();
            return;
        }
        binding.pBar.setVisibility(View.VISIBLE);
//        disposable.add(RetrofitBuilder.create(getApplicationContext()).raiseComplaint(Const.DEVKEY, getToken(), "orderId", mobile, title, des)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable1 -> binding.pBar.setVisibility(View.VISIBLE))
//                .doOnDispose(() -> {
//
//                })
//                .subscribe((restResponse, throwable) -> {
//                    binding.pBar.setVisibility(View.GONE);
////                    if (restResponse != null && restResponse.isStatus()) {
////                        binding.lytBottom.setVisibility(View.GONE);
////                        Toast.makeText(this, "Complain Raised Successfully", Toast.LENGTH_SHORT).show();
////
////                    } else {
////                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
////                    }
//                    bottomSheetDialog.dismiss();
//                }));

    }

    private void initView() {
        binding.tablayout.addTab(binding.tablayout.newTab().setText(R.string.summary));
        binding.tablayout.addTab(binding.tablayout.newTab().setText(R.string.items));

    }


}