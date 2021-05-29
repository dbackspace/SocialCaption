package com.xlteam.textonpicture.ui.edit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.ui.home.HomePageActivity;

public class CompleteDialogBuilder {

    private Dialog mDialog;
    private ImageView imgSavedPicture, imgBack, imgHome;

    public CompleteDialogBuilder(Context context, Bitmap bitmap) {
        mDialog = new Dialog(context, R.style.Theme_TextOnPicture);
        mDialog.setContentView(R.layout.dialog_complete);
        imgSavedPicture = mDialog.findViewById(R.id.image_saved_picture);
        imgBack = mDialog.findViewById(R.id.image_back);
        imgHome = mDialog.findViewById(R.id.image_home);
        imgSavedPicture.setImageBitmap(bitmap);
        imgBack.setOnClickListener(view -> mDialog.dismiss());

        imgHome.setOnClickListener(view -> {
            Intent intent = new Intent(context, HomePageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            ((Activity) context).finish();
        });

    }

    public Dialog build() {
        return mDialog;
    }
}
