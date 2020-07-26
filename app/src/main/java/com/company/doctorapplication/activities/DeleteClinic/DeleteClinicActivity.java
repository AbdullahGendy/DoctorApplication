package com.company.doctorapplication.activities.DeleteClinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.company.doctorapplication.Models.DeleteClinic.DeleteClinicResponse;
import com.company.doctorapplication.Models.GetClinics.Data;
import com.company.doctorapplication.Models.GetClinics.GetClinicsResponse;
import com.company.doctorapplication.Network.NetworkUtil;
import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.Utills.Validation;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.company.doctorapplication.Utills.Constant.CitiesIdArrayList;
import static com.company.doctorapplication.Utills.Constant.CitiesSpinnerArray;
import static com.company.doctorapplication.Utills.Constant.GetClinicId;

public class DeleteClinicActivity extends AppCompatActivity {
    Spinner spinnerClinic;
    Button buttonDeleteClinic;
    CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_clinic);
        spinnerClinic = findViewById(R.id.spinner_clinic);
        buttonDeleteClinic = findViewById(R.id.button_delete_clinic);
        mSubscriptions = new CompositeSubscription();
        if (Validation.isConnected(this)) {
            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .GetClinics()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.buildDialog(this);
        }
        buttonDeleteClinic.setOnClickListener(view -> {
            if (Validation.isConnected(this)) {
                mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                        .DeleteClinic(GetClinicId(spinnerClinic.getSelectedItem().toString()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponseDelete, this::handleError));
            } else {
                Constant.buildDialog(this);
            }
        });

    }

    private void handleResponseDelete(DeleteClinicResponse deleteClinicResponse) {
        switch (deleteClinicResponse.getResultCode()) {
            case "1":
                Toast.makeText(this, "Clinic Has been deleted Successfully", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(this::finish, 1000);
                finish();
                break;
            case "0":
                Constant.ErrorDialog(this, deleteClinicResponse.getResultMessageEn());
                Log.e("DeleteClinicError(0)", deleteClinicResponse.getResultMessageEn());
                break;
            case "2":
                Toast.makeText(this, deleteClinicResponse.getResultMessageEn(), Toast.LENGTH_SHORT).show();
                Log.e("DeleteClinicError(2)", deleteClinicResponse.getResultMessageEn());
                break;
        }

    }

    private void handleError(Throwable throwable) {
        Log.e("DeleteClinicThrowable", throwable.getMessage(), throwable);

    }

    private void handleResponse(GetClinicsResponse getClinicsResponse) {
        switch (getClinicsResponse.getResultCode()) {
            case "1":
                CitiesSpinnerArray = new ArrayList<>();
                CitiesIdArrayList = new ArrayList<>();
                for (Data clinic :
                        getClinicsResponse.getData()) {
                    CitiesSpinnerArray.add(clinic.getCity());
                    CitiesIdArrayList.add(clinic.getClinicId());
                }
                ArrayAdapter<String> CitiesAdapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_spinner_item, CitiesSpinnerArray);
                CitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerClinic.setAdapter(CitiesAdapter);

                break;
            case "0":
                Constant.ErrorDialog(this, getClinicsResponse.getResultMessageEn());
                Log.e("DeleteClinicError(0)", getClinicsResponse.getResultMessageEn());
                break;
            case "2":
                Toast.makeText(this, getClinicsResponse.getResultMessageEn(), Toast.LENGTH_SHORT).show();
                Log.e("DeleteClinicError(2)", getClinicsResponse.getResultMessageEn());
                break;
        }

    }
}
