package com.xlteam.socialcaption.ui.edit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.datasource.ColorDataSource;
import com.xlteam.socialcaption.external.datasource.FontDataSource;
import com.xlteam.socialcaption.external.utility.gesture.MultiTouchListener;
import com.xlteam.socialcaption.external.utility.gesture.OnGestureControl;
import com.xlteam.socialcaption.external.utility.gesture.OnMultiTouchListener;
import com.xlteam.socialcaption.external.utility.gesture.OnPhotoEditorListener;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.external.utility.utils.FileUtils;
import com.xlteam.socialcaption.external.utility.utils.MapViewUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.model.Color;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

import static com.xlteam.socialcaption.external.utility.utils.Constant.BACKGROUND_COLOR_0;
import static com.xlteam.socialcaption.external.utility.utils.Constant.SAVE_DATE_TIME_FORMAT;

public class EditCaptionActivity extends AppCompatActivity
        implements
        DialogAddTextBuilder.Callback,
        OnPhotoEditorListener,
        OnMultiTouchListener {
    private ImageView imgBack, imgCancelText, imgDoneText;
    private TextView tvDone;
    private ImageView mImgBackground;
    private RelativeLayout layoutText;


    private AdView mAdView;
    public static final int PICK_IMAGE = 1;

    // check change
    private boolean isPickedPicture = false;
    private boolean isCropped = false;
    private Context mContext;

    // relative background
    private RelativeLayout relativeBackground;

    // Text editor
    private RecyclerView rvFont, rvColor;
    // Set default for tool
    private int mGravityText, mNumberBg = BACKGROUND_COLOR_0, mNumberColor = 0;
    // imgBackground: img background of align
    private ImageView imgGravity, imgBackground;
    private ItemText mItemTextViewClicked;
    private RelativeLayout containerBgImage;
    private boolean isHasText = false;

    // text current
    private TextView currentText;
    // tool for text
    private ImageView imgRemove, imgEdit, imgBalance, imgZoom;
    // border for text
    private FrameLayout borderOfText;
    private View currentViewOfText;
    private View previousViewOfText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_caption);
        mContext = getBaseContext();
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findViewById();
        relativeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutText.getVisibility() == View.VISIBLE) {
                    showTextMode(false);
                }
            }
        });

        Intent intent = getIntent();
        int type = intent.getIntExtra(Constant.EXTRA_TYPE_PICTURE, -1);
        if (type == Constant.TYPE_PICK_PHOTO) {
            Uri imageUri = intent.getParcelableExtra(Constant.EXTRA_PICK_PHOTO_URL);
            if (imageUri != null) {
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    mImgBackground.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (type == Constant.TYPE_TAKE_PHOTO) {
            Bitmap photo = intent.getParcelableExtra(Constant.EXTRA_TAKE_PHOTO_BITMAP);
            if (photo != null) mImgBackground.setImageBitmap(photo);
        } else {
            String url = intent.getStringExtra(Constant.EXTRA_URL_PICTURE);
            if (type == Constant.TYPE_PICTURE_FIREBASE) {
                Glide.with(this)
                        .load(url)
                        .fitCenter()
                        .into(mImgBackground);
            } else if (type == Constant.TYPE_PICTURE_CREATED) {
                Glide.with(this)
                        .load("file://" + url)
                        .fitCenter()
                        .into(mImgBackground);
            }
        }
        initTextEditor();
        tvDone.setOnClickListener(v -> {
            if (Utility.isValidClick(v.getId()))
                saveImageCreatedToSdcard(relativeBackground);
        });
        tvDone.setClickable(false);
        imgBack.setOnClickListener(v -> finish());
        imgCancelText.setOnClickListener(view -> showTextMode(false));
        imgDoneText.setOnClickListener(view -> {
            showTextMode(false);
            showToolAndBorderOfText(false);
        });
    }

    private void findViewById() {
        imgBack = findViewById(R.id.btn_edit_back_and_cancel);
        tvDone = findViewById(R.id.tv_edit_save);
        mImgBackground = findViewById(R.id.img_edit_background);

        relativeBackground = findViewById(R.id.relative_background_save_img);
        containerBgImage = findViewById(R.id.container_background_image);

        //text editor
        layoutText = findViewById(R.id.layout_text);
        imgCancelText = findViewById(R.id.btn_cancel_edit_text);
        imgDoneText = findViewById(R.id.image_save_text);
        rvColor = findViewById(R.id.rvColor);
        rvFont = findViewById(R.id.rvFont);
        imgGravity = findViewById(R.id.imgGravity);
        imgBackground = findViewById(R.id.image_background);
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
                    isChangedListener();
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
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            mImgBackground.setImageBitmap(selectedImage);

                            // co su thay doi
                            isPickedPicture = true;
                            isChangedListener();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
        }
    }

    public void onOpenTextEditorClicked(View view) {
        Dialog addTextDialog = new DialogAddTextBuilder(this, this, null, Utility.getBitmapFromView(relativeBackground)).build();
        addTextDialog.show();
    }

    public void onAddTextClicked(View view) {
        Dialog addTextDialog = new DialogAddTextBuilder(this, this, null, Utility.getBitmapFromView(relativeBackground)).build();
        addTextDialog.show();
    }

    public void onAddImageClicked(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void onEditPictureClicked(View view) {
        // start cropping activity for pre-acquired image saved on the device
        CropImage.activity(Utility.getImageUri(getApplicationContext(), getBitmapFromImageView(mImgBackground)))
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    // Thay đổi màu chữ (color)
    public void onTextColorClicked(View view) {
        rvColor.setVisibility(View.VISIBLE);
        rvFont.setVisibility(View.GONE);
    }

    // Thay đổi font
    public void onTextFontClicked(View view) {
        rvFont.setVisibility(View.VISIBLE);
        rvColor.setVisibility(View.GONE);
    }

    // Thay đổi nền chữ
    public void onTextBgClicked(View view) {
        Color color = ColorDataSource.getInstance().getAllData().get(mNumberColor);
        if (mNumberBg == Constant.BACKGROUND_COLOR_0) {
            mNumberBg = Constant.BACKGROUND_COLOR_50;
//            imgBackground.setImageResource(R.drawable.ic_align_right);
            String colorBlur = "#80" + color.getColor().substring(3);
            Utility.setColorForView(currentText, colorBlur);
            Utility.setColorForTextView(currentText, color.getTextColor());

        } else if (mNumberBg == Constant.BACKGROUND_COLOR_50) {
            mNumberBg = Constant.BACKGROUND_COLOR_100;
//            imgBackground.setImageResource(R.drawable.ic_align_left);
            Utility.setColorForView(currentText, color.getColor());
            Utility.setColorForTextView(currentText, color.getTextColor());
        } else {
            mNumberBg = Constant.BACKGROUND_COLOR_0;
//            imgBackground.setImageResource(R.drawable.ic_align_center);
            Utility.setColorForView(currentText, "#00FFFFFF");
            Utility.setColorForTextView(currentText, color.getColor());
        }
        mItemTextViewClicked.setBg(mNumberBg);
    }

    public void onTextAlignClicked(View view) {
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
        currentText.setGravity(mGravityText);
        mItemTextViewClicked.setGravity(mGravityText);
    }

    public Bitmap getBitmapFromImageView(ImageView imageView) {
        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        return drawable.getBitmap();
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

    // save image
    private void saveImageCreatedToSdcard(View view) {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Bitmap bitmap = Utility.getBitmapFromView(view);
                        File saveFolder = FileUtils.findExistingFolderSaveImage();
                        if (saveFolder != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat(SAVE_DATE_TIME_FORMAT, Locale.getDefault());
                            String savePath = saveFolder.getAbsolutePath() + File.separator + sdf.format(new Date(Utility.now())) + ".png";
                            File saveFile = Utility.bitmapToFile(bitmap, savePath);
                            if (saveFile != null) {
                                Toast.makeText(mContext, getString(R.string.save_success), Toast.LENGTH_LONG).show();

                                MediaScannerConnection.scanFile(mContext,
                                        new String[]{savePath}, null,
                                        (path, uri) -> Log.i("SaveImage", "Finished scanning " + path));

//                                    if (mActivity != null) mActivity.checkAndShowAds(3);

                                // save image path to sharePref
                                Timber.e(savePath);
                                responseResultToMainActivity();
                            } else {
                                Toast.makeText(mContext, getString(R.string.save_fail), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(mContext, getString(R.string.notify_not_enough_permission), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void responseResultToMainActivity() {
        Intent intent = getIntent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onSaveClicked(String text, boolean isEditOldText) {
        /**
         * Không tạo empty text.
         * TODO: Xử lý trong textChange ở DialogAddText, có cho enable nút DONE hay không
         */
        if (TextUtils.isEmpty(text.trim())) {
            isHasText = false;
            return;
        }
        isHasText = true;
        // set button "Lưu" mờ hay hiện
        isChangedListener();

        if (isEditOldText) {
            currentText.setText(text);
            return;
        }

        if (currentViewOfText != null) {
            showToolAndBorderOfText(false);
            previousViewOfText = currentViewOfText;
        }

        currentViewOfText = getTextStickerLayout();
        currentText = currentViewOfText.findViewById(R.id.text_tv);
        borderOfText = currentViewOfText.findViewById(R.id.text_border);
        imgRemove = currentViewOfText.findViewById(R.id.image_text_remove);
        imgEdit = currentViewOfText.findViewById(R.id.image_text_edit);
        imgBalance = currentViewOfText.findViewById(R.id.image_text_balance);
        imgZoom = currentViewOfText.findViewById(R.id.image_text_zoom);

        imgRemove.setOnClickListener(v -> {
            if (currentViewOfText != null) {
                deleteViewFromParent(currentViewOfText);
            }
        });

        imgEdit.setOnClickListener(v -> {
            Dialog addTextDialog = new DialogAddTextBuilder(this, this, currentText.getText().toString(), Utility.getBitmapFromView(relativeBackground)).build();
            addTextDialog.show();
        });

        imgBalance.setOnClickListener(v -> {
            if (currentViewOfText != null) {
                currentViewOfText.setRotation(0f);
            }
        });

        currentText.setBackgroundResource(R.drawable.bg_text_view_edit);
        currentText.setText(text);
        currentText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                getResources().getDimension(R.dimen.text_added_default_size));

        MultiTouchListener multiTouchListener = new MultiTouchListener(mImgBackground, this, mContext);
        multiTouchListener.setOnGestureControl(new OnGestureControl() {
            @Override
            public void onDoubleClick(@NotNull View currentView) {
                previousViewOfText = currentViewOfText;
                currentViewOfText = currentView;
                showTextMode(true);
            }

            @Override
            public void onDown(@NotNull View currentView) {
                previousViewOfText = currentViewOfText;
                currentViewOfText = currentView;
                showTextMode(true);
            }

            @Override
            public void onLongClick() {
            }
        });

        mNumberBg = BACKGROUND_COLOR_0;
        mNumberColor = 0;
        Utility.setColorForView(currentText, "#00FFFFFF");
//        Utility.setColorForTextView(currentText, color.getColor());
        mItemTextViewClicked = new ItemText(text);

        showTextMode(true);
        addViewToParent(currentViewOfText);
        currentViewOfText.setOnTouchListener(multiTouchListener);
    }

/*    public void editTextByClickTextView(View view, ItemText itemText) {
        mColorAdapter.setNumberSelect(mNumberColor);
        mFontAdapter.setNumberFont(mNumberFont);
        if (mGravityText == Gravity.CENTER) {
            imgGravity.setImageResource(R.drawable.ic_align_center);
        } else if (mGravityText == Gravity.END) {
            imgGravity.setImageResource(R.drawable.ic_align_right);
        } else {
            imgGravity.setImageResource(R.drawable.ic_align_left);
        }
        if (mNumberBg == Constant.BACKGROUND_COLOR_0) {
            imgBackground.setImageResource(R.drawable.ic_align_center);
        } else if (mNumberBg == Constant.BACKGROUND_COLOR_50) {
            imgBackground.setImageResource(R.drawable.ic_align_right);
        } else {
            imgBackground.setImageResource(R.drawable.ic_align_left);
        }

    }*/

    private View getTextStickerLayout() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rootView = layoutInflater.inflate(R.layout.item_text_added, null);
        TextView txtText = rootView.findViewById(R.id.text_tv);
        if (txtText != null) {
            txtText.setGravity(Gravity.CENTER);
        }
        return rootView;
    }

    private void addViewToParent(View viewOfText) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        relativeBackground.addView(viewOfText, params);
    }

    private void deleteViewFromParent(View viewOfText) {
        relativeBackground.removeView(viewOfText);
        relativeBackground.invalidate();
        showTextMode(false);
    }

    @Override
    public void onEventDownChangeListener(View view) {
    }

    @Override
    public void onEventMoveChangeListener(View view) {
    }

    @Override
    public void onEventUpChangeListener(View view) {
    }

    private void initTextEditor() {
        rvFont.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        FontAdapter mFontAdapter = new FontAdapter(this, numberFont -> {
            Timber.e("setFont");
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "font/" + FontDataSource.getInstance().getAllFonts().get(numberFont).getFont());
            currentText.setTypeface(typeface);
            mItemTextViewClicked.setFont(numberFont);
        });
        rvFont.setAdapter(mFontAdapter);

        rvColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        //loại bỏ #FF, thay bằng #80
        ColorAdapter mColorAdapter = new ColorAdapter(colorPosition -> {
            mNumberColor = colorPosition;
            Color color = ColorDataSource.getInstance().getAllData().get(colorPosition);
            if (mNumberBg == Constant.BACKGROUND_COLOR_0) {
                Utility.setColorForView(currentText, "#00FFFFFF");
                Utility.setColorForTextView(currentText, color.getColor());
            } else if (mNumberBg == Constant.BACKGROUND_COLOR_50) {
                String colorBlur = "#80" + color.getColor().substring(3);//loại bỏ #FF, thay bằng #80
                Utility.setColorForView(currentText, colorBlur);
                Utility.setColorForTextView(currentText, color.getTextColor());
            } else {
                Utility.setColorForView(currentText, color.getColor());
                Utility.setColorForTextView(currentText, color.getTextColor());
            }
            mItemTextViewClicked.setColor(colorPosition);
        });
        rvColor.setAdapter(mColorAdapter);

        containerBgImage.setOnClickListener(v -> {
            //cái này rất mua việc, xóa cho khỏe
        });
    }

    @Override
    public void onBackPressed() {
        if (layoutText.getVisibility() == View.VISIBLE) {
            //hỏi xem có lưu thay đổi
            showTextMode(false);
        } else {
            super.onBackPressed();
        }
    }

    private void isChangedListener() {
        if (isCropped || isPickedPicture || isHasText) {
            tvDone.setClickable(true);
            tvDone.setAlpha(1);
        } else {
            tvDone.setClickable(false);
            tvDone.setAlpha(0.7f);
        }
    }

    private void showTextMode(boolean isShow) {
        if (isShow) {
            layoutText.setVisibility(View.VISIBLE);
            if (currentViewOfText != null) {
                showToolAndBorderOfText(true);
            }
        } else {
            layoutText.setVisibility(View.GONE);
            previousViewOfText = null;
            if (currentViewOfText != null) {
                showToolAndBorderOfText(false);
                currentViewOfText = null;
            }
        }
    }

    private void showToolAndBorderOfText(boolean isShow) {
        if (isShow && previousViewOfText != null && previousViewOfText != currentViewOfText) {
            borderOfText.setBackgroundResource(0);
            imgRemove.setVisibility(View.GONE);
            imgEdit.setVisibility(View.GONE);
            imgBalance.setVisibility(View.GONE);
            imgZoom.setVisibility(View.GONE);
        }
        if (isShow && currentViewOfText != null) {
            updateToolAndBorderToNewCurrentText();
        }
        borderOfText.setBackgroundResource(isShow ? R.drawable.background_border_text_added : 0);
        imgRemove.setVisibility(isShow ? View.VISIBLE : View.GONE);
        imgEdit.setVisibility(isShow ? View.VISIBLE : View.GONE);
        imgBalance.setVisibility(isShow ? View.VISIBLE : View.GONE);
        imgZoom.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void updateToolAndBorderToNewCurrentText() {
        currentText = currentViewOfText.findViewById(R.id.text_tv);
        borderOfText = currentViewOfText.findViewById(R.id.text_border);
        imgRemove = currentViewOfText.findViewById(R.id.image_text_remove);
        imgEdit = currentViewOfText.findViewById(R.id.image_text_edit);
        imgBalance = currentViewOfText.findViewById(R.id.image_text_balance);
        imgZoom = currentViewOfText.findViewById(R.id.image_text_zoom);
    }
}