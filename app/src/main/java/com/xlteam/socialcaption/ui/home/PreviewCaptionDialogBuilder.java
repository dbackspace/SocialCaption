package com.xlteam.socialcaption.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.CommonCaption;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import java.util.Objects;

public class PreviewCaptionDialogBuilder {
    private Context mContext;
    private Dialog mDialog;
    private final ImageView mBtnBack;
    private final ImageView mBtnEdit;
    private final ImageView mBtnCopy;
    private final ImageView mBtnSave;
    private final ImageView mBtnShare;


    public PreviewCaptionDialogBuilder(Context context, CommonCaption commonCaption) {
        mDialog = new Dialog(context, R.style.Theme_SocialCaption);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_preview_caption);
        TextView tvContent = mDialog.findViewById(R.id.tv_content_caption);
        tvContent.setText(commonCaption.getContent());

        // init ImageView with findViewById
        mBtnBack = mDialog.findViewById(R.id.btn_preview_to_home);
        mBtnEdit = mDialog.findViewById(R.id.btn_preview_edit);
        mBtnCopy = mDialog.findViewById(R.id.btn_preview_copy);
        mBtnSave = mDialog.findViewById(R.id.btn_preview_save);
        mBtnShare = mDialog.findViewById(R.id.btn_preview_share);

        mBtnBack.setOnClickListener(v -> mDialog.dismiss());

        mBtnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCaptionActivity.class);
            intent.putExtra("CAPTION", commonCaption);
            context.startActivity(intent);
        });

        mBtnShare.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                String shareMessage = commonCaption.getContent();
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)));
            } catch (Exception e) {
                //e.toString();
            }
        });

    }

    public Dialog build() {
        return mDialog;
    }
}
