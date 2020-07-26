package com.company.doctorapplication.activities.Home.Fragments.ReviewsFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.doctorapplication.Adapters.Review.ReviewAdapter;
import com.company.doctorapplication.Models.Reviews.Data;
import com.company.doctorapplication.Models.Reviews.ReviewsResponse;
import com.company.doctorapplication.Network.NetworkUtil;
import com.company.doctorapplication.R;
import com.company.doctorapplication.Utills.Constant;
import com.company.doctorapplication.Utills.Validation;

import java.util.ArrayList;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ReviewsFragment extends Fragment {
    private View view;
    private TextView textViewNoContent;
    private RecyclerView ReviewsRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviews, container, false);
        textViewNoContent = view.findViewById(R.id.text_view_no_content);
        textViewNoContent.setVisibility(View.GONE);
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        if (Validation.isConnected(Objects.requireNonNull(getActivity()))) {

            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .GetAllReviews()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.buildDialog(getActivity());
        }
        return view;
    }

    private void handleError(Throwable throwable) {
        Log.e("reviewsResponse(1)", Objects.requireNonNull(throwable.getMessage()));

    }

    private void handleResponse(ReviewsResponse reviewsResponse) {
            switch (reviewsResponse.getResultCode()) {
                case "1":
                    Log.e("reviewsResponse(1)", String.valueOf(reviewsResponse.getData().size()));
                    ArrayList<Data> data = new ArrayList<>();
                    if (reviewsResponse.getData() != null) {
                        for (Data dataResponse : reviewsResponse.getData()) {
                            data.add(new Data(dataResponse.getComment(), dataResponse.getRate(), dataResponse.getName()));
                        }
                        ReviewAdapter reviewAdapter = new ReviewAdapter(getContext(), data);
                        ReviewsRecyclerView = view.findViewById(R.id.reviews_recycler_view);
                        ReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        ReviewsRecyclerView.setAdapter(reviewAdapter);
                    } else {
                        ReviewsRecyclerView.setVisibility(View.GONE);
                        textViewNoContent.setVisibility(View.VISIBLE);
                    }

                    break;
                case "0":
                    Log.e("reviewsResponse(0)",reviewsResponse.getResultMessageEn());
                    // Toast.makeText(getActivity(), outOfStockResponse.getResultMessageEn() + "0", Toast.LENGTH_LONG).show();

                    break;
                case "2":
                    Log.e("reviewsResponse(2)",reviewsResponse.getResultMessageEn());
                    //Toast.makeText(getActivity(), outOfStockResponse.getResultMessageEn() + "2", Toast.LENGTH_LONG).show();
                    break;
            }
    }


}
