package com.xlteam.socialcaption.ui.edit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.customview.AutoFitEditText;
import com.xlteam.socialcaption.external.utility.utils.Utility;

public class DialogAddTextBuilder {
    private Dialog mDialog;
    private ImageView imgBack, imgDone, imgFont;
    private AutoFitEditText edtText;
    private RelativeLayout rlEmptyClick; // cho phép click vào vùng trống khi edit để lưu text
    private Context mContext;

    interface Callback {
        void onSaveClicked(String text);
    }

    public DialogAddTextBuilder(Context context, Callback callback, String text) {
        mContext = context;
        mDialog = new Dialog(mContext, R.style.Theme_SocialCaption);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_add_text);
        mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        imgBack = mDialog.findViewById(R.id.imgBack);
        edtText = mDialog.findViewById(R.id.edtText);
        imgDone = mDialog.findViewById(R.id.imgDone);
        rlEmptyClick = mDialog.findViewById(R.id.rlEmptyClick);

        //init default
        imgBack.setOnClickListener(v -> mDialog.dismiss());
        imgDone.setOnClickListener(v -> {
            callback.onSaveClicked(edtText.getText().toString());
            mDialog.dismiss();
        });
        rlEmptyClick.setOnClickListener(v -> {
            callback.onSaveClicked(edtText.getText().toString());
            mDialog.dismiss();
        });
        if (!TextUtils.isEmpty(text)) edtText.setText(text);
        edtText.requestFocus();
        edtText.setShowSoftInputOnFocus(true);
//        InputMethodManager mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        mInputMethodManager.showSoftInput(edtText, 0);

        // khi dismiss thì callback onDismiss(), khi cancel callback cả onDismiss() và onCancel()
    }

    public Dialog build() {
        return mDialog;
    }

    public void showKeyboard() {
        if (!Utility.isKeyboardOpened(mContext)) {
            edtText.clearFocus();
            edtText.requestFocus();
            edtText.setSelectAllOnFocus(false);
            InputMethodManager mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.showSoftInput(edtText, 0);
        }
    }
}
