package com.company.doctorapplication.Utills;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class ButtonBold extends AppCompatButton {

    public ButtonBold(Context context) {
        super(context);
    }

    public ButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontHelperbold.setCustomFont(this, context, attrs);
    }

    public ButtonBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        FontHelperbold.setCustomFont(this, context, attrs);
    }
}
