
package com.example.food_o_door.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_o_door.popups.LoderPopup;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.food_o_door.R;
import com.example.food_o_door.SessionManager;
import com.example.food_o_door.databinding.ItemLoginBinding;
import com.example.food_o_door.interfaces.LoginListnraer;
import com.example.food_o_door.Const;

import java.text.DecimalFormat;


public abstract class BaseActivity extends AppCompatActivity {

    static final String TAG = "baseactivity";
    SessionManager sessionManager;

    BottomSheetDialog bottomSheetDialog;





    private LoderPopup loderPopup;

    DecimalFormat df;
    private LoginListnraer loginlistener;

    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        sessionManager = new SessionManager(this);
        df = new DecimalFormat(Const.FORMAT_PATTERN);
        loderPopup = new LoderPopup(this);
        if (sessionManager.getBooleanValue(Const.IS_LOGIN)) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }




//        if (sessionManager.getBooleanValue(Const.IS_LOGIN)) {
//        }

    }



    // [START signin]
    private void signIn() {
        loderPopup.show();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void updateUI(FirebaseUser user) {

    }


    public String getToken() {
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getUid();
        }
        return "";
    }

    public void openLoginSheet(LoginListnraer loginListnraer) {

        this.loginlistener = loginListnraer;
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        ItemLoginBinding itemLoginBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_login, null, false);
        bottomSheetDialog.setContentView(itemLoginBinding.getRoot());
        bottomSheetDialog.show();
        itemLoginBinding.buttonLoginWithNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

        itemLoginBinding.btnclose.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            loginlistener.onFailure();
        });
//        itemLoginBinding.tvTerms.setOnClickListener(v -> WebActivity.open(this, "Privacy Policy", sessionManager.getSetting().getPrivacyPolicy()));
        bottomSheetDialog.setOnDismissListener(dialog -> loginlistener.onDismiss());
    }


    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loderPopup.cencel();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(BaseActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            sessionManager.saveBooleanValue(Const.IS_LOGIN, true);
                            bottomSheetDialog.dismiss();
                            loginlistener.onLoginSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(BaseActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }
                    }
                });
    }


}
