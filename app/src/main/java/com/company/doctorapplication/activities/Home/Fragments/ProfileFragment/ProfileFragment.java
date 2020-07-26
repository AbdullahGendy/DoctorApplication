package com.company.doctorapplication.activities.Home.Fragments.ProfileFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.company.doctorapplication.Models.GetDoctorInfo.Clinics;
import com.company.doctorapplication.Models.GetDoctorInfo.GetDoctorInfoResponse;
import com.company.doctorapplication.Network.NetworkUtil;
import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.Utills.Validation;
import com.company.doctorapplication.activities.AddClinic.AddClinicActivity;
import com.company.doctorapplication.activities.ChangePassword.ChangePasswordActivity;
import com.company.doctorapplication.activities.DeleteClinic.DeleteClinicActivity;
import com.company.doctorapplication.activities.Login.LoginActivity;
import com.company.doctorapplication.activities.ShowReservation.ShowReservation;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {
    private TextView textViewSpecialization;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewScientificDegree;
    private TextView textViewPhoneNumber;
    private TextView textViewAppointmentFees;
    private TextView textViewChangePassword;
    private TextView textViewDeleteClinic;
    private TextView textViewShowReservation;
    private TextView textViewAddClinic;
    private TextView textViewAddress;
    private SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        textViewSpecialization = view.findViewById(R.id.text_view_specialization);
        textViewName = view.findViewById(R.id.text_view_name);
        textViewShowReservation = view.findViewById(R.id.text_view_show_reservation);
        textViewChangePassword = view.findViewById(R.id.text_view_change_password);
        textViewDeleteClinic = view.findViewById(R.id.text_view_delete_clinic);
        textViewAddClinic = view.findViewById(R.id.text_view_add_clinic);
        textViewEmail = view.findViewById(R.id.text_view_email);
        textViewScientificDegree = view.findViewById(R.id.text_view_scientific_degree);
        textViewAddress = view.findViewById(R.id.text_view_address);
        textViewAppointmentFees = view.findViewById(R.id.text_view_appointment_fees);
        textViewPhoneNumber = view.findViewById(R.id.text_view_phone_number);
        TextView textViewLogOut = view.findViewById(R.id.text_view_log_out);
        mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        textViewChangePassword.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });
        textViewAddClinic.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), AddClinicActivity.class);
            startActivity(intent);
        });
        textViewShowReservation.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ShowReservation.class);
            startActivity(intent);
        });
        textViewDeleteClinic.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), DeleteClinicActivity.class);
            startActivity(intent);
        });
        textViewLogOut.setOnClickListener(v -> {
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(Constant.accessToken, "");
            editor.putString(Constant.notificationToken, "");
            editor.putBoolean(Constant.rememberMe, false);
            editor.putString(Constant.logoutNotificationTitle, "");
            editor.putString(Constant.logoutNotificationBody, "");
            editor.apply();
            Objects.requireNonNull(getActivity()).finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
        });
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        String mAccessToken = mSharedPreferences.getString(Constant.accessToken, "1");
        if (Validation.isConnected(Objects.requireNonNull(getActivity()))) {

            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .GetDoctorInfoByDoctorId("1")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.buildDialog(getActivity());
        }
        return view;
    }

    private void handleError(Throwable throwable) {
        Log.e("ErrorDoctorProfile:", throwable.getMessage(), throwable);
    }

    @SuppressLint("SetTextI18n")
    private void handleResponse(GetDoctorInfoResponse getDoctorInfoResponse) {
        switch (getDoctorInfoResponse.getResultCode()) {
            case "1":

                Log.e("getDoctorInfoResponse", getDoctorInfoResponse.getResultCode() + "-" + getDoctorInfoResponse.getResultMessageEn());
                Clinics clinic = getDoctorInfoResponse.getData().getClinics().get(0);
                textViewSpecialization.setText(getDoctorInfoResponse.getData().getSpecialization());
                textViewName.setText(getDoctorInfoResponse.getData().getName());
                textViewPhoneNumber.setText(getDoctorInfoResponse.getData().getMobileNumber());
                textViewEmail.setText(getDoctorInfoResponse.getData().getEmail());
                textViewAddress.setText("City: " + clinic.getCity() +
                        ", Street Name: " + clinic.getStreetName() +
                        ", Building Name: " + clinic.getBuildingName() +
                        ", floor number: " + clinic.getFloor() + ".");
                textViewScientificDegree.setText(getDoctorInfoResponse.getData().getScientificDegree());
                textViewAppointmentFees.setText(getDoctorInfoResponse.getData().getAppointmentFees());
                break;
            case "0":
            case "2":
                // Toast.makeText(this, registrationResponseModel.getResultMessageEn(), Toast.LENGTH_SHORT).show();
                Log.e("DoctorInfoServerError", getDoctorInfoResponse.getResultCode() + "-" + getDoctorInfoResponse.getResultMessageEn());
                break;
        }
    }


    private String calcAge(String DateOfBirth) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1;
            try {
                date1 = simpleDateFormat.parse(DateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
                return "1";
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DATE);
            LocalDate birthDate = LocalDate.of(year, month, date);
            LocalDate now = LocalDate.now();
            Period age;

            age = Period.between(birthDate, now);

            return String.valueOf(age.getYears());
        } else return String.valueOf(2020 - Integer.valueOf(DateOfBirth.substring(0, 4)));
    }
}