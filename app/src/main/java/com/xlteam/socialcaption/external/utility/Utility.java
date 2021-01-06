package com.xlteam.socialcaption.external.utility;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.widget.TextView;

public class Utility {
    public static void setColorForView(View view, String color) {
        int intColor = Color.parseColor(color);
        Drawable drawable = view.getBackground();
        if (drawable instanceof ShapeDrawable) {
            ((ShapeDrawable) drawable).getPaint().setColor(intColor);
        } else if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setColor(intColor);
        } else if (drawable instanceof ColorDrawable) {
            ((ColorDrawable) drawable).setColor(intColor);
        }
    }

    public static void setColorForTextView(TextView view, String color) {
        int intColor = Color.parseColor(color);
        view.setTextColor(intColor);
    }
}
