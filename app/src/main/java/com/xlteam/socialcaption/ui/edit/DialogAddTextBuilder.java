package com.xlteam.socialcaption.ui.edit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.datasource.ColorDataSource;
import com.xlteam.socialcaption.external.datasource.FontDataSource;
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.model.Font;

import java.util.Objects;

public class DialogAddTextBuilder {
    private Dialog mDialog;
    private ImageView imgGravity, imgBack, imgDone, imgFont;
    private EditText edtText;
    private TextView tvBg;
    private RecyclerView rvFont, rvColor;
    private LinearLayout lnColor, lnBg;
    private View viewColor;
    private Context mContext;
    private int mGravityText;
    private int mNumberFont, mNumberColor, mNumberBg;
    private BackgroundColorSpan span;

    interface SavedCallback {
        void onSaveClicked(EditText editText, BackgroundColorSpan span);
    }


    public DialogAddTextBuilder(SavedCallback savedCallback, ItemText itemText) {
        mContext = (Context) savedCallback;
        mDialog = new Dialog(mContext, R.style.Theme_SocialCaption);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_add_text);
        imgGravity = mDialog.findViewById(R.id.imgGravity);
        imgBack = mDialog.findViewById(R.id.imgBack);
        edtText = mDialog.findViewById(R.id.edtText);
        imgDone = mDialog.findViewById(R.id.imgDone);
        rvFont = mDialog.findViewById(R.id.rvFont);
        rvColor = mDialog.findViewById(R.id.rvColor);
        imgFont = mDialog.findViewById(R.id.imgFont);
        lnColor = mDialog.findViewById(R.id.lnColor);
        viewColor = mDialog.findViewById(R.id.viewColor);
        lnBg = mDialog.findViewById(R.id.lnBg);
        tvBg = mDialog.findViewById(R.id.tvBg);

        //init default
        mNumberFont = 1; // tạm
        mNumberColor = 1; // tạm
        //
        Utility.setColorForView(viewColor, ColorDataSource.getInstance().getAllDataMini().get(mNumberColor));
        imgBack.setOnClickListener(v -> mDialog.cancel());
        imgDone.setOnClickListener(v -> {
            savedCallback.onSaveClicked(edtText, span);
            mDialog.cancel();
        });

        imgGravity.setOnClickListener(v -> {
            if (mGravityText == Gravity.CENTER) {
                mGravityText = Gravity.END;
                imgGravity.setImageResource(R.drawable.ic_align_right);
                span.setAlignment(Constant.ALIGN_END);
            } else if (mGravityText == Gravity.END) {
                mGravityText = Gravity.START;
                imgGravity.setImageResource(R.drawable.ic_align_left);
                span.setAlignment(Constant.ALIGN_START);
            } else {
                mGravityText = Gravity.CENTER;
                imgGravity.setImageResource(R.drawable.ic_align_center);
                span.setAlignment(Constant.ALIGN_CENTER);
            }
            edtText.setGravity(mGravityText);
        });
        imgFont.setOnClickListener(v -> {
            rvFont.setVisibility(View.VISIBLE);
            rvColor.setVisibility(View.GONE);
        });
        lnColor.setOnClickListener(v -> {
            rvColor.setVisibility(View.VISIBLE);
            rvFont.setVisibility(View.GONE);
        });
        lnBg.setOnClickListener(v -> {
            if (mNumberBg == Constant.BACKGROUND_SPAN_TRANSPARENT) {
                //blur
                mNumberBg = Constant.BACKGROUND_SPAN_BLUR;
                Utility.setColorForView(tvBg, "#80FFFFFF");
                Utility.setColorForTextView(tvBg, "#FFFFFFFF");
                Utility.setColorForTextView(edtText, "#FFFFFFFF");
                String colorString = ColorDataSource.getInstance().getAllDataMini().get(mNumberColor);
                span.setColor("#80" + colorString.substring(3));
            } else if (mNumberBg == Constant.BACKGROUND_SPAN_BLUR) {
                //color solid
                mNumberBg = Constant.BACKGROUND_SPAN_FULL_SOLID;
                Utility.setColorForView(tvBg, "#FFFFFFFF");
                Utility.setColorForTextView(tvBg, "#FF000000");
                Utility.setColorForTextView(edtText, "#FFFFFFFF");
                String colorString = ColorDataSource.getInstance().getAllDataMini().get(mNumberColor);
                span.setColor(colorString);
            } else {
                //transparent
                mNumberBg = Constant.BACKGROUND_SPAN_TRANSPARENT;
                Utility.setColorForView(tvBg, "#00FFFFFF");
                Utility.setColorForTextView(tvBg, "#FFFFFFFF");
                String colorString = ColorDataSource.getInstance().getAllDataMini().get(mNumberColor);
                Utility.setColorForTextView(edtText, colorString);
                span.setColor("#00FFFFFF");
            }
        });

        rvFont.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        rvFont.setAdapter(new FontAdapter(mContext, numberFont -> {
            mNumberFont = numberFont;
            Font font = FontDataSource.getInstance().getAllFonts().get(numberFont);
            Typeface type = Typeface.createFromAsset(mContext.getAssets(), "font/" + font.getFont());
            edtText.setTypeface(type);
        }));

        rvColor.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        rvColor.setAdapter(new ColorAdapter(color -> {
            mNumberColor = color;
            String colorString = ColorDataSource.getInstance().getAllDataMini().get(color);
            Utility.setColorForView(viewColor, colorString);
            if (mNumberBg == Constant.BACKGROUND_SPAN_BLUR) {
                span.setColor("#80" + colorString.substring(3)); //loại bỏ #FF, thay bằng #80
            } else if (mNumberBg == Constant.BACKGROUND_SPAN_FULL_SOLID) {
                span.setColor(colorString);
            } else {
                Utility.setColorForTextView(edtText, colorString);
            }
        }));

        // setup for item text info if not null
//        if (itemText != null) {
//            edtText.setText(itemText.getText());
//            edtText.setTextColor(itemText.getColor());
//            edtText.setTextSize(itemText.getSize());
//            edtText.setTextAlignment(itemText.getAlignment());
//        }

        int padding = Utility.getDp(mContext, 8);
        int radius = Utility.getDp(mContext, 5);
        span = new BackgroundColorSpan("#FFFFFF", (float) padding, (float) radius);

        edtText.setShadowLayer(padding, 0f, 0f, 0);
        edtText.setPadding(padding, padding, padding, padding);
        edtText.setFocusableInTouchMode(true);
        edtText.setFocusable(true);
        edtText.setCursorVisible(true);
        int position = edtText.getText().length();
        Editable editObj = edtText.getText();
        Selection.setSelection(editObj, position);
        showKeyboard();
        edtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                s.setSpan(span, 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });
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

/*    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isMobileKeyboard = Utility.isMobileKeyboardAvailable(mContext);
        if (Utility.isKeyboardOpened(mContext)) {
            if (isMobileKeyboard) {
                imm.toggleSoftInput(0, 0);
            } else {
                imm.hideSoftInputFromWindow(edtText.getWindowToken(), 0);
            }
        }
    }*/
}
