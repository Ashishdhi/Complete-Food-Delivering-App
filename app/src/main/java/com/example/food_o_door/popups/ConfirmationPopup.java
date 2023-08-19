package com.example.food_o_door.popups;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.food_o_door.R;
import com.example.food_o_door.databinding.PopupConfirmationBinding;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ConfirmationPopup {
    private final Dialog dialog;

    public ConfirmationPopup(Context context, String title, String decription, OnPopupClickListner onPopupClickListner, String positive, String negative) {
        dialog = new Dialog(context, R.style.customStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        PopupConfirmationBinding popupConfirmationBinding = DataBindingUtil.inflate(inflater, R.layout.popup_confirmation, null, false);
        popupConfirmationBinding.tvtitle.setText(title);
        if (decription.isEmpty()) {
            popupConfirmationBinding.tvDes.setVisibility(View.GONE);
        } else {
            popupConfirmationBinding.tvDes.setText(decription);
        }
        if (negative.isEmpty()) {
            popupConfirmationBinding.tvNagetive.setVisibility(View.GONE);
        }
        popupConfirmationBinding.tvPositive.setText(positive);
        popupConfirmationBinding.tvNagetive.setText(negative);
        dialog.setContentView(popupConfirmationBinding.getRoot());
        dialog.show();

        popupConfirmationBinding.tvPositive.setOnClickListener(v -> {
            dialog.dismiss();
            onPopupClickListner.onPositive();
        });
        popupConfirmationBinding.tvNagetive.setOnClickListener(v -> {
            dialog.dismiss();
            onPopupClickListner.onNegative();
        });


    }

    public interface OnPopupClickListner {
        void onPositive();

        void onNegative();
    }
}
