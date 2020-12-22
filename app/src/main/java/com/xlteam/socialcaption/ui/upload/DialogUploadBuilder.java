package com.xlteam.socialcaption.ui.upload;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.xlteam.socialcaption.R;

import java.util.Objects;

public class DialogUploadBuilder {
    private Dialog mDialog;

    public DialogUploadBuilder(Context context) {
        mDialog = new Dialog(context, R.style.Theme_SocialCaption);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_upload);
    }

    public DialogUploadBuilder() {

    }

    public static DialogUploadBuilder newInstance() {
        return new DialogUploadBuilder();
    }


    public Dialog build() {
        return mDialog;
    }
}
