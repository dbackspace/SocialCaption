package com.xlteam.textonpicture.ui.edit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.utility.customview.AutoFitEditText;
import com.xlteam.textonpicture.external.utility.customview.BlurImageView;
import com.xlteam.textonpicture.external.utility.utils.Utility;

public class DialogAddTextBuilder {
    private Dialog mDialog;
    private ImageView imgBack, imgDone, imgFont;
    private AutoFitEditText edtText;
    private RelativeLayout rlEmptyClick; // cho phép click vào vùng trống khi edit để lưu text
    private Context mContext;
    private BlurImageView blurBackgroundView;

    private boolean isEditOldText = false;

    interface Callback {
        void onSaveClicked(String text, boolean isEditOldText);
    }

    public DialogAddTextBuilder(Context context, Callback callback, String text, Bitmap background) {
        mContext = context;
        if (text != null) {
            isEditOldText = true;
        }
        mDialog = new Dialog(mContext, R.style.Theme_TextOnPicture);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_add_text);
        mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        imgBack = mDialog.findViewById(R.id.imgBack);
        edtText = mDialog.findViewById(R.id.edtText);
        imgDone = mDialog.findViewById(R.id.imgDone);
        blurBackgroundView = mDialog.findViewById(R.id.blur_background_view);
        rlEmptyClick = mDialog.findViewById(R.id.rlEmptyClick);

        //init default
        blurBackgroundView.setImageBitmap(background);
        blurBackgroundView.setBlur(5);
        imgBack.setOnClickListener(v -> mDialog.dismiss());
        imgDone.setOnClickListener(v -> {
            callback.onSaveClicked(edtText.getText().toString(), isEditOldText);
            mDialog.dismiss();
        });
        rlEmptyClick.setOnClickListener(v -> {
            callback.onSaveClicked(edtText.getText().toString(), isEditOldText);
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
