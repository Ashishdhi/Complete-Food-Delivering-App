package com.example.food_o_door.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.example.food_o_door.Const;
import com.example.food_o_door.models.fooditemsobject;

public class CartUtils {
    private final AppDatabase db;
    Context context;

    public CartUtils(Context context) {
        this.context = context;

        db = Room.databaseBuilder(context,
                AppDatabase.class, Const.DB_NAME).allowMainThreadQueries().build();

    }

    public void add(fooditemsobject product) {

        String name = product.getName();
        String imageurl = product.getImage();
        String priceunitid = product.getProductID();
        String price = product.getPrice();
        String munit = product.getQuantity();
        String munitName = product.getPack();

        Log.d("TAG", "setCartData: price u idqq " + priceunitid);
        Log.d("TAG", "setCartData: price munit " + munit);
        Log.d("TAG", "setCartData: price unitname " + munitName);

        long quantity = getCartdata(priceunitid);
        int quanityproduct = Integer.parseInt(product.getQuantity());
        if (quanityproduct <= quantity) {
            Toast.makeText(context, "You reached Max Limit", Toast.LENGTH_SHORT).show();
            return;
        }

        if (quantity == 0) {
            quantity++;
            CartOffline cartOffline = new CartOffline(quantity, price, name, imageurl, munit, munitName, priceunitid);
            db.cartDao().insertNew(cartOffline);
            Log.d("TAG", "setCartData: added new ");
        } else {
            quantity++;
            db.cartDao().updateObj(quantity, priceunitid);
            Log.d("TAG", "add: updated one");
        }

    }

    public long getCartdata(String id) {
        Log.d("qqqq1", "getCartdata: " + id);
        Log.d("TAG", "getCartdata: size  " + db.cartDao().getCartProduct(id).size());
        if (!db.cartDao().getCartProduct(id).isEmpty()) {
            return db.cartDao().getCartProduct(id).get(0).getQuantity();
        } else {
            return 0;
        }


    }

    public void less(long quantity) {
        String priceunitid = String.valueOf("88");
        db.cartDao().updateObj(quantity, priceunitid);
        long q = getCartdata(priceunitid);
        if (q < 1) {
            db.cartDao().deleteObjbyPid(priceunitid);
        }
    }

    public void less(long quantity, String priceunitid) {

        db.cartDao().updateObj(quantity, priceunitid);
        long q = getCartdata(priceunitid);
        if (q < 1) {
            db.cartDao().deleteObjbyPid(priceunitid);
        }
    }


    public void add(CartOffline product) {

        String name = product.getName();
        String imageurl = product.getImageUrl();
        String priceunitid = String.valueOf(product.getPriceUnitId());
        String price = String.valueOf(product.getPrice());
        String munit = String.valueOf(product.getPriceUnit());
        String munitName = String.valueOf(product.getPriceUnitName());

        Log.d("TAG", "setCartData: price u id " + priceunitid);
        Log.d("TAG", "setCartData: price munit " + munit);
        Log.d("TAG", "setCartData: price unitname " + munitName);

        long quantity = getCartdata(priceunitid);
        int quanityproduct = Integer.parseInt(product.getPriceUnit());

        if (quanityproduct <= quantity) {
            Toast.makeText(context, "You reached Max Limit", Toast.LENGTH_SHORT).show();
            return;
        }
        if (quantity == 0) {
            quantity++;
            CartOffline cartOffline = new CartOffline(quantity, price, name, imageurl, munit, munitName, priceunitid);
            db.cartDao().insertNew(cartOffline);
            Log.d("TAG", "setCartData: added new ");
        } else {
            quantity++;
            db.cartDao().updateObj(quantity, priceunitid);
            Log.d("TAG", "add: updated one");
        }
    }
}
