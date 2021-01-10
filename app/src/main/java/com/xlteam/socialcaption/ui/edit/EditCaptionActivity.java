package com.xlteam.socialcaption.ui.edit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.datasource.ColorDataSource;
import com.xlteam.socialcaption.external.datasource.FontDataSource;
import com.xlteam.socialcaption.external.utility.PrefUtils;
import com.xlteam.socialcaption.external.utility.UiUtils;
import com.xlteam.socialcaption.model.CommonCaption;
import com.xlteam.socialcaption.model.Font;
import com.xlteam.socialcaption.ui.edit.adapter.DetailForAlignAdapter;
import com.xlteam.socialcaption.ui.edit.adapter.DetailForColorAdapter;
import com.xlteam.socialcaption.ui.edit.adapter.DetailForFontAdapter;
import com.xlteam.socialcaption.ui.edit.adapter.ToolColorAlignFontAdapter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.EDITOR;
import static com.xlteam.socialcaption.external.utility.Constant.EDITOR_CAU_THOAI_SIZE;

public class EditCaptionActivity extends AppCompatActivity
        implements DetailForFontAdapter.DetailForFontCallback, DetailForColorAdapter.ColorSelectCallback, DetailForAlignAdapter.AlignSelectCallback {
    private static final int RESULT_LOAD_IMG = 1;
    private List<Font> mFonts;
    private ArrayList<String> mColors;
    private RecyclerView rvToolPrimary;
    private RecyclerView rvToolSecondary;
    private ImageView mBtnBackAndCancel;
    private TextView mBtnSaveImage;
    private EditText mEdtCaption;
    private CommonCaption mCommonCaption;
    private ImageView mImgBackground;
    public static final int[] TOOL_FOR_COLOR_FONT_ALIGN_POST = {R.drawable.ic_baseline_image_24, R.drawable.ic_baseline_font_download_24, R.drawable.ic_baseline_format_color_text_24, R.drawable.ic_baseline_format_align_justify_24, R.drawable.ic_baseline_format_size_24};
    private int textSize; // 0 -> 4
    private boolean isChanged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_caption);

        mBtnBackAndCancel = findViewById(R.id.btn_edit_back_and_cancel);
        mEdtCaption = findViewById(R.id.tv_content_caption);
        mBtnSaveImage = findViewById(R.id.tv_edit_save);
        rvToolPrimary = findViewById(R.id.rv_edit_tool_primary);
        rvToolSecondary = findViewById(R.id.rv_edit_tool_secondary);
        mImgBackground = findViewById(R.id.img_edit_background);

        // chua co thay doi gi
        isChanged = false;

        // get Caption
        mCommonCaption = getIntent().getExtras().getParcelable("CAPTION");
        mEdtCaption.setText(mCommonCaption.getContent());

        // init adapter
        // color background
//        DetailForToolsAdapter adapterBgr = new DetailForToolsAdapter(GRADIENT_BACKGROUND_ARRAY, position -> imgBackgroundForCaption.setImageResource(GRADIENT_BACKGROUND_ARRAY[position]));

        // font
        mFonts = FontDataSource.getInstance().getAllFonts();
        DetailForFontAdapter adapterFont = new DetailForFontAdapter(this, mFonts, this);

        // color text
        mColors = ColorDataSource.getInstance().getAllDataMini();
        DetailForColorAdapter adapterColor = new DetailForColorAdapter(this);

        // text size
        textSize = PrefUtils.getInt(this, EDITOR, EDITOR_CAU_THOAI_SIZE, 1);

        // align
        DetailForAlignAdapter adapterAlign = new DetailForAlignAdapter(this);

        // set recycler view for tool (align, font, color, ...)
        rvToolSecondary.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ToolColorAlignFontAdapter toolAdapter = new ToolColorAlignFontAdapter(TOOL_FOR_COLOR_FONT_ALIGN_POST, position -> {
            switch (position) {
                case 1:
                    // Font caption
                    rvToolSecondary.setAdapter(adapterFont);
                    rvToolSecondary.scrollToPosition(position);
                    break;
                case 2:
                    // Color for font caption
                    rvToolSecondary.setAdapter(adapterColor);
                    break;
                case 3:
                    // Align for caption
                    rvToolSecondary.setAdapter(adapterAlign);
                    break;
                case 4:
                    textSize++;
                    updateTextSizeEditTet();
                    // Set recycler view for detail of tools
                case 0:
                    // image background
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                    break;
                default:
                    // color background
//                    rvToolSecondary.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//                    rvToolSecondary.setAdapter(adapterBgr);
            }
        });

        rvToolPrimary.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvToolPrimary.setAdapter(toolAdapter);

        // focus editCaption and show keyboard
        mEdtCaption.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEdtCaption, InputMethodManager.SHOW_IMPLICIT);

        mBtnBackAndCancel.setOnClickListener((v) -> {
            finish();
        });
    }

    @Override
    public void onFontClicked(int position) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/" + mFonts.get(position).getFont());
        mEdtCaption.setTypeface(typeface);
    }

    @Override
    public void selectColor(int color) {
        UiUtils.setColorForTextView(mEdtCaption, mColors.get(color));
    }

    @Override
    public void selectAlign(int Align) {
        Log.e("TEST ", Align + "");
        switch (Align) {
            case 0:
                mEdtCaption.setGravity(Gravity.FILL);
                break;
            case 1:
                mEdtCaption.setGravity(Gravity.CENTER);
                break;
            case 2:
                mEdtCaption.setGravity(Gravity.START);
                break;
            case 3:
                mEdtCaption.setGravity(Gravity.END);
                break;
        }
    }

    private void updateTextSizeEditTet() {
        textSize %= 5;
        switch (textSize) {
            case 0:
                mEdtCaption.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                break;
            case 1:
                mEdtCaption.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
                break;
            case 2:
                mEdtCaption.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
                break;
            case 3:
                mEdtCaption.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 34);
                break;
            case 4:
                mEdtCaption.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
                break;
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mImgBackground.setImageBitmap(selectedImage);
                isChanged = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
