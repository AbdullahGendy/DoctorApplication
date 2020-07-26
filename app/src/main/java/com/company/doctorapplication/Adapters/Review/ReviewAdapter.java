package com.company.doctorapplication.Adapters.Review;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.doctorapplication.Models.Reviews.Data;
import com.company.doctorapplication.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {


    private Context context;
    private List<Data> itemList;


    public ReviewAdapter(Context context, List<Data> itemList) {
        this.context = context;
        this.itemList = itemList;


    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {

        holder.textView.setText(itemList.get(listPosition).getName());
        holder.reviewText.setText(itemList.get(listPosition).getComment());
        holder.doctorRate.setRating(Float.valueOf(Integer.valueOf(itemList.get(listPosition).getRate())));

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        TextView reviewText;
        SimpleRatingBar doctorRate;
        CircleImageView circleImageView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.reviewer_name);
            reviewText = itemView.findViewById(R.id.review_text);
            doctorRate = itemView.findViewById(R.id.doctor_rate);
            circleImageView = itemView.findViewById(R.id.reviewer_image);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
