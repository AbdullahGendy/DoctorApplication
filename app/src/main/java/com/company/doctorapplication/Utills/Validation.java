package com.company.doctorapplication.Utills;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Patterns;


public class Validation {

    public static boolean validateFields(String name) {

        return !TextUtils.isEmpty(name);
    } public static boolean validateBirthDate(String birthDate) {

        return !birthDate.equals("MM-DD-YYYY");
    }

    public static boolean isNullOrEmpty(String response) {

        return response.equals("");
    }

    public static boolean isNullOrEmpty(int response) {

        return response == 0;
    }

    public static boolean validateEmail(String string) {

        return !TextUtils.isEmpty(string) && Patterns.EMAIL_ADDRESS.matcher(string).matches();
    }

    public static boolean validatePhone(String string) {

        return !TextUtils.isEmpty(string) && string.length() == 11 && Patterns.PHONE.matcher(string).matches();
    }

    public static boolean validatePassword(String string) {

        return !TextUtils.isEmpty(string) && string.length() >= 6;
    }

    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else return false;
    }

}