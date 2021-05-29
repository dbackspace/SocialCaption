package com.xlteam.textonpicture.ui.edit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;

import com.xlteam.textonpicture.R;

public class CompleteDialogBuilder {

    private Dialog mDialog;

    public CompleteDialogBuilder(Context context, Bitmap bitmap) {
        mDialog = new Dialog(context, R.style.Theme_TextOnPicture);
        mDialog.setContentView(R.layout.dialog_save_changes);
    }

    public Dialog build() {
        return mDialog;
    }
}
