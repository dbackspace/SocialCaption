package com.xlteam.socialcaption.ui.edit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.UserCaptionRepository;
import com.xlteam.socialcaption.model.CommonCaption;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class EditCaptionActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 1;
    private ImageView mBtnBackAndCancel;
    private TextView mBtnSaveImage;
    private CommonCaption mCommonCaption;
    private ImageView mImgBackground;

    private boolean isPickedPicture = false;
    // set default for tool
    private int mTextSizeDefault; // default = 1    [0 -> 4]
    private int mFontDefault = 6;
    private int mColorTextDefault = 0;
    private int mAlignTextDefault = 0;
    private String mPathImg = "";
    private UserCaptionRepository mRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_caption);
        // create databaseRepository
//        mRepository = (UserCaptionRepository) RepositoryFactory.createRepository(this, this, USER_REPOSITORY);

        mBtnBackAndCancel = findViewById(R.id.btn_edit_back_and_cancel);
        mBtnSaveImage = findViewById(R.id.tv_edit_save);
        mImgBackground = findViewById(R.id.img_edit_background);

        // get Caption
//        mCommonCaption = getIntent().getExtras().getParcelable(Constant.EXTRA_CAPTION);
        mBtnSaveImage.setOnClickListener(v -> {
//            UserCaption userCaption = new UserCaption(mEdtCaption.getText().toString(), mPathImg, mColorTextDefault, mFontDefault, mAlignTextDefault, true);
//            mRepository.insertUserCaption(userCaption);
//            Toast.makeText(this, "Đã lưu caption. Để xem lại, bạn có thể vào Menu -> Caption đã tạo!", Toast.LENGTH_LONG).show();
//            this.finish();
        });
        mBtnSaveImage.setClickable(false);


//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Bạn có muốn lưu bản nháp");
//        builder.setCancelable(false);
//        builder.setPositiveButton(
//                "Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        UserCaption userCaption = new UserCaption(mEdtCaption.getText().toString(), mPathImg, mColorTextDefault, mFontDefault, mAlignTextDefault, true);
//                        mRepository.insertUserCaption(userCaption);
//                        Toast.makeText(mContext, "Đã lưu bản nháp. Để xem lại, bạn có thể vào Menu -> Caption đã tạo!", Toast.LENGTH_LONG).show();
//
//                        dialog.cancel();
//                        finish();
//                    }
//                });
//
//        builder.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                        finish();
//                    }
//                });
//
//        AlertDialog alert = builder.create();


    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
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

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    void enableBtnSave() {
        if (isPickedPicture || mFontDefault != 6 || mColorTextDefault != 0 || mAlignTextDefault != 0 || mTextSizeDefault != 1) {
            mBtnSaveImage.setAlpha(1);
            mBtnSaveImage.setClickable(true);
        } else {
            mBtnSaveImage.setAlpha(0.7f);
            mBtnSaveImage.setClickable(false);
        }
    }

//    @Override
//    public void loadResult(int loaderTaskType, List result) {
//        if (loaderTaskType == LOAD_ALL_USER_CAPTION) {
//            bindUserCaptions(result);
//        }
//    }

    private void bindUserCaptions(List result) {
        Log.e("TEST", result.size() + "");
    }

    public void textOnClick(View view) {
    }

    public void imageOnClick(View view) {
    }


    public void rotateOnClick(View view) {
    }

    public void cropOnClick(View view) {
    }

    public void flipOnClick(View view) {
    }

    public void backOnClick(View view) {
//            Log.e("TEST", isPickedPicture + " " + mFontDefault + " " + mColorTextDefault + " " + mAlignTextDefault + " " + mTextSizeDefault);
//            if (isPickedPicture || mFontDefault != 6 || mColorTextDefault != 0 || mAlignTextDefault != 0 || mTextSizeDefault != 1) {
//                alert.show();
//            } else {
        finish();
//            }
    }
}
