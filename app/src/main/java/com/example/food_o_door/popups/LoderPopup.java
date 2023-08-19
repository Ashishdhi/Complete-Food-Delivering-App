package com.example.food_o_door.popups;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.example.food_o_door.R;
import com.example.food_o_door.databinding.PopupLoderBinding;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class LoderPopup {
    private final Dialog dialog;
    final Context context;

    public LoderPopup(Context context) {
        dialog = new Dialog(context, R.style.customStyle);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        PopupLoderBinding popupLoderBinding = DataBindingUtil.inflate(inflater, R.layout.popup_loder, null, false);
        dialog.setContentView(popupLoderBinding.getRoot());

    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void cencel() {

        dialog.dismiss();

    }

}
