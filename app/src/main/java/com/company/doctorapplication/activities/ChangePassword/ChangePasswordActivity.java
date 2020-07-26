package com.company.doctorapplication.activities.ChangePassword;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.company.doctorapplication.Models.ChangeDoctorPassword.ChangeDoctorPasswordRequest;
import com.company.doctorapplication.Models.ChangeDoctorPassword.ChangeDoctorPasswordResponse;
import com.company.doctorapplication.Network.NetworkUtil;
import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.Utills.Validation;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.company.doctorapplication.Utills.Constant.ErrorDialog;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText editTextOldPassword;
    EditText editTextNewPassword;
    EditText editTextReEnterNewPassword;
    CompositeSubscription mSubscriptions;
    Button buttonChangePassword;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mSubscriptions = new CompositeSubscription();
        mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        editTextOldPassword = findViewById(R.id.edit_text_old_password);
        editTextReEnterNewPassword = findViewById(R.id.edit_text_re_enter_new_password);
        editTextNewPassword = findViewById(R.id.edit_text_new_password);
        buttonChangePassword = findViewById(R.id.button_change_password);
        buttonChangePassword.setOnClickListener(view -> {

            validation(editTextOldPassword.getText().toString(),
                    editTextNewPassword.getText().toString(),
                    editTextReEnterNewPassword.getText().toString());
        });

    }

    void validation(String oldPassword, String password, String rePassword) {
        if (!Validation.validateFields(oldPassword)) {
            ErrorDialog(this, "username is Empty");
        } else if (!Validation.validateFields(password)) {
            ErrorDialog(this, "Password is Empty");
        } else if (!Validation.validateFields(rePassword)) {
            ErrorDialog(this, "Re-Password is Empty");
        } else if (!password.equals(rePassword)) {
            ErrorDialog(this, "Password is not matched");
        } else {
            if (Validation.isConnected(this)) {
                ChangeDoctorPasswordRequest changeDoctorPasswordRequest = new ChangeDoctorPasswordRequest();
                changeDoctorPasswordRequest.setDoctorId(mSharedPreferences.getString(Constant.accessToken, ""));
                changeDoctorPasswordRequest.setDoctorOldPassword(oldPassword);
                changeDoctorPasswordRequest.setDoctorNewPassword(password);

                mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                        .ChangeDoctorPassword(changeDoctorPasswordRequest)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleErrorLogin));
            } else {
                Constant.buildDialog(this);
            }
        }
    }

    private void handleErrorLogin(Throwable throwable) {
        Log.e("ChangePasswordError", throwable.getMessage(), throwable);
    }


    private void handleResponse(ChangeDoctorPasswordResponse changeDoctorPasswordResponse) {
        switch (changeDoctorPasswordResponse.getResultCode()) {
            case "1":
                Toast.makeText(this, "Password Has been Changed Successfully.", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(this::finish, 1000);
                finish();
                break;
            case "0":
                Toast.makeText(this, "Some Data is incorrect,Try Again.", Toast.LENGTH_SHORT).show();

                Constant.ErrorDialog(this, "Some Data is incorrect,Try Again.");
                Log.e("LoginServerError(0)", changeDoctorPasswordResponse.getResultMessageEn());

                break;
            case "2":
                Toast.makeText(this, "Some Data is incorrect,Try Again.", Toast.LENGTH_SHORT).show();
                Log.e("LoginServerError(2)", changeDoctorPasswordResponse.getResultMessageEn());
                break;
        }
    }

}
