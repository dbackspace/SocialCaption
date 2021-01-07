package com.xlteam.socialcaption.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.Caption;

import java.util.Objects;

public class PreviewCaptionDialogBuilder {
    private Context mContext;
    private Dialog mDialog;

    public PreviewCaptionDialogBuilder(Context context, Caption caption) {
        mDialog = new Dialog(context, R.style.Theme_SocialCaption);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_preview_caption);
        TextView tvContent = mDialog.findViewById(R.id.tv_content_caption);
        tvContent.setText(caption.getContent());
    }

    public Dialog build() {
        return mDialog;
    }
}
