package com.example.food_o_door.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_o_door.R;
import com.example.food_o_door.databinding.ItemDeliveryaddressOptionsBinding;
import com.example.food_o_door.models.DeliveryAddress;

import java.util.List;

public class DeliveryAddressOptionsAdapter extends RecyclerView.Adapter<DeliveryAddressOptionsAdapter.OptionsViewHolder> {


    private int checkPos = 0;
    OnAddressSelectListnear onAddressSelectListnear;
    private List<DeliveryAddress> data;

    public OnAddressSelectListnear getOnAddressSelectListnear() {
        return onAddressSelectListnear;
    }

    public void setOnAddressSelectListnear(OnAddressSelectListnear onAddressSelectListnear) {
        this.onAddressSelectListnear = onAddressSelectListnear;
    }

    @NonNull
    @Override
    public OptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deliveryaddress_options, parent, false);
        return new OptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.binding.radioBtn.setChecked(checkPos == position);
        holder.binding.radioBtn.setOnClickListener(v -> {

            checkPos = position;

            notifyDataSetChanged();
            onAddressSelectListnear.onAddressSelected(data.get(position));
        });
        holder.setModel(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<DeliveryAddress> data) {

        this.data = data;
    }

    public interface OnAddressSelectListnear {
        void onAddressSelected(DeliveryAddress address);
    }

    public class OptionsViewHolder extends RecyclerView.ViewHolder {
        ItemDeliveryaddressOptionsBinding binding;

        public OptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDeliveryaddressOptionsBinding.bind(itemView);


        }

        public void setModel(DeliveryAddress address) {
            binding.tvUserName.setText(address.getName());
            binding.tvAddress.setText(address.getAddress().concat(" " +
                    address.getState() + " " +
                    address.getCity() + " " +
                    address.getPincode()));

            binding.tvMobile.setText(address.getPhoneno());



                binding.tvDefult.setVisibility(View.VISIBLE);

            String s = getAddressType(1);
            binding.tvType.setText(s);

        }


        private String getAddressType(int addressType) {
            if (addressType == 1) {
                return "Home";
            } else if (addressType == 2) {
                return "Office";
            } else {
                return "Other";
            }

        }
    }
}
