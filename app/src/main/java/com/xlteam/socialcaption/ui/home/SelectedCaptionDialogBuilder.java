package com.xlteam.socialcaption.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.Constant;
import com.xlteam.socialcaption.model.CommonCaption;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

public class SelectedCaptionDialogBuilder {
    private BottomSheetDialog mDialog;
    private LinearLayout btnEdit, btnCopy, btnShare;

    public SelectedCaptionDialogBuilder(Context context, CommonCaption commonCaption) {
        mDialog = new BottomSheetDialog(context, R.style.CustomDialog);
        mDialog.setContentView(R.layout.dialog_selected_caption);
        btnEdit = mDialog.findViewById(R.id.btn_preview_edit);
        btnCopy = mDialog.findViewById(R.id.btn_preview_copy);
        btnShare = mDialog.findViewById(R.id.btn_preview_share);

        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditCaptionActivity.class);
            intent.putExtra(Constant.EXTRA_CAPTION, commonCaption);
            context.startActivity(intent);
        });

        btnCopy.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", commonCaption.getContent());
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, R.string.copied, Toast.LENGTH_SHORT).show();
            }
        });

        btnShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, commonCaption.getContent());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, context.getString(R.string.send_friend));
            context.startActivity(shareIntent);
        });
    }

    public BottomSheetDialog build() {
        return mDialog;
    }
}
