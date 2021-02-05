package com.xlteam.socialcaption.ui.edit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
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
    private ImageView imgGravity, imgBack, imgDone, imgFont;
    private AutoFitEditText edtText;
    private RelativeLayout rlEmptyClick; // cho phép click vào vùng trống khi edit để lưu text
    private Context mContext;
    private int mGravityText;

    interface SavedCallback {
        void onSaveClicked(String text, int gravity);
    }


    public DialogAddTextBuilder(SavedCallback savedCallback, ItemText itemText) {
        mContext = (Context) savedCallback;
        mDialog = new Dialog(mContext, R.style.Theme_SocialCaption);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_add_text);
        imgGravity = mDialog.findViewById(R.id.imgGravity);
        imgBack = mDialog.findViewById(R.id.imgBack);
        edtText = mDialog.findViewById(R.id.edtText);
        imgDone = mDialog.findViewById(R.id.imgDone);
        rlEmptyClick = mDialog.findViewById(R.id.rlEmptyClick);

        //init default
        imgBack.setOnClickListener(v -> mDialog.cancel());
        imgDone.setOnClickListener(v -> {
            savedCallback.onSaveClicked(edtText.getText().toString(), mGravityText);
            mDialog.cancel();
        });
        rlEmptyClick.setOnClickListener(v -> {
            savedCallback.onSaveClicked(edtText.getText().toString(), mGravityText);
            mDialog.cancel();
        });

        imgGravity.setOnClickListener(v -> {
            if (mGravityText == Gravity.CENTER) {
                mGravityText = Gravity.END;
                imgGravity.setImageResource(R.drawable.ic_align_right);
            } else if (mGravityText == Gravity.END) {
                mGravityText = Gravity.START;
                imgGravity.setImageResource(R.drawable.ic_align_left);
            } else {
                mGravityText = Gravity.CENTER;
                imgGravity.setImageResource(R.drawable.ic_align_center);
            }
            edtText.setGravity(mGravityText);
        });
        mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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
