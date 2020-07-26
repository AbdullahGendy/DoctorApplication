package com.company.doctorapplication.Adapters.Review.Appointment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.doctorapplication.Models.Appointments.GetAppointmentsResponseDataModel;
import com.company.doctorapplication.R;

import java.util.List;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {


    private Context context;
    private List<GetAppointmentsResponseDataModel> itemList;


    public AppointmentAdapter(Context context, List<GetAppointmentsResponseDataModel> itemList) {
        this.context = context;
        this.itemList = itemList;


    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, parent, false);
        return new AppointmentAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final AppointmentAdapter.ViewHolder holder, final int listPosition) {

        holder.textViewName.setText("name: " + itemList.get(listPosition).getName());
        holder.textViewAddress.setText("Address: " + itemList.get(listPosition).getCity());
        holder.textViewTime.setText("Time: " + itemList.get(listPosition).getExpectedTime());
        holder.textViewDate.setText("Date: " + itemList.get(listPosition).getDate().substring(0,10));
        holder.textViewType.setText("Type: " + itemList.get(listPosition).getBookingType());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewAddress, textViewName, textViewTime, textViewType, textViewDate;


        ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewAddress = itemView.findViewById(R.id.text_view_address);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewDate = itemView.findViewById(R.id.text_view_date);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
