package com.company.doctorapplication.activities.AddClinic;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.company.doctorapplication.Models.AddNewClinic.AddNewClinicRequest;
import com.company.doctorapplication.Models.AddNewClinic.AddNewClinicResponse;
import com.company.doctorapplication.Models.AddNewClinic.WorkingDays;
import com.company.doctorapplication.Network.NetworkUtil;
import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.Utills.Validation;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.company.doctorapplication.Utills.Constant.ErrorDialog;

public class AddClinicActivity extends AppCompatActivity {
    CheckBox checkboxFriday, checkboxThursday, checkboxWednesday, checkboxTuesday, checkboxMonday, checkboxSunday, checkboxSaturday;
    EditText editTextCity, editTextMobile, editTextBuildingName, editTextFloor, editTextStreetName;
    Spinner spinnerFrom, spinnerTo;
    Button buttonAddNewClinic;
    CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);
        mSubscriptions = new CompositeSubscription();
        checkboxFriday = findViewById(R.id.checkbox_friday);
        checkboxThursday = findViewById(R.id.checkbox_thursday);
        checkboxWednesday = findViewById(R.id.checkbox_wednesday);
        checkboxTuesday = findViewById(R.id.checkbox_tuesday);
        checkboxSunday = findViewById(R.id.checkbox_sunday);
        checkboxMonday = findViewById(R.id.checkbox_monday);
        checkboxSaturday = findViewById(R.id.checkbox_saturday);
        editTextCity = findViewById(R.id.edit_text_city);
        editTextMobile = findViewById(R.id.edit_text_mobile);
        editTextBuildingName = findViewById(R.id.edit_text_building_name);
        editTextFloor = findViewById(R.id.edit_text_floor);
        editTextStreetName = findViewById(R.id.edit_text_street_name);
        spinnerFrom = findViewById(R.id.spinner_from);
        spinnerTo = findViewById(R.id.spinner_to);
        buttonAddNewClinic = findViewById(R.id.button_add_new_clinic);




        buttonAddNewClinic.setOnClickListener(view -> {
            validate();

        });


    }

    private void validate() {
        if (!Validation.validateFields(editTextCity.getText().toString())) {
            ErrorDialog(this, "City is Empty");
        } else if (!Validation.validateFields(editTextMobile.getText().toString())) {
            ErrorDialog(this, "Mobile is Empty");
        } else if (!Validation.validateFields(editTextBuildingName.getText().toString())) {
            ErrorDialog(this, "Building Name is Empty");
        } else if (!Validation.validateFields(editTextFloor.getText().toString())) {
            ErrorDialog(this, "Floor is Empty");
        } else if (!Validation.validateFields(editTextStreetName.getText().toString())) {
            ErrorDialog(this, "Street Name is Empty");
        } else if (!checkboxFriday.isChecked() && !checkboxMonday.isChecked()
                && !checkboxSaturday.isChecked() && !checkboxTuesday.isChecked()
                && !checkboxWednesday.isChecked() && !checkboxThursday.isChecked()
                && !checkboxSunday.isChecked()) {
            ErrorDialog(this, "select at least on Day");
        } else {
            Add();
        }
    }

     void Add() {

        if (Validation.isConnected(this)) {
            AddNewClinicRequest addNewClinicRequest = new AddNewClinicRequest();
            addNewClinicRequest.setCity(editTextCity.getText().toString());
            addNewClinicRequest.setBuildingName(editTextBuildingName.getText().toString());
            addNewClinicRequest.setFloor(editTextFloor.getText().toString());
            addNewClinicRequest.setMobileNumber(editTextMobile.getText().toString());
            addNewClinicRequest.setStreetName(editTextStreetName.getText().toString());
            ArrayList<WorkingDays> workingDays = new ArrayList<>();

            if (checkboxSunday.isChecked())
                workingDays.add(new WorkingDays(spinnerFrom.getSelectedItem().toString(),
                        spinnerTo.getSelectedItem().toString(),
                        "Sunday"));
            if (checkboxMonday.isChecked())
                workingDays.add(new WorkingDays(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString(), "Monday"));
            if (checkboxTuesday.isChecked())
                workingDays.add(new WorkingDays(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString(), "Tuesday"));
            if (checkboxWednesday.isChecked())
                workingDays.add(new WorkingDays(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString(), "Wednesday"));
            if (checkboxThursday.isChecked())
                workingDays.add(new WorkingDays(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString(), "Thursday"));
            if (checkboxFriday.isChecked())
                workingDays.add(new WorkingDays(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString(), "Friday"));
            if (checkboxSaturday.isChecked())
                workingDays.add(new WorkingDays(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString(), "Saturday"));
            addNewClinicRequest.setWorkingDays(workingDays);

            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .AddClinic(addNewClinicRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleErrorLogin));
        } else {
            Constant.buildDialog(this);
        }
    }

    private void handleErrorLogin(Throwable throwable) {
        Log.e("AddNewClinicError", throwable.getMessage(), throwable);
    }

    private void handleResponse(AddNewClinicResponse addNewClinicResponse) {
        switch (addNewClinicResponse.getResultCode()) {
            case "1":
                Toast.makeText(this, "New clinic Has been Added Successfully.", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(this::finish, 2000);
                finish();
                break;
            case "0":
                Toast.makeText(this, "Some Data is incorrect,Try Again.", Toast.LENGTH_SHORT).show();

                Constant.ErrorDialog(this, "Username or password is incorrect, Please try again.");
                Log.e("AddNewClinicError(0)", addNewClinicResponse.getResultMessageEn());

                break;
            case "2":
                Toast.makeText(this, "Some Data is incorrect,Try Again.", Toast.LENGTH_SHORT).show();
                Log.e("AddNewClinicError(2)", addNewClinicResponse.getResultMessageEn());
                break;
        }


    }
}
