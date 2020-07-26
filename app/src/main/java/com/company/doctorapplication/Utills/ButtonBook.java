package com.company.doctorapplication.Utills;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class ButtonBook extends AppCompatButton {

    public ButtonBook(Context context) {
        super(context);
    }

    public ButtonBook(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontHelperbook.setCustomFont(this, context, attrs);
    }

    public ButtonBook(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        FontHelperbook.setCustomFont(this, context, attrs);
    }
}
