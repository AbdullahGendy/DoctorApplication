package com.company.doctorapplication.Utills;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class MyTextViewBold extends AppCompatTextView {

    public MyTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewBold(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Gotham Bold.otf");
        setTypeface(tf);

    }
}