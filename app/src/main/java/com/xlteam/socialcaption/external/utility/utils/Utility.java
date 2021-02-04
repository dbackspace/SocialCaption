package com.xlteam.socialcaption.external.utility.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.logger.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import androidx.core.content.FileProvider;
import de.cketti.mailto.EmailIntentBuilder;

import static com.xlteam.socialcaption.external.utility.utils.Constant.FILE_PROVIDER_PATH;

public class Utility {
    private static final String TAG = "Utility";
    private static final long DOUBLE_CLICK_TIME_DELTA = 700; // VI time of Chooser is been longer than before.    200 -> 400 ms

    private static long sLastClickTime = 0;
    private static int sPrevId;
    private static int sPrevPosition;

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

    public static void vibrateAnimation(Context context, View view) {
        Animation vibrate = AnimationUtils.loadAnimation(context, R.anim.vibrate_shake);
        view.startAnimation(vibrate);
    }

    public static void vibratorNotify(Context context, int duration) {
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(duration);
    }

    public static int getDp(Context context, int value) {
        return (int) (context.getResources().getDisplayMetrics().density * value + 0.5f);
    }

    public static Uri getImageUri(Context context, Bitmap bitmap) {
        try {
            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs();
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        return FileProvider.getUriForFile(context, FILE_PROVIDER_PATH, newFile);
    }

    public static void shareImage(Context context, Uri imageUri) {
        if (imageUri == null) {
//            showSnackbar(getString(R.string.msg_save_image_to_share));
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, buildFileProviderUri(context, imageUri));
        context.startActivity(Intent.createChooser(intent, "Share image"));
    }

    private static Uri buildFileProviderUri(Context context, Uri uri) {
        return FileProvider.getUriForFile(context,
                FILE_PROVIDER_PATH,
                new File(uri.getPath()));
    }

    public static void setColorForTextView(TextView view, String color) {
        int intColor = Color.parseColor(color);
        view.setTextColor(intColor);
    }

    public static void setColorGradient(View view, int[] colors) {
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gd.setCornerRadius(0f);
        view.setBackground(gd);
    }

    public static boolean isValidClick(int id) { // view.getId()
        return isValidClick(id, -1, DOUBLE_CLICK_TIME_DELTA);
    }

    public static boolean isValidClick(int id, int position) { // view.hashCode()
        return position >= 0 && isValidClick(id, position, DOUBLE_CLICK_TIME_DELTA);
    }

    public static boolean isValidClick(int id, int position, long delay) {
        boolean bRet = true;
        long clickTime = now();

        if (sPrevId == id && (sPrevPosition == position)) {
            if (clickTime - sLastClickTime < delay) {
                bRet = false;
            }
        } else {
            Log.d("this", "isValidClick View is different");
        }

        sLastClickTime = clickTime;
        sPrevId = id;
        sPrevPosition = position;

        return bRet;
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

    public static boolean isKeyboardOpened(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();
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

    public static <T> boolean isEmpty(Collection<T> items) {
        return items == null || items.isEmpty();
    }

}
