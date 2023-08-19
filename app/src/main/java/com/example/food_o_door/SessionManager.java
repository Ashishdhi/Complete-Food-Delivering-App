package com.example.food_o_door;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.food_o_door.models.fooditemsobject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SessionManager {
    private static final String TAG = "sessonm";
    private static String userid;
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.pref = context.getSharedPreferences(Const.PREF_NAME, MODE_PRIVATE);
        this.editor = this.pref.edit();
    }

    public void saveStringValue(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        return pref.getString(key, "");
    }

    public void saveBooleanValue(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        return pref.getBoolean(key, false);
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String s) {
        SessionManager.userid = s;
    }

    public void toggleWishlist(fooditemsobject productItem) {
        List<fooditemsobject> wishlist = getWishlist();
        Log.d(TAG, "toggleWishlist: array size " + wishlist.size());

        boolean flag = false;
        for (int i = 0; i < wishlist.size(); i++) {
            if (Objects.equals(wishlist.get(i).getProductID(), productItem.getProductID())) {

                flag = true;
            }
        }
        Log.d(TAG, "toggleWishlist: " + flag);
        if (!flag) {
            wishlist.add(productItem);
        }
        else {
            wishlist.remove(productItem);
        }


        editor.putString(Const.WISHLIST, new Gson().toJson(wishlist));
        editor.apply();
    }

    public List<fooditemsobject> getWishlist() {
        String strData = pref.getString(Const.WISHLIST, "");
        if (!strData.isEmpty()) {
            return new Gson().fromJson(strData, new TypeToken<List<fooditemsobject>>() {
            }.getType());
        }
        return new ArrayList<>();
    }

}
