package com.company.doctorapplication.FirebaseServices;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.company.doctorapplication.Models.DeviceToken.UpdateDeviceTokenRequest;
import com.company.doctorapplication.Models.DeviceToken.UpdateDeviceTokenResponse;
import com.company.doctorapplication.Network.NetworkUtil;
import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.Utills.Validation;
import com.company.doctorapplication.activities.Home.HomeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


@SuppressLint("Registered")
public class MyFireBaseMessagingService extends FirebaseMessagingService {
    public SharedPreferences mSharedPreferences;
    public String mAccessToken = "";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        mAccessToken = mSharedPreferences.getString(Constant.accessToken, "");
        if (!mAccessToken.equals("")) {
            if (remoteMessage.getData().isEmpty())
                showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(), remoteMessage.getNotification().getBody());
            else
                showNotification(remoteMessage.getData());
        } else {
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            String body = data.get("body");
            SharedPreferences.Editor editor_LogoutNotification = mSharedPreferences.edit();
            editor_LogoutNotification.putString(Constant.logoutNotificationTitle, title);
            editor_LogoutNotification.putString(Constant.logoutNotificationBody, body);
            editor_LogoutNotification.apply();
        }


    }

    private void showNotification(Map<String, String> data) {
        String title = data.get("title");
        String body = data.get("body");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANEL_ID = "com.example.donatorapp";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID, "notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("approval");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorPrimary);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setContentInfo("info");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }


    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANEL_ID = "com.example.donatorapp";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID, "notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("approval");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorPrimary);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setContentInfo("info");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("on new token", s);
        mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        SharedPreferences.Editor editor_newToken = mSharedPreferences.edit();
        editor_newToken.putString(Constant.notificationToken, s);
        editor_newToken.apply();
        if (!Objects.requireNonNull(mSharedPreferences.getString(Constant.accessToken, "")).equals("")) {
            if (Validation.isConnected(this)) {
                CompositeSubscription mSubscriptions = new CompositeSubscription();
                UpdateDeviceTokenRequest updateDeviceTokenRequest = new UpdateDeviceTokenRequest();
                updateDeviceTokenRequest.setDeviceToken(mSharedPreferences.getString(Constant.notificationToken, ""));
                updateDeviceTokenRequest.setDonatorId(mSharedPreferences.getString(Constant.accessToken, ""));
                mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                        .UpdateDonatorDeviceToken(updateDeviceTokenRequest)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
            }
        }


    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();

    }

    private void handleResponse(UpdateDeviceTokenResponse updateDeviceTokenResponse) {
        Toast.makeText(this, updateDeviceTokenResponse.getResultMessageEn(), Toast.LENGTH_LONG).show();

    }

}