package com.xlteam.socialcaption.ui.edit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.datasource.ColorDataSource;
import com.xlteam.socialcaption.external.datasource.FontDataSource;
import com.xlteam.socialcaption.external.utility.Utility;
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

    private int mGravityText;
    private int mNumberFont, mNumberColor, mNumberBg;

    public DialogAddTextBuilder(Context context) {
        mDialog = new Dialog(context, R.style.Theme_SocialCaption);
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
        imgDone.setOnClickListener(v -> mDialog.cancel());

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
        imgFont.setOnClickListener(v -> {
            rvFont.setVisibility(View.VISIBLE);
            rvColor.setVisibility(View.GONE);
        });
        lnColor.setOnClickListener(v -> {
            rvColor.setVisibility(View.VISIBLE);
            rvFont.setVisibility(View.GONE);
        });
        lnBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNumberBg == 0) {
                    mNumberBg = 1;
                    Utility.setColorForView(tvBg, "#80FFFFFF");
                    Utility.setColorForTextView(tvBg, "#FFFFFFFF");
                } else if (mNumberBg == 1) {
                    mNumberBg = 2;
                    Utility.setColorForView(tvBg, "#FFFFFFFF");
                    Utility.setColorForTextView(tvBg, "#FF000000");
                } else {
                    mNumberBg = 0;
                    Utility.setColorForView(tvBg, "#00FFFFFF");
                    Utility.setColorForTextView(tvBg, "#FFFFFFFF");
                }
            }
        });

        rvFont.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        rvFont.setAdapter(new FontAdapter(context, numberFont -> {
            mNumberFont = numberFont;
            Font font = FontDataSource.getInstance().getAllFonts().get(numberFont);
            Typeface type = Typeface.createFromAsset(context.getAssets(), "font/" + font.getFont());
            edtText.setTypeface(type);
        }));

        rvColor.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        rvColor.setAdapter(new ColorAdapter(color -> {
            mNumberColor = color;
            String colorString = ColorDataSource.getInstance().getAllDataMini().get(color);
            Utility.setColorForView(viewColor, colorString);
            Utility.setColorForTextView(edtText, colorString);
        }));

    }

    public Dialog build() {
        return mDialog;
    }
}
