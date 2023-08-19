package com.example.food_o_door;

import android.content.Context;

import androidx.core.content.ContextCompat;

public class VegiUtils {

private VegiUtils() {

}

    public static String getAddressType(int addressType) {
        if (addressType == 1) {
            return "Home";
        } else if (addressType == 2) {
            return "Work";
        } else {
            return "Other";
        }

    }

    public static String getOrderStatus(int status) {
        if (status == 1) {
            return "Processing";
        } else if (status == 2) {
            return "Confirmed";
        } else if (status == 3) {
            return "Hold";
        } else if (status == 4) {
            return "Completed";
        } else if (status == 5) {
            return "Cancelled";
        }
        return "";
    }

    public static int getOrderStatusColor(Context context, int status) {
        if (status == 1) {
            return ContextCompat.getColor(context, R.color.processing);
        } else if (status == 2) {
            return ContextCompat.getColor(context, R.color.confirmed);
        } else if (status == 3) {
            return ContextCompat.getColor(context, R.color.onhold);
        } else if (status == 4) {
            return ContextCompat.getColor(context, R.color.completed);
        } else if (status == 5) {
            return ContextCompat.getColor(context, R.color.cancelled);
        }
        return ContextCompat.getColor(context, R.color.cancelled);
    }

    public static String getComplainStatus(int status) {

        return status == 1 ? "Closed" : "Open";
    }
}
