package com.example.food_o_door.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.example.food_o_door.Const;
import com.example.food_o_door.R;
import com.example.food_o_door.adapters.CartAdapter;
import com.example.food_o_door.dao.AppDatabase;
import com.example.food_o_door.dao.CartOffline;
import com.example.food_o_door.databinding.FragmentCartBinding;
import com.example.food_o_door.interfaces.LoginListnraer;
import com.example.food_o_door.popups.ConfirmationPopup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity {
    FragmentCartBinding binding;

    private AppDatabase db;
    double totalPrice = 0;
    private List<CartOffline> list = new ArrayList<>();
    List<String> productIds = new ArrayList<>();
    List<CartOffline> cartItemlist = new ArrayList<>();
    CartAdapter cartAdapter = new CartAdapter(cartItemlist);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_cart);
        db = Room.databaseBuilder(this,
                AppDatabase.class, Const.DB_NAME).allowMainThreadQueries().build();
        cartAdapter.initAdapter(this);
        getCartData();
    }
    private void getCartData() {
        list.clear();
        list = db.cartDao().getall();
        cartAdapter.addData(list);
        cartAdapter.setOnCartClickListnear(new CartAdapter.OnCartClickListnear() {
            @Override
            public void onNotify() {
                calculateTotal();
            }

            @Override
            public void onDelete(CartOffline product, int pos) {
                new ConfirmationPopup(CartActivity.this, getString(R.string.deleteproduct), getString(R.string.deleteconfirlmsg), new ConfirmationPopup.OnPopupClickListner() {
                    @Override
                    public void onPositive() {
                        deleteProduct(product, pos);
                    }

                    @Override
                    public void onNegative() {
//ll
                    }
                }, "OK", "Cancel");


            }
        });
        calculateTotal();
        if (list.isEmpty()) {
            binding.lyt404.setVisibility(View.VISIBLE);
            binding.materialTotalCardView.setVisibility(View.GONE);
        }
        else {
            initView();
        }
    }

    private void deleteProduct(CartOffline product, int pos) {


        AppDatabase db1 = Room.databaseBuilder(this,
                AppDatabase.class, Const.DB_NAME).allowMainThreadQueries().build();
        db1.cartDao().deleteObjbyPid(product.getPriceUnitId());
        Log.d("TAG", "deleteProduct: " + product.getName());
        cartAdapter.removeItem(pos);
        list.remove(product);
        calculateTotal();
        if (list.isEmpty()) {
            binding.lyt404.setVisibility(View.VISIBLE);
        }
    }

    private void calculateTotal() {
        Log.d("TAG", "calculateTotal: ");
        totalPrice = 0;
        double p;
        for (CartOffline product : db.cartDao().getall()) {
            productIds.add(String.valueOf(product.getPid()));
            p = Double.parseDouble(product.getPrice());
            long quantity = product.getQuantity();
            double price = (p * quantity);
            Log.d("TAG", "calculateTotal: forrr " + price + "  qq " + quantity);
            totalPrice = (totalPrice + price);

        }
        double finalprice = totalPrice+40;
        binding.textView23.setText("₹" + totalPrice);
        binding.textView24.setText("₹0");
        binding.textView25.setText("₹40");
        if (totalPrice==0){
            binding.tvTotalAmount.setText(Const.getCurrency1() + new DecimalFormat(Const.FORMAT_PATTERN).format(totalPrice));
            binding.totaltvcard.setText("₹" + totalPrice);
            binding.lyt404.setVisibility(View.VISIBLE);
            binding.materialTotalCardView.setVisibility(View.GONE);

        }
        else{
            binding.tvTotalAmount.setText(Const.getCurrency1() + new DecimalFormat(Const.FORMAT_PATTERN).format(finalprice));
            binding.totaltvcard.setText("₹" + finalprice);
        }
    }

    private void initView() {
        binding.rvCart.setAdapter(cartAdapter);
        binding.tvCheckout.setOnClickListener(v -> {
            if (list.isEmpty()) {
                Toast.makeText(this, "Your Cart Is Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (sessionManager.getBooleanValue(Const.IS_LOGIN)) {
                checkProductAvaiblity();
            } else {

                ((BaseActivity) this).openLoginSheet(new LoginListnraer() {
                    @Override
                    public void onLoginSuccess() {

                    }

                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void onDismiss() {
                        //
                    }
                });

            }
        });
    }

    private void checkProductAvaiblity() {
        startActivity(new Intent(this, DeliveryOptionsActivity.class));
    }

}