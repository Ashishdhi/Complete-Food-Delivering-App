package com.example.food_o_door.activites;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.example.food_o_door.Const;
import com.example.food_o_door.R;
import com.example.food_o_door.adapters.OrderItemsTextAdapter;
import com.example.food_o_door.dao.AppDatabase;
import com.example.food_o_door.dao.CartOffline;
import com.example.food_o_door.databinding.ActivityPaymentSummaryBinding;
import com.example.food_o_door.models.DeliveryAddress;
import com.example.food_o_door.models.fooditemsobject;
import com.example.food_o_door.popups.LoderPopup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentSummaryActivity extends BaseActivity {
    private static final String TAG = "paymentactivity";
    ActivityPaymentSummaryBinding binding;


    String userAddress = "";

    List<String> quantities = new ArrayList<>();
    List<String> prices = new ArrayList<>();
    List<String> productnames = new ArrayList<>();
    List<String> images = new ArrayList<>();
    List<String> priceunits = new ArrayList<>();
    List<String> productIds = new ArrayList<>();
    List<String> priceunitnames = new ArrayList<>();
    List<String> totalprices = new ArrayList<>();
    List<CartOffline> list = new ArrayList<>();


    String addressString = "";
    AppDatabase db;
    private int paymentType = 1;
    private double totalamount = 0;
    private double shippingCharge = 0;
    private String couponCode = "";
    private double subTotal;
    private DeliveryAddress address;
    private int selectedCouponId = 0;
    private List<fooditemsobject> coupons = new ArrayList<>();

    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void radioButtonListnear() {
        binding.radioCash.setChecked(true);
        binding.radioCash.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.radioOnline.setChecked(false);
                binding.radioCash.setChecked(true);
                paymentType = 1;
                toggleCardUI();
            }
        });
        binding.radioOnline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.radioCash.setChecked(false);
                binding.radioOnline.setChecked(true);
                paymentType = 2;
                toggleCardUI();
            }
        });
    }

    private void toggleCardUI() {
        if (paymentType == 1) {
            binding.lytCard.setVisibility(View.GONE);
            binding.btnPlaceOrder.setText(R.string.placeorder);
        } else {
            binding.lytCard.setVisibility(View.VISIBLE);
            binding.btnPlaceOrder.setText(R.string.checkout);
        }
    }

    private void orderConfirm() {
        userAddress = address.getAddress().concat(" " +
                address.getCity() + " " + address.getState() + " " + address.getPincode());
         LoderPopup loderPopup;
        loderPopup = new LoderPopup(this);
        loderPopup.show();
        DatabaseReference rootref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("orders");
                String pushref = rootref.push().getKey();
                assert pushref != null;
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("name",address.getName());
                hashMap.put("orderid",pushref);
                hashMap.put("price",Const.getCurrency1() + totalamount);
                hashMap.put("items","Order Items (".concat(String.valueOf(list.size())).concat(")"));
                hashMap.put("orderaddress",userAddress);
                hashMap.put("phoneno",address.getPhoneno());
                rootref.child(pushref).setValue(hashMap).addOnSuccessListener(unused -> {
                    loderPopup.cencel();
                        startActivity(new Intent(this,OrderConfirmedActivity.class).putExtra("orderid",pushref));
                        finish();
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_summary);




        Intent intent = getIntent();

        addressString = intent.getStringExtra("address");

        if (addressString != null && !addressString.equals("")) {
            DeliveryAddress tempAddress = new Gson().fromJson(addressString, DeliveryAddress.class);
            if (tempAddress != null) {
                address = tempAddress;
                setAddress();

            }
        }
        shippingCharge = Double.parseDouble("0");
        initView();
        radioButtonListnear();


    }

    private void setAddress() {
        binding.tvName.setText(address.getName());
        userAddress = address.getAddress().concat(" " +
                address.getCity() + " " + address.getState() + " " + address.getPincode()+"\n"+address.getPhoneno());
        binding.tvAddress.setText(userAddress);

    }




    private void initView() {

        db = Room.databaseBuilder(this,
                AppDatabase.class, Const.DB_NAME).allowMainThreadQueries().build();
        list.clear();
        list = db.cartDao().getall();

        OrderItemsTextAdapter orderItemsTextAdapter = new OrderItemsTextAdapter(list);
        binding.rvOrdersItems.setAdapter(orderItemsTextAdapter);
        binding.tvOrderItems.setText("Order Items (".concat(String.valueOf(list.size())).concat(")"));


        for (int i = 0; i <= list.size() - 1; i++) {
            CartOffline product = list.get(i);
            Log.d(TAG, "uploadCart:forpids  " + product.getPid() + " qq " + product.getQuantity());
            productIds.add(String.valueOf(product.getPid()));
            quantities.add(String.valueOf(product.getQuantity()));
            prices.add(String.valueOf(product.getPrice()));
            productnames.add(String.valueOf(product.getName()));
            images.add(String.valueOf(product.getImageUrl()));
            priceunits.add(String.valueOf(product.getPriceUnit()));
            priceunitnames.add(String.valueOf(product.getPriceUnitName()));

            double p = Double.parseDouble(product.getPrice());
            long quantity = product.getQuantity();
            double price = p * quantity;
            totalprices.add(String.valueOf(price));


        }
        setPrice();
        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderConfirm();
            }
        });


        binding.lytCard.setVisibility(View.GONE);
        binding.imgArrow.setOnClickListener(v -> {
            if (binding.rvOrdersItems.getVisibility()==View.VISIBLE){
                collapse(binding.rvOrdersItems);
            }
            else{
                expand(binding.rvOrdersItems);


            }
        });
        paymentType = 1;

        binding.pBar.setVisibility(View.GONE);
    }


    private void setPrice() {
        totalamount = 0;
        subTotal = 0;
        binding.tvShippnigCharge.setText(Const.getCurrency1() + "40");
        setSubTotal();
        setTotalPrice();
    }


    private void setTotalPrice() {
        totalamount = subTotal + shippingCharge+40;
        binding.tvSubtotalPrice.setText(Const.getCurrency1() + df.format(subTotal));
        binding.tvTotalPrice.setText(Const.getCurrency1() + df.format(totalamount));
    }

    private void setSubTotal() {
        subTotal = 0;
        for (int i = 0; i <= list.size() - 1; i++) {
            CartOffline product = list.get(i);
            double p = Double.parseDouble(product.getPrice());
            long quantity = product.getQuantity();
            double price = p * quantity;

            Log.d(TAG, "getTotalAmount: " + product.getPrice() + " * " + product.getQuantity());
            subTotal = subTotal + price;
        }
        binding.tvSubtotalPrice.setText(Const.getCurrency1() + df.format(subTotal));

    }
    public void onClickBack(View v){
        onBackPressed();
    }


}