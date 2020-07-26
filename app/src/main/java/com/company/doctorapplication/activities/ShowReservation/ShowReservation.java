package com.company.doctorapplication.activities.ShowReservation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.doctorapplication.Adapters.Review.Appointment.AppointmentAdapter;
import com.company.doctorapplication.Models.Appointments.GetAppointmentsResponseDataModel;
import com.company.doctorapplication.Models.Appointments.GetAppointmentsResponseModel;
import com.company.doctorapplication.Network.NetworkUtil;
import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.Utills.Validation;

import java.util.ArrayList;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ShowReservation extends AppCompatActivity {

    private TextView textViewNoContent;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reservation);
        textViewNoContent = findViewById(R.id.text_view_no_content);
        textViewNoContent.setVisibility(View.GONE);
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        if (Validation.isConnected(ShowReservation.this)) {
            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .GetAppointments()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.buildDialog(this);
        }
    }

    private void handleError(Throwable throwable) {
        Log.e("AppointmentsResponseErr", Objects.requireNonNull(throwable.getMessage()));

    }

    private void handleResponse(GetAppointmentsResponseModel getAppointmentsResponseModel) {
        switch (getAppointmentsResponseModel.getResultCode()) {
            case "1":
                Log.e("reviewsResponse(1)", String.valueOf(getAppointmentsResponseModel.getData().size()));
                ArrayList<GetAppointmentsResponseDataModel> data = new ArrayList<>();
                if (getAppointmentsResponseModel.getData() != null) {
                    AppointmentAdapter appointmentAdapter = new AppointmentAdapter(ShowReservation.this, getAppointmentsResponseModel.getData());
                    recyclerView = findViewById(R.id.Appointment_recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(appointmentAdapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textViewNoContent.setVisibility(View.VISIBLE);
                }

                break;
            case "0":
                Log.e("Appointments(0)", getAppointmentsResponseModel.getResultMessageEn());
                // Toast.makeText(getActivity(), outOfStockResponse.getResultMessageEn() + "0", Toast.LENGTH_LONG).show();

                break;
            case "2":
                Log.e("Appointments(1))", getAppointmentsResponseModel.getResultMessageEn());
                //Toast.makeText(getActivity(), outOfStockResponse.getResultMessageEn() + "2", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
