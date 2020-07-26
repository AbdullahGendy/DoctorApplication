package com.company.doctorapplication.activities.Home.Fragments.Reservation;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.company.doctorapplication.Models.ConfirmReservation.ConfirmReservationRequest;
import com.company.doctorapplication.Models.ConfirmReservation.ConfirmReservationResponse;
import com.company.doctorapplication.Models.GetClinicInfoByClinicId.Data;
import com.company.doctorapplication.Models.GetClinicInfoByClinicId.GetClinicInfoByClinicIdResponse;
import com.company.doctorapplication.Models.GetClinicInfoByClinicId.WorkingDays;
import com.company.doctorapplication.Models.GetClinics.GetClinicsResponse;
import com.company.doctorapplication.Models.GetEstimatedReservationTime.GetEstimatedReservationTimeRequest;
import com.company.doctorapplication.Models.GetEstimatedReservationTime.GetEstimatedReservationTimeResponse;
import com.company.doctorapplication.Network.NetworkUtil;
import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.Utills.Validation;

import java.util.ArrayList;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static com.company.doctorapplication.Utills.Constant.CitiesIdArrayList;
import static com.company.doctorapplication.Utills.Constant.CitiesSpinnerArray;
import static com.company.doctorapplication.Utills.Constant.ClinicDaysSpinnerArray;
import static com.company.doctorapplication.Utills.Constant.buildDialog;
import static com.company.doctorapplication.Utills.Constant.errorBuildDialog;

public class ReservationFragment extends Fragment {
    private Spinner spinnerClinic;
    private CompositeSubscription mSubscriptions;
    private String afterCheckDetails;
    private Button reserveButton, checkButton;
    private Data currentClinicData;
    private SharedPreferences mSharedPreferences;
    private String date;
    private TextView Birthday;
    private DatePicker datePicker;
    private GetEstimatedReservationTimeResponse globalGetEstimatedReservationTimeResponse;
    private Button done;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_reservations, container, false);
        spinnerClinic = fragmentView.findViewById(R.id.spinner_clinic);
        checkButton = fragmentView.findViewById(R.id.Check_button);
        reserveButton = fragmentView.findViewById(R.id.Reserve_button);
        Birthday = fragmentView.findViewById(R.id.Birthday);
        datePicker = fragmentView.findViewById(R.id.datePicker);
        done = fragmentView.findViewById(R.id.done);

        mSubscriptions = new CompositeSubscription();
        mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        datePicker.setVisibility(GONE);
        done.setVisibility(GONE);
        checkButton.setVisibility(GONE);

        Birthday.setOnClickListener(view -> {

            datePicker.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
        });
        done.setOnClickListener(view -> {
            date = datePicker.getMonth() + 1 + "-" + datePicker.getDayOfMonth() + "-" + datePicker.getYear();
            Birthday.setText(date);
            done.setVisibility(GONE);
            checkButton.setVisibility(View.VISIBLE);
            datePicker.setVisibility(GONE);
        });
        if (Validation.isConnected(Objects.requireNonNull(getActivity()))) {
            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .GetClinics()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog(getActivity());
        }
        spinnerClinic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String clinicId = Constant.GetClinicId(spinnerClinic.getSelectedItem().toString());
                if (Validation.isConnected(Objects.requireNonNull(getActivity()))) {
                    mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                            .GetClinicInfoByClinicId(clinicId)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponseGetClinicDetails, this::handleError));
                } else {
                    buildDialog(getActivity());
                }
            }

            private void handleResponseGetClinicDetails(GetClinicInfoByClinicIdResponse getClinicInfoByClinicIdResponse) {

                switch (getClinicInfoByClinicIdResponse.getResultCode()) {
                    case "1":
                        ClinicDaysSpinnerArray = new ArrayList<>();
                        for (WorkingDays workingDay :
                                getClinicInfoByClinicIdResponse.getData().getWorkingDays()) {
                            ClinicDaysSpinnerArray.add(workingDay.getDayName());
                        }
                        currentClinicData = new Data();
                        currentClinicData.setCity(getClinicInfoByClinicIdResponse.getData().getCity());
                        currentClinicData.setBuildingName(getClinicInfoByClinicIdResponse.getData().getBuildingName());
                        currentClinicData.setClinicId(getClinicInfoByClinicIdResponse.getData().getClinicId());
                        currentClinicData.setFloor(getClinicInfoByClinicIdResponse.getData().getFloor());
                        currentClinicData.setMobileNumber(getClinicInfoByClinicIdResponse.getData().getMobileNumber());
                        currentClinicData.setStreetName(getClinicInfoByClinicIdResponse.getData().getStreetName());


                        break;
                    case "0":
                        Constant.errorBuildDialog(getActivity(), getClinicInfoByClinicIdResponse.getResultMessageEn());
                        Log.e("getClinicInfoError(0)", getClinicInfoByClinicIdResponse.getResultMessageEn());
                        break;
                    case "2":
                        Toast.makeText(getActivity(), getClinicInfoByClinicIdResponse.getResultMessageEn(), Toast.LENGTH_SHORT).show();
                        Log.e("getClinicInfoError(2)", getClinicInfoByClinicIdResponse.getResultMessageEn());
                        break;
                }

            }

            private void handleError(Throwable throwable) {
                Log.e("GetClinicsThrowable", throwable.getMessage(), throwable);

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //TODO
            }
        });
        checkButton.setOnClickListener(view1 -> {
            if (currentClinicData.getClinicId() != null) {
                if (Validation.isConnected(Objects.requireNonNull(getActivity()))) {
                    if (!Birthday.getText().equals("M M - D D - Y Y Y Y")) {

                        GetEstimatedReservationTimeRequest getEstimatedReservationTimeRequest = new GetEstimatedReservationTimeRequest();
                        getEstimatedReservationTimeRequest.setClinicId(currentClinicData.getClinicId());
                        getEstimatedReservationTimeRequest.setDate(date);
                        getEstimatedReservationTimeRequest.setBookingType("Online");
                        getEstimatedReservationTimeRequest.setUserId(mSharedPreferences.getString(Constant.accessToken, ""));
                        mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                                .GetEstimatedReservationTime(getEstimatedReservationTimeRequest)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(this::handleResponse, this::handleError));
                    } else {
                        Constant.errorBuildDialog(getActivity(), "Fill the Date.");
                    }
                } else {
                    buildDialog(getActivity());
                }
            } else {
                buildDialog(getActivity());
            }
        });
        reserveButton.setOnClickListener(view1 -> {

            if (Validation.isConnected(Objects.requireNonNull(getActivity()))) {
                ConfirmReservationRequest confirmReservationRequest = new ConfirmReservationRequest();
                confirmReservationRequest.setCheckupNumber(globalGetEstimatedReservationTimeResponse.getData().getCheckupNumber());
                confirmReservationRequest.setBookingType("Offline");
                confirmReservationRequest.setClinicId(currentClinicData.getClinicId());
                confirmReservationRequest.setDate(date);
                confirmReservationRequest.setExpectedTime(globalGetEstimatedReservationTimeResponse.getData().getExpectedTime());
                //confirmReservationRequest.setUserId("");
                mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                        .ConfirmReservation(confirmReservationRequest)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
            } else {
                buildDialog(getActivity());
            }

        });

        return fragmentView;
    }

    private void handleResponse(ConfirmReservationResponse confirmReservationResponse) {
        switch (confirmReservationResponse.getResultCode()) {
            case "1":
                Toast.makeText(getActivity(), "Success Reservation.", Toast.LENGTH_SHORT).show();
                reserveButton.setVisibility(GONE);
                break;
            case "0":
                Constant.errorBuildDialog(getActivity(), confirmReservationResponse.getResultMessageEn());
                Log.e("EstimationError(0)", confirmReservationResponse.getResultMessageEn());
                break;
            case "2":
                Constant.errorBuildDialog(getActivity(), confirmReservationResponse.getResultMessageEn());
                Log.e("EstimationError(2)", confirmReservationResponse.getResultMessageEn());
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void handleResponse(GetEstimatedReservationTimeResponse getEstimatedReservationTimeResponse) {
        globalGetEstimatedReservationTimeResponse = getEstimatedReservationTimeResponse;
        switch (getEstimatedReservationTimeResponse.getResultCode()) {
            case "1":
                reserveButton.setVisibility(View.VISIBLE);
                checkButton.setVisibility(GONE);
                afterCheckDetails = "If you reserve your number will be: " + getEstimatedReservationTimeResponse.getData().getCheckupNumber()
                        + ".\nYou have to visit the clinic at :" + getEstimatedReservationTimeResponse.getData().getExpectedTime() + ".\nClinic Address: "
                        + currentClinicData.getCity() + ", " + currentClinicData.getStreetName() + ", " + currentClinicData.getBuildingName()
                        + ", floor: " + currentClinicData.getFloor() + ".";
                Constant.errorBuildDialog(getActivity(), afterCheckDetails);
                break;
            case "0":
                reserveButton.setVisibility(GONE);
                afterCheckDetails = getEstimatedReservationTimeResponse.getResultMessageEn();
                Constant.errorBuildDialog(getActivity(), afterCheckDetails);
                Log.e("EstimationError(0)", getEstimatedReservationTimeResponse.getResultMessageEn());
                break;
            case "2":
                reserveButton.setVisibility(GONE);
                Toast.makeText(getActivity(), getEstimatedReservationTimeResponse.getResultMessageEn(), Toast.LENGTH_SHORT).show();
                Log.e("EstimationError(2)", getEstimatedReservationTimeResponse.getResultMessageEn());
                break;
        }
    }

    private void handleResponse(GetClinicsResponse getClinicsResponse) {
        switch (getClinicsResponse.getResultCode()) {
            case "1":
                CitiesSpinnerArray = new ArrayList<>();
                CitiesIdArrayList = new ArrayList<>();
                for (com.company.doctorapplication.Models.GetClinics.Data clinic :
                        getClinicsResponse.getData()) {
                    CitiesSpinnerArray.add(clinic.getCity());
                    CitiesIdArrayList.add(clinic.getClinicId());
                }
                ArrayAdapter<String> CitiesAdapter = new ArrayAdapter<>(
                        Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, CitiesSpinnerArray);
                CitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerClinic.setAdapter(CitiesAdapter);

                break;
            case "0":
                Constant.errorBuildDialog(getActivity(), getClinicsResponse.getResultMessageEn());
                Log.e("DeleteClinicError(0)", getClinicsResponse.getResultMessageEn());
                break;
            case "2":
                Toast.makeText(getActivity(), getClinicsResponse.getResultMessageEn(), Toast.LENGTH_SHORT).show();
                Log.e("DeleteClinicError(2)", getClinicsResponse.getResultMessageEn());
                break;
        }

    }

    private void handleError(Throwable throwable) {
        Log.e("GetClinicsThrowable", throwable.getMessage(), throwable);

    }


}
