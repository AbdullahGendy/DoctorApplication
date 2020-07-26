package com.company.doctorapplication.activities.Splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.Utills.Validation;
import com.company.doctorapplication.activities.Home.HomeActivity;
import com.company.doctorapplication.activities.Login.LoginActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import rx.subscriptions.CompositeSubscription;


public class Splash extends AppCompatActivity {

    SharedPreferences mSharedPreferences;
    CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (Validation.isConnected(this)) {
                if (mSharedPreferences.getString(Constant.accessToken, "").equals("")) {
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
                        String newToken = instanceIdResult.getToken();
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString(Constant.notificationToken, newToken);
                        editor.apply();
                    });
                }
            }
            Intent intent;
            if ((mSharedPreferences.getBoolean(Constant.rememberMe, false))) {
                intent = new Intent(Splash.this, HomeActivity.class);

            } else {
                intent = new Intent(Splash.this, LoginActivity.class);
            }

            finish();
            startActivity(intent);
        }, 3000);
    }

}
