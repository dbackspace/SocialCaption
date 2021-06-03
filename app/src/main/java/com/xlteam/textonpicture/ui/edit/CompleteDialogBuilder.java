package com.xlteam.textonpicture.ui.edit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.ui.commondialog.DialogSaveChangesBuilder;
import com.xlteam.textonpicture.ui.home.HomePageActivity;

public class CompleteDialogBuilder {

    private Dialog mDialog;
    private ImageView imgSavedPicture, imgBack, imgHome;
    private AdView mAdView;
    private TextView tvSavedPath;
    private LinearLayout layoutSuccess;
    private ProgressBar loading;

    public CompleteDialogBuilder(Context context, Bitmap bitmap, String savedPath) {
        mDialog = new Dialog(context, R.style.Theme_TextOnPicture);
        mDialog.setContentView(R.layout.dialog_complete);
        MobileAds.initialize(context, initializationStatus -> {
        });
        imgSavedPicture = mDialog.findViewById(R.id.image_saved_picture);
        imgBack = mDialog.findViewById(R.id.image_back);
        imgHome = mDialog.findViewById(R.id.image_home);
        mAdView = mDialog.findViewById(R.id.adView);
        tvSavedPath = mDialog.findViewById(R.id.tvSavedPath);
        layoutSuccess = mDialog.findViewById(R.id.layout_success);
        loading = mDialog.findViewById(R.id.loading);

        imgSavedPicture.setImageBitmap(bitmap);
        imgBack.setOnClickListener(view -> {
            mDialog.dismiss();
        });

        imgHome.setOnClickListener(view -> {
            Intent intent = new Intent(context, HomePageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            ((Activity) context).finish();
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mDialog.setOnDismissListener(dialogInterface -> {
            DialogSaveChangesBuilder.create(context).setTitleMessage("Bạn có muốn quay lại màn hình chỉnh sửa ảnh không?").setFirstButton(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }, "Đóng").setSecondButton(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }, "Quay lại").setThirdButton(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }, "Trang chủ").build().show();
            if (mAdView != null) mAdView.destroy();
        });

        tvSavedPath.setText(context.getString(R.string.file_path, savedPath));
        new Handler().postDelayed(() -> {
            loading.setVisibility(View.GONE);
            layoutSuccess.setVisibility(View.VISIBLE);
        }, 1000);
    }

    public Dialog build() {
        return mDialog;
    }
}
