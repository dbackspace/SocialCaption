package com.xlteam.socialcaption.ui.commondialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xlteam.socialcaption.R;

public class DialogSaveChangesBuilder {

    private final Dialog mDialog;

    public DialogSaveChangesBuilder(Context context, View.OnClickListener closeClick, View.OnClickListener cancelClick, View.OnClickListener saveClick) {
        mDialog = new Dialog(context, R.style.CustomDialog);
        mDialog.setContentView(R.layout.dialog_save_changes);
        mDialog.setCancelable(false);
        TextView tvClose = mDialog.findViewById(R.id.tvClose);
        TextView tvCancel = mDialog.findViewById(R.id.tvCancel);
        TextView tvSave = mDialog.findViewById(R.id.tvSave);

        tvClose.setOnClickListener(v -> {
            closeClick.onClick(v);
            mDialog.dismiss();
        });
        tvCancel.setOnClickListener(v -> {
            cancelClick.onClick(v);
            mDialog.dismiss();
        });
        tvSave.setOnClickListener(v -> {
            saveClick.onClick(v);
            mDialog.dismiss();
        });

    }

    public Dialog build() {
        return mDialog;
    }
}