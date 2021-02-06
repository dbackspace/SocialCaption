package com.xlteam.socialcaption.ui.commondialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xlteam.socialcaption.R;

public class DialogSaveChangesBuilder {

    private final Dialog mDialog;

    private DialogSaveChangesBuilder(Context context) {
        mDialog = new Dialog(context, R.style.CustomDialog);
        mDialog.setContentView(R.layout.dialog_save_changes);
    }

    public static DialogSaveChangesBuilder create(Context context) {
        return new DialogSaveChangesBuilder(context);
    }

    public DialogSaveChangesBuilder setTitleMessage(String titleMessage) {
        TextView tvTitle = mDialog.findViewById(R.id.tv_title);
        tvTitle.setText(titleMessage);
        return this;
    }

    public DialogSaveChangesBuilder setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
        return this;
    }

    public DialogSaveChangesBuilder setFirstButton(View.OnClickListener closeClick, String btnName) {
        TextView tvClose = mDialog.findViewById(R.id.tvClose);
        tvClose.setVisibility(View.VISIBLE);
        tvClose.setText(btnName);
        tvClose.setOnClickListener(v -> {
            closeClick.onClick(v);
            mDialog.dismiss();
        });
        return this;
    }

    public DialogSaveChangesBuilder setSecondButton(View.OnClickListener cancelClick, String btnName) {
        TextView tvCancel = mDialog.findViewById(R.id.tvCancel);
        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.setText(btnName);
        tvCancel.setOnClickListener(v -> {
            cancelClick.onClick(v);
            mDialog.dismiss();
        });
        return this;
    }

    public DialogSaveChangesBuilder setThirdButton(View.OnClickListener saveClick, String btnName) {
        TextView tvSave = mDialog.findViewById(R.id.tvSave);
        tvSave.setVisibility(View.VISIBLE);
        tvSave.setText(btnName);
        tvSave.setOnClickListener(v -> {
            saveClick.onClick(v);
            mDialog.dismiss();
        });
        return this;
    }

    public Dialog build() {
        return mDialog;
    }
}