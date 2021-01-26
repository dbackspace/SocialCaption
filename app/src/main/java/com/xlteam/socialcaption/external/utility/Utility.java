package com.xlteam.socialcaption.external.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xlteam.socialcaption.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import de.cketti.mailto.EmailIntentBuilder;

public class Utility {
    private static final String TAG = "Utility";

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

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    public static long now() {
        return System.currentTimeMillis();
    }


    public static File bitmapToFile(Bitmap bitmap, String filePathToSave) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(filePathToSave);
            file.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 100, bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return file; // it will return null
        }
    }

    public static void showDialogRequestPermission(Context context) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_request_permission);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.tvCancel).setOnClickListener(v1 -> dialog.dismiss());
        dialog.findViewById(R.id.tvAccept).setOnClickListener(v1 -> {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        });
        dialog.show();
    }

    public static void saveArrayList(Context context, ArrayList<String> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public static ArrayList<String> getArrayList(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
