package com.xlteam.socialcaption.ui.home;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.CommonCaption;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class PreviewCaptionDialogBuilder {
    private Context mContext;
    private Dialog mDialog;
    private final ImageView mBtnBack;
    private final ImageView mBtnEdit;
    private final ImageView mBtnCopy;
    private final ImageView mBtnShare;
    private final RelativeLayout rlBackgroundForSaving;
    private final CommonCaption mCaption;

    public PreviewCaptionDialogBuilder(Context context, CommonCaption commonCaption) {
        mDialog = new Dialog(context, R.style.Theme_SocialCaption);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_preview_caption);
        TextView tvContent = mDialog.findViewById(R.id.tv_content_caption);
        tvContent.setText(commonCaption.getContent());
        rlBackgroundForSaving = mDialog.findViewById(R.id.rl_preview_background);

        mCaption = commonCaption;
        mContext = context;

        // init ImageView with findViewById
        mBtnBack = mDialog.findViewById(R.id.btn_preview_to_home);
        mBtnEdit = mDialog.findViewById(R.id.btn_preview_edit);
        mBtnCopy = mDialog.findViewById(R.id.btn_preview_copy);
        mBtnShare = mDialog.findViewById(R.id.btn_preview_share);

        mBtnBack.setOnClickListener(v -> mDialog.dismiss());

        mBtnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCaptionActivity.class);
            intent.putExtra("CAPTION", commonCaption);
            context.startActivity(intent);
        });

        mBtnShare.setOnClickListener(v -> {
            try {
                Bitmap bitmap = getBitmapFromView(rlBackgroundForSaving);
                String url= MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bitmap, "Ảnh hay và ý nghĩa", "");

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
                String shareMessage = commonCaption.getContent();
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)));
            } catch (Exception e) {
                //e.toString();
            }
        });

    }


    private void takePicture(View viewForPicture) {
        Log.e("TAG", Environment.getDataDirectory().toString());
        String fileName = mCaption.getId() + "_images.PNG";
        try {
            Bitmap bitmap = getBitmapFromView(viewForPicture);
            OutputStream fos;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = mContext.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
            } else {
                String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
                File image = new File(imagesDir, fileName);
                fos = new FileOutputStream(image);
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(mContext, "Ảnh đã được lưu ở thư mục DCIM", Toast.LENGTH_LONG).show();

            Objects.requireNonNull(fos).close();
        } catch (IOException e) {
            // Log Message
            Toast.makeText(mContext, "Xảy ra lỗi, lưu ảnh không thành công", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public Dialog build() {
        return mDialog;
    }
}
