package com.xlteam.socialcaption.ui.edit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.Utility;

import java.util.Objects;

public class DialogAddTextBuilder {
    private Context mContext;
    private Dialog mDialog;
    private ImageView imgGravity, imgBack, imgFont;
    private EditText edtText;
    private TextView tvSave;
    private RecyclerView rvFont;

    private int mGravityText;
    private int mNumberFont;

    public DialogAddTextBuilder(Context context) {
        mDialog = new Dialog(context, R.style.Theme_SocialCaption);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_add_text);
        imgGravity = mDialog.findViewById(R.id.imgGravity);
        imgBack = mDialog.findViewById(R.id.imgBack);
        edtText = mDialog.findViewById(R.id.edtText);
        tvSave = mDialog.findViewById(R.id.tvSave);
        rvFont = mDialog.findViewById(R.id.rvFont);
        imgFont = mDialog.findViewById(R.id.imgFont);

        imgBack.setOnClickListener(v -> mDialog.dismiss());

        tvSave.setOnClickListener(v -> mDialog.dismiss());

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
        imgFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvFont.setVisibility(View.VISIBLE);
            }
        });
        rvFont.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        rvFont.setAdapter(new FontAdapter(context, new FontAdapter.FontSelectCallback() {
            @Override
            public void selectFont(int numberFont) {
                mNumberFont = numberFont;
            }
        }));
    }

    public Dialog build() {
        return mDialog;
    }
}
