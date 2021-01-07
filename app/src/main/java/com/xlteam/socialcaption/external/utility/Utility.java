package com.xlteam.socialcaption.external.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.xlteam.socialcaption.R;

import de.cketti.mailto.EmailIntentBuilder;

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

    public static void sendEmailFeedback(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String[] emailList = {Constant.EMAIL_TEAM};
        EmailIntentBuilder.from(context)
                .to(emailList[0])
                .subject(context.getString(R.string.feedback_title))
                .body(preGetAppInfo(context, pInfo).toString())
                .start();
    }

    private static StringBuilder preGetAppInfo(Context context, PackageInfo pInfo) {
        StringBuilder info = new StringBuilder();
        if (pInfo != null) {
            info.append("Thông tin ứng dụng:\n- PACKAGE NAME: ").append(pInfo.packageName)
                    .append("\n- VERSION NAME: ").append(pInfo.versionName)
                    .append("\n- VERSION CODE: ").append(pInfo.versionCode)
                    .append("\n- VERSION OS: ").append(Build.VERSION.SDK_INT)
                    .append("\n- MODEL: ").append(Build.MODEL)
                    .append("\n- MANUFACTURER: ").append(Build.MANUFACTURER.toUpperCase());
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            info.append("\n- DISPLAY SIZE: ").append(dm.heightPixels).append('x').append(dm.widthPixels);
            info.append("\n\n").append(context.getString(R.string.feedback_msg)).append("\n");
        }
        return info;
    }
}
