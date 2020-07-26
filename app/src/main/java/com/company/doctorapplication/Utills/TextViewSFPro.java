package com.company.doctorapplication.Utills;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class TextViewSFPro extends AppCompatTextView {

    public TextViewSFPro(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewSFPro(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewSFPro(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "SF-Pro-Text-Regular.ttf");
        setTypeface(tf);

    }
}