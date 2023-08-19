package com.example.food_o_door.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.food_o_door.R;
import com.example.food_o_door.activites.BaseActivity;
import com.example.food_o_door.activites.DeliveryAddressActivity;
import com.example.food_o_door.activites.SpleshActivity;
import com.example.food_o_door.activites.WishlistActivity;
import com.example.food_o_door.databinding.FragmentProfileBinding;
import com.example.food_o_door.interfaces.LoginListnraer;
import com.example.food_o_door.popups.ConfirmationPopup;
import com.example.food_o_door.popups.LoderPopup;
import com.example.food_o_door.Const;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.util.HashMap;



public class ProfileFragment extends BaseFragment {

    FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        initView();
        if (sessionManager.getBooleanValue(Const.IS_LOGIN)) {
            setData();
        } else {
            binding.lytLogout.setVisibility(View.GONE);
            checklogin();
        }
        return binding.getRoot();
    }



    public void setData() {
        binding.lytLogout.setVisibility(View.VISIBLE);

        if (getActivity() == null) return;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser!=null){
            Glide.with(getActivity()).load(currentUser.getPhotoUrl()).circleCrop()
                    .placeholder(R.drawable.user_place_holder)
                    .error(R.drawable.user_place_holder).into(binding.lytimage);
            binding.tvName.setText(currentUser.getDisplayName());
            binding.tvPhone.setText(currentUser.getEmail());

        }
    }


    private void initView() {
        binding.lytmyaddress.setOnClickListener(v ->
                {
                    if (sessionManager.getBooleanValue(Const.IS_LOGIN)) {
                        startActivity(new Intent(getActivity(), DeliveryAddressActivity.class));

                    } else {
                        checklogin();
                    }
                }
        );
        binding.lytWishlist.setOnClickListener(v ->  {
                    if (sessionManager.getBooleanValue(Const.IS_LOGIN)) {
                        startActivity(new Intent(getActivity(), WishlistActivity.class));

                    } else {
                        checklogin();
                    }
                }

        );
//        binding.lytComplain.setOnClickListener(v -> startActivity(new Intent(getActivity(), ComplainActivity.class)));
        binding.lytLogout.setOnClickListener(v -> openConfirmDialog());


    }

    private void checklogin() {
            if (getActivity() != null) {
                ((BaseActivity) getActivity()).openLoginSheet(new LoginListnraer() {
                    @Override
                    public void onLoginSuccess() {
                        setData();

                    }

                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void onDismiss() {
                    }
                });
            }
    }








    private void openConfirmDialog() {
        if (getActivity() == null) return;
        new ConfirmationPopup(getActivity(), "Log Out", "Are you really want to logout?", new ConfirmationPopup.OnPopupClickListner() {
            @Override
            public void onPositive() {
                    logoutUser();
            }

            @Override
            public void onNegative() {

            }
        }, "Yes", "No");


    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mGoogleSignInClient.signOut();
        Toast.makeText(getActivity(), "Logout Success", Toast.LENGTH_SHORT).show();
        binding.pd.setVisibility(View.GONE);
        sessionManager.saveStringValue(Const.USER_IMAGE, "");
        sessionManager.saveBooleanValue(Const.IS_LOGIN, false);
        startActivity(new Intent(getActivity(), SpleshActivity.class));
        if (getActivity() != null)
            getActivity().finishAffinity();

    }




}