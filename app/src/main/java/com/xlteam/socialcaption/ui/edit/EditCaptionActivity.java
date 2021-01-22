package com.xlteam.socialcaption.ui.edit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.UserCaptionRepository;
import com.xlteam.socialcaption.external.utility.Constant;
import com.xlteam.socialcaption.model.CommonCaption;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class EditCaptionActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 1;
    private ImageView imgBack;
    private TextView tvDone;
    private CommonCaption mCommonCaption;
    private ImageView mImgBackground;
    private LinearLayout layoutMenu;

    // set default for tool
    private int mTextSizeDefault; // default = 1    [0 -> 4]
    private int mFontDefault = 6;
    private int mColorTextDefault = 0;
    private int mAlignTextDefault = 0;
    private String mPathImg = "";
    private UserCaptionRepository mRepository;
    private int flipCurrent = 0;
    private AdView mAdView;
    public static final int PICK_IMAGE = 1;

    // check change
    private boolean isPickedPicture = false;
    private boolean isCropped = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_caption);
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Intent intent = getIntent();
        mCommonCaption = (CommonCaption) intent.getSerializableExtra(Constant.EXTRA_CAPTION);
        if (mCommonCaption != null) {
            //init old data
        } else {
            //show dialog
        }
        findViewById();
        tvDone.setOnClickListener(v -> {
//            UserCaption userCaption = new UserCaption(mEdtCaption.getText().toString(), mPathImg, mColorTextDefault, mFontDefault, mAlignTextDefault, true);
//            mRepository.insertUserCaption(userCaption);
//            Toast.makeText(this, "Đã lưu caption. Để xem lại, bạn có thể vào Menu -> Caption đã tạo!", Toast.LENGTH_LONG).show();
//            this.finish();
        });
        tvDone.setClickable(false);

        imgBack.setOnClickListener(v -> finish());
    }

    private void findViewById() {
        imgBack = findViewById(R.id.btn_edit_back_and_cancel);
        tvDone = findViewById(R.id.tv_edit_save);
        mImgBackground = findViewById(R.id.img_edit_background);
        layoutMenu = findViewById(R.id.layout_menu);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    mImgBackground.setImageURI(resultUri);
                    isCropped = true;

                    enableBtnSave();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        Toast.makeText(this, "Bạn chưa chọn ảnh.", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            final Uri imageUri = data.getData();
                            mPathImg = imageUri.getPath();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            mImgBackground.setImageBitmap(selectedImage);

                            // co su thay doi
                            isPickedPicture = true;
                            enableBtnSave();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
        }
    }

    void enableBtnSave() {
        if (checkChangeBackground(mImgBackground)) {
            tvDone.setAlpha(1);
            tvDone.setClickable(true);
        } else {
            tvDone.setAlpha(0.7f);
            tvDone.setClickable(false);
        }
    }


    private boolean checkChangeBackground(ImageView imageView) {
        return isPickedPicture || imageView.getRotation() % 360 != 0 || isCropped || (flipCurrent == 180);
    }

    private void bindUserCaptions(List result) {
        Log.e("TEST", result.size() + "");
    }

    public void textOnClick(View view) {
        imgBack.setVisibility(View.INVISIBLE);
        tvDone.setVisibility(View.INVISIBLE);
        layoutMenu.setVisibility(View.INVISIBLE);
        Dialog addTextDialog = new DialogAddTextBuilder(this).build();
        addTextDialog.setOnCancelListener(dialog -> {
            imgBack.setVisibility(View.VISIBLE);
            tvDone.setVisibility(View.VISIBLE);
            layoutMenu.setVisibility(View.VISIBLE);
        });
        addTextDialog.show();
    }

    public void imageOnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }


    public void rotateOnClick(View view) {
        mImgBackground.setRotation(mImgBackground.getRotation() + -90);

        enableBtnSave();
    }

    public void cropOnClick(View view) {
        // start cropping activity for pre-acquired image saved on the device
        CropImage.activity(getImageUri(getApplicationContext(), getBitmapFromImageView(mImgBackground)))
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void flipOnClick(View view) {
        flipCurrent = (flipCurrent + 180) % 360;
        mImgBackground.setRotationY(flipCurrent);

        enableBtnSave();
    }

    public Bitmap getBitmapFromImageView(ImageView imageView) {
        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        return drawable.getBitmap();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "temp", null);
        return Uri.parse(path);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }
}
