package com.xlteam.textonpicture.ui.edit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
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
import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.datasource.ColorDataSource;
import com.xlteam.textonpicture.external.datasource.FontDataSource;
import com.xlteam.textonpicture.external.utility.colorpicker.ColorPickerDialog;
import com.xlteam.textonpicture.external.utility.customview.ClipArt;
import com.xlteam.textonpicture.external.utility.gesture.MultiTouchListener;
import com.xlteam.textonpicture.external.utility.gesture.OnGestureControl;
import com.xlteam.textonpicture.external.utility.gesture.OnMultiTouchListener;
import com.xlteam.textonpicture.external.utility.gesture.OnPhotoEditorListener;
import com.xlteam.textonpicture.external.utility.gesture.OnRotateListener;
import com.xlteam.textonpicture.external.utility.gesture.OnZoomListener;
import com.xlteam.textonpicture.external.utility.logger.Log;
import com.xlteam.textonpicture.external.utility.utils.Constant;
import com.xlteam.textonpicture.external.utility.utils.FileUtils;
import com.xlteam.textonpicture.external.utility.utils.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

import static com.xlteam.textonpicture.external.utility.utils.Constant.SAVE_DATE_TIME_FORMAT;

public class EditPictureActivity extends AppCompatActivity
        implements
        DialogAddTextBuilder.Callback,
        OnPhotoEditorListener,
        OnMultiTouchListener, FontAdapter.FontSelectCallback, ColorAdapter.ColorSelectCallback {
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
    private RecyclerView rvFont;
    private FontAdapter fontAdapter;
    private boolean isFirstClickFont = true;

    //color
    private RelativeLayout layoutOpacityColor;
    private RecyclerView rvColor;
    private SeekBar sbOpacity;
    private TextView tvValueOpacity;
    private RelativeLayout layoutShadow;
    private SeekBar sbSaturationShadow, sbOpacityShadow;
    private TextView tvValueSaturationShadow, tvValueOpacityShadow;
    private ImageView imgShadowLeft, imgShadowRight, imgShadowTop, imgShadowBottom, imgShadowCenter;
    private int currentModeOfColor = -1;
    private ColorAdapter mColorAdapter;

    //align
    private RelativeLayout layoutAlign;
    private ImageView imgAlignCenter;
    private ImageView imgAlignLeft;
    private ImageView imgAlignRight;

    private boolean isHasText = false;

    // text current
    private TextView currentText;
    // tool for text
    private ImageView imgRemove, imgRotate, imgBalance, imgZoom;
    private RecyclerView rvToolText;
    private ToolTextAdapter mToolTextAdapter;
    // border for text
    private ImageView borderOfText;
    private View currentViewOfText;
    private View previousViewOfText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_picture);
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

        //text editor
        rvToolText = findViewById(R.id.rv_tool_text);
        layoutText = findViewById(R.id.layout_text);
        imgCancelText = findViewById(R.id.btn_cancel_edit_text);
        imgDoneText = findViewById(R.id.image_save_text);

        rvFont = findViewById(R.id.rvFont);
        //color
        rvColor = findViewById(R.id.rvColor);
        layoutOpacityColor = findViewById(R.id.layout_opacity_color);
        sbOpacity = findViewById(R.id.sb_opacity);
        tvValueOpacity = findViewById(R.id.tv_value_opacity);
        layoutShadow = findViewById(R.id.layout_shadow);
        sbSaturationShadow = findViewById(R.id.sb_saturation_shadow);
        sbOpacityShadow = findViewById(R.id.sb_opacity_shadow);
        tvValueSaturationShadow = findViewById(R.id.tv_value_saturation_shadow);
        tvValueOpacityShadow = findViewById(R.id.tv_value_opacity_shadow);
        imgShadowTop = findViewById(R.id.image_shadow_top);
        imgShadowBottom = findViewById(R.id.image_shadow_bottom);
        imgShadowLeft = findViewById(R.id.image_shadow_left);
        imgShadowRight = findViewById(R.id.image_shadow_right);
        imgShadowCenter = findViewById(R.id.image_shadow_center);

        //align
        layoutAlign = findViewById(R.id.layout_align);
        imgAlignLeft = findViewById(R.id.image_align_left);
        imgAlignCenter = findViewById(R.id.image_align_center);
        imgAlignRight = findViewById(R.id.image_align_right);

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

    public void onAddTextClicked() {
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
    public void onTextColorClicked() {
        layoutOpacityColor.setVisibility(View.VISIBLE);
        rvColor.setVisibility(View.VISIBLE);
        mColorAdapter.setNoColor(false);
        rvFont.setVisibility(View.GONE);
        layoutAlign.setVisibility(View.GONE);
        layoutShadow.setVisibility(View.GONE);
        if (currentViewOfText != null) {
            sbOpacity.setProgress(((ItemText) currentViewOfText.getTag()).getOpacityText());
        }
    }

    public void onTextBgColorClicked() {
        layoutOpacityColor.setVisibility(View.VISIBLE);
        rvColor.setVisibility(View.VISIBLE);
        mColorAdapter.setNoColor(true);
        rvFont.setVisibility(View.GONE);
        layoutShadow.setVisibility(View.GONE);
        layoutAlign.setVisibility(View.GONE);
        if (currentViewOfText != null) {
            sbOpacity.setProgress(((ItemText) currentViewOfText.getTag()).getOpacityBackground());
        }
    }

    public void onTextShadowClicked() {
        rvColor.setVisibility(View.VISIBLE);
        mColorAdapter.setNoColor(true);
        layoutShadow.setVisibility(View.VISIBLE);
        rvFont.setVisibility(View.GONE);
        layoutAlign.setVisibility(View.GONE);
        layoutOpacityColor.setVisibility(View.GONE);
        sbOpacityShadow.setProgress(((ItemText) currentViewOfText.getTag()).getOpacityShadow());
        sbSaturationShadow.setProgress(((ItemText) currentViewOfText.getTag()).getSaturationShadow());
    }

    // Thay đổi font
    public void onTextFontClicked() {
        rvFont.setVisibility(View.VISIBLE);
        layoutOpacityColor.setVisibility(View.GONE);
        rvColor.setVisibility(View.GONE);
        layoutShadow.setVisibility(View.GONE);
        layoutAlign.setVisibility(View.GONE);
        if (currentViewOfText != null) {
            ItemText itemText = (ItemText) currentViewOfText.getTag();
            fontAdapter.setNumberFont(itemText.getFont());
            fontAdapter.notifyDataSetChanged();
            rvFont.smoothScrollToPosition(itemText.getFont());
            if (isFirstClickFont) {
                rvFont.smoothScrollToPosition(itemText.getFont());
                isFirstClickFont = false;
            }
        }
    }

    public void onTextAlignClicked() {
        layoutAlign.setVisibility(View.VISIBLE);
        rvFont.setVisibility(View.GONE);
        layoutOpacityColor.setVisibility(View.GONE);
        layoutShadow.setVisibility(View.GONE);
        rvColor.setVisibility(View.GONE);
        if (currentViewOfText != null) {
            ItemText itemText = (ItemText) currentViewOfText.getTag();
            setIconGravity(itemText.getGravity());
        }
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
        imgRotate = currentViewOfText.findViewById(R.id.image_text_edit);
        imgBalance = currentViewOfText.findViewById(R.id.image_text_balance);
        imgZoom = currentViewOfText.findViewById(R.id.image_text_zoom);

        imgRemove.setOnClickListener(v -> {
            if (currentViewOfText != null) {
                deleteViewFromParent(currentViewOfText);
            }
        });

//        imgEdit.setOnClickListener(v -> {
//            Dialog addTextDialog = new DialogAddTextBuilder(this, this, currentText.getText().toString(), Utility.getBitmapFromView(relativeBackground)).build();
//            addTextDialog.show();
//        });

        imgBalance.setOnClickListener(v -> {
            if (currentViewOfText != null) {
                currentViewOfText.setRotation(0f);
            }
        });

        imgRotate.setOnTouchListener(new OnRotateListener((RelativeLayout) currentViewOfText));
        imgZoom.setOnTouchListener(new OnZoomListener((RelativeLayout) currentViewOfText, relativeBackground, currentText));

        currentText.measure(0, 0);
        currentText.setBackgroundResource(R.drawable.bg_text_view_edit);
        currentText.setText(text);
        currentText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                getResources().getDimension(R.dimen.text_added_default_size));
        Typeface type = Typeface.createFromAsset(mContext.getAssets(), "font/" + "dancingscript_bold.ttf");
        currentText.setTypeface(type);

        MultiTouchListener multiTouchListener =
                MultiTouchListener
                        .create(mContext)
                        .setBackgroundImage(mImgBackground)
                        .setPhotoEditorListener(this)
                        // sua tam de ko loi
                        .setTextAddedView((ClipArt) currentViewOfText)
                        .setZoomRotateBtn(imgZoom);

        multiTouchListener.setOnGestureControl(new OnGestureControl() {
            @Override
            public void onDown(@NotNull ClipArt currentView) {

            }

            @Override
            public void onLongClick() {

            }

            @Override
            public void onDoubleClick(@NotNull ClipArt currentView) {

            }
          /*  @Override
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
            }*/
        });

//        imgGravity.setImageResource(R.drawable.ic_align_center);

        Utility.setColorForView(currentText, "#00FFFFFF");
//        Utility.setColorForTextView(currentText, color.getColor());

        ItemText itemText = new ItemText(currentText.getText().toString());
        currentViewOfText.setTag(itemText);

        showTextMode(true);
        addViewToParent(currentViewOfText);
        currentViewOfText.setOnTouchListener(multiTouchListener);
    }

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
                currentText.getMeasuredWidth() + 150, currentText.getMeasuredHeight() + 150);
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
        rvToolText.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mToolTextAdapter = new ToolTextAdapter(this, number -> {
            currentModeOfColor = number;
            switch (number) {
                case 0:
                    onAddTextClicked();
                    break;
                case 1:
                    onTextColorClicked();
                    break;
                case 2:
                    onTextBgColorClicked();
                    break;
                case 3:
                    onTextShadowClicked();
                    break;
                case 4:
                    onTextFontClicked();
                    break;
                case 5:
                    onTextAlignClicked();
                    break;

            }
        });
        rvToolText.setAdapter(mToolTextAdapter);

        /* init font*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        int DEFAULT_FONT_NUMBER = 3;
        linearLayoutManager.scrollToPositionWithOffset(DEFAULT_FONT_NUMBER, 20);
        rvFont.setLayoutManager(linearLayoutManager);
        fontAdapter = new FontAdapter(this, this);
        fontAdapter.setNumberFont(DEFAULT_FONT_NUMBER);
        rvFont.setAdapter(fontAdapter);

        /* init color*/
        rvColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mColorAdapter = new ColorAdapter(this);
        rvColor.setAdapter(mColorAdapter);
        sbOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvValueOpacity.setText(progress + "%");
                ItemText itemText = (ItemText) currentViewOfText.getTag();
                switch (mToolTextAdapter.getCurrentNumberTool()) {
                    case 1:
                        itemText.setOpacityText(progress);
                        Utility.setColorForTextView(currentText, buildColorString(progress, itemText.getColorText()));
                        break;
                    case 2:
                        itemText.setOpacityBackground(progress);
                        Utility.setColorForView(currentText, buildColorString(progress, itemText.getColorBackground()));
                        break;
                }
                currentViewOfText.setTag(itemText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /*shadow*/
        sbSaturationShadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value;
            ItemText itemText;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvValueSaturationShadow.setText(progress + "%");
                itemText = (ItemText) currentViewOfText.getTag();
                currentText.setShadowLayer((progress + 1) / 5f, itemText.getDxShadow(), itemText.getDyShadow(),
                        Color.parseColor(buildColorString(itemText.getOpacityShadow(), itemText.getColorShadow())));
                value = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                itemText.setSaturationShadow(value);
                currentViewOfText.setTag(itemText);
            }
        });

        sbOpacityShadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value;
            ItemText itemText;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvValueOpacityShadow.setText(progress + "%");
                itemText = (ItemText) currentViewOfText.getTag();
                currentText.setShadowLayer((itemText.getSaturationShadow() + 1) / 5f, itemText.getDxShadow(), itemText.getDyShadow(),
                        Color.parseColor(buildColorString(progress, itemText.getColorShadow())));
                value = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                itemText.setOpacityShadow(value);
                currentViewOfText.setTag(itemText);
            }
        });

        View.OnClickListener shadowArrowClick = v -> {
            ItemText itemText = (ItemText) currentViewOfText.getTag();
            float dx = itemText.getDxShadow(), dy = itemText.getDyShadow();
            switch (v.getId()) {
                case R.id.image_shadow_left:
                    if (dx >= -10) itemText.setDxShadow(dx - 1f);
                    else return;
                    break;
                case R.id.image_shadow_right:
                    if (dx <= 10) itemText.setDxShadow(dx + 1f);
                    else return;
                    break;
                case R.id.image_shadow_top:
                    if (dy >= -10) itemText.setDyShadow(dy - 1f);
                    else return;
                    break;
                case R.id.image_shadow_bottom:
                    if (dy <= 10) itemText.setDyShadow(dy + 1f);
                    else return;
                    break;
                case R.id.image_shadow_center:
                    itemText.setDxShadow(0f);
                    itemText.setDyShadow(0f);
                    break;

            }
            currentText.setShadowLayer((itemText.getSaturationShadow() + 1) / 5f, itemText.getDxShadow(), itemText.getDyShadow(),
                    Color.parseColor(buildColorString(itemText.getOpacityShadow(), itemText.getColorShadow())));
            currentViewOfText.setTag(itemText);
        };
        imgShadowLeft.setOnClickListener(shadowArrowClick);
        imgShadowRight.setOnClickListener(shadowArrowClick);
        imgShadowTop.setOnClickListener(shadowArrowClick);
        imgShadowBottom.setOnClickListener(shadowArrowClick);
        imgShadowCenter.setOnClickListener(shadowArrowClick);

        // init align
        imgAlignRight.setOnClickListener(v -> {
            currentText.setGravity(Gravity.END);
            ItemText itemText = (ItemText) currentViewOfText.getTag();
            itemText.setGravity(2);
            setIconGravity(2);
            currentViewOfText.setTag(itemText);
        });

        imgAlignLeft.setOnClickListener(v -> {
            currentText.setGravity(Gravity.START);
            ItemText itemText = (ItemText) currentViewOfText.getTag();
            itemText.setGravity(0);
            setIconGravity(0);
            currentViewOfText.setTag(itemText);
        });

        imgAlignCenter.setOnClickListener(v -> {
            currentText.setGravity(Gravity.CENTER);
            ItemText itemText = (ItemText) currentViewOfText.getTag();
            itemText.setGravity(1);
            setIconGravity(1);
            currentViewOfText.setTag(itemText);
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
            imgRotate.setVisibility(View.GONE);
            imgBalance.setVisibility(View.GONE);
            imgZoom.setVisibility(View.GONE);
        }
        if (isShow && currentViewOfText != null) {
            updateToolAndBorderToNewCurrentText();
        }
        borderOfText.setBackgroundResource(isShow ? R.drawable.background_border_text_added : 0);
        imgRemove.setVisibility(isShow ? View.VISIBLE : View.GONE);
        imgRotate.setVisibility(isShow ? View.VISIBLE : View.GONE);
        imgBalance.setVisibility(isShow ? View.VISIBLE : View.GONE);
        imgZoom.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void updateToolAndBorderToNewCurrentText() {
        currentText = currentViewOfText.findViewById(R.id.text_tv);
        borderOfText = currentViewOfText.findViewById(R.id.text_border);
        imgRemove = currentViewOfText.findViewById(R.id.image_text_remove);
        imgRotate = currentViewOfText.findViewById(R.id.image_text_edit);
        imgBalance = currentViewOfText.findViewById(R.id.image_text_balance);
        imgZoom = currentViewOfText.findViewById(R.id.image_text_zoom);

        ItemText itemText = (ItemText) currentViewOfText.getTag();
        updateOldStateTool(itemText);
    }

    private void updateOldStateTool(ItemText itemText) { // trả lại trạng thái cũ cho tool ứng với text
        if (itemText == null) return;
        int opacityProgress = -1;
        if (currentModeOfColor == 1) {
            opacityProgress = itemText.getOpacityText();
        } else if (currentModeOfColor == 2) {
            opacityProgress = itemText.getOpacityBackground();
        }
        if (opacityProgress != -1) {
            sbOpacity.setProgress(opacityProgress);
        }

        //font
        int positionFont = itemText.getFont();
        rvFont.smoothScrollToPosition(positionFont);
        fontAdapter.setNumberFont(positionFont);
        fontAdapter.notifyDataSetChanged();

        //align
        setIconGravity(itemText.getGravity());

        //shadow
        sbSaturationShadow.setProgress(itemText.getSaturationShadow());
        sbOpacityShadow.setProgress(itemText.getOpacityShadow());
    }

    private void setIconGravity(int gravity) {
        switch (gravity) {
            case 0:
                setColorForImageView(imgAlignLeft, R.color.color_3cc2f5_legend);
                setColorForImageView(imgAlignCenter, R.color.white);
                setColorForImageView(imgAlignRight, R.color.white);
                break;
            case 1:
                setColorForImageView(imgAlignLeft, R.color.white);
                setColorForImageView(imgAlignCenter, R.color.color_3cc2f5_legend);
                setColorForImageView(imgAlignRight, R.color.white);
                break;
            case 2:
                setColorForImageView(imgAlignLeft, R.color.white);
                setColorForImageView(imgAlignCenter, R.color.white);
                setColorForImageView(imgAlignRight, R.color.color_3cc2f5_legend);
                break;
        }
    }

    private void setColorForImageView(ImageView img, int colorId) {
        ImageViewCompat.setImageTintList(img, ColorStateList.valueOf(ContextCompat.getColor(this, colorId)));
    }

    @Override
    public void selectFont(int numberFont) {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "font/" + FontDataSource.getInstance().getAllFonts().get(numberFont).getFont());
        currentText.setTypeface(typeface);
        ItemText itemText = (ItemText) currentViewOfText.getTag();
        itemText.setFont(numberFont);
        currentViewOfText.setTag(itemText);
    }

    @Override
    public void selectColor(int color) {
        String colorCSS = ColorDataSource.getInstance().getAllData().get(color);
        ItemText itemText = (ItemText) currentViewOfText.getTag();
        switch (mToolTextAdapter.getCurrentNumberTool()) {
            case 1:
                itemText.setColorText(colorCSS);
                int opacityText = itemText.getOpacityText();
                if (opacityText == 0) { // nếu màu trong suốt thì trả lại 100% màu
                    itemText.setOpacityText(100);
                    currentViewOfText.setTag(itemText);
                    sbOpacity.setProgress(100);
                } else {
                    Utility.setColorForTextView(currentText, buildColorString(opacityText, colorCSS));
                    currentViewOfText.setTag(itemText);
                }

                break;
            case 2:
                itemText.setColorBackground(colorCSS);
                int opacityBackground = itemText.getOpacityBackground();
                if (opacityBackground == 0) {
                    itemText.setOpacityBackground(20);
                    currentViewOfText.setTag(itemText);
                    sbOpacity.setProgress(20);
                } else {
                    Utility.setColorForView(currentText, buildColorString(opacityBackground, colorCSS));
                    currentViewOfText.setTag(itemText);
                }
                break;
            case 3:
                itemText.setColorShadow(colorCSS);
                int opacityShadow = itemText.getOpacityShadow();
                if (opacityShadow == 0) {
                    itemText.setOpacityShadow(100);
                    currentViewOfText.setTag(itemText);
                    sbOpacityShadow.setProgress(100);
                } else {
                    currentText.setShadowLayer((itemText.getSaturationShadow() + 1) / 5f, itemText.getDxShadow(), itemText.getDyShadow(),
                            Color.parseColor(buildColorString(opacityShadow, colorCSS)));
                    currentViewOfText.setTag(itemText);
                }
                break;
        }
    }

    @Override
    public void setNoColor() {
        ItemText itemText = (ItemText) currentViewOfText.getTag();
        switch (mToolTextAdapter.getCurrentNumberTool()) {
            case 2:
                itemText.setOpacityBackground(0);
                currentViewOfText.setTag(itemText);
                sbOpacity.setProgress(0);
                break;
            case 3:
                itemText.setOpacityShadow(0);
                currentViewOfText.setTag(itemText);
                sbOpacityShadow.setProgress(0);
                break;
        }
    }


    @Override
    public void pickColor() {
        String color = "#000000";
        ItemText itemText = (ItemText) currentViewOfText.getTag();
        int position = mToolTextAdapter.getCurrentNumberTool();
        if (itemText != null) {
            switch (position) {
                case 1:
                    color = "#" + itemText.getColorText();
                    break;
                case 2:
                    color = "#" + itemText.getColorBackground();
                    break;
                case 3:
                    color = "#" + itemText.getColorShadow();
                    break;
            }
        }
        ColorPickerDialog dialog = new ColorPickerDialog(this, Color.parseColor(color), false, new ColorPickerDialog.OnColorPickerListener() {
            @Override
            public void onCancel(ColorPickerDialog dialog) {
            }

            @Override
            public void onApply(ColorPickerDialog dialog, String color) {
                Log.d("binh.ngk ", " color = " + color);
                ItemText itemText = (ItemText) currentViewOfText.getTag();
                if (itemText == null) return;
                switch (position) {
                    case 1:
                        itemText.setColorText(color);
                        int opacityText = itemText.getOpacityText();
                        if (opacityText == 0) { // nếu màu trong suốt thì trả lại 100% màu
                            itemText.setOpacityText(100);
                            currentViewOfText.setTag(itemText);
                            sbOpacity.setProgress(100);
                        } else {
                            Utility.setColorForTextView(currentText, buildColorString(opacityText, color));
                            currentViewOfText.setTag(itemText);
                        }
                        break;
                    case 2:
                        itemText.setColorBackground(color);
                        int opacityBackground = itemText.getOpacityBackground();
                        if (opacityBackground == 0) {
                            itemText.setOpacityBackground(20);
                            currentViewOfText.setTag(itemText);
                            sbOpacity.setProgress(20);
                        } else {
                            Utility.setColorForView(currentText, buildColorString(opacityBackground, color));
                            currentViewOfText.setTag(itemText);
                        }
                        break;
                    case 3:
                        itemText.setColorShadow(color);
                        int opacityShadow = itemText.getOpacityShadow();
                        if (opacityShadow == 0) {
                            itemText.setOpacityShadow(100);
                            currentViewOfText.setTag(itemText);
                            sbOpacityShadow.setProgress(100);
                        } else {
                            currentText.setShadowLayer((itemText.getSaturationShadow() + 1) / 5f, itemText.getDxShadow(), itemText.getDyShadow(),
                                    Color.parseColor(buildColorString(opacityShadow, color)));
                            currentViewOfText.setTag(itemText);
                        }
                        break;
                }
            }
        });

        dialog.show();
    }

    private String buildColorString(int opacity, String color) {
        return "#" + Utility.convertOpacityToHexString(opacity) + color;
    }
}