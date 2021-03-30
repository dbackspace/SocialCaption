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
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.xlteam.textonpicture.external.utility.gesture.OnMultiTouchListener;
import com.xlteam.textonpicture.external.utility.gesture.OnPhotoEditorListener;
import com.xlteam.textonpicture.external.utility.logger.Log;
import com.xlteam.textonpicture.external.utility.stickerview.BitmapStickerIcon;
import com.xlteam.textonpicture.external.utility.stickerview.DeleteIconEvent;
import com.xlteam.textonpicture.external.utility.stickerview.FlipHorizontallyEvent;
import com.xlteam.textonpicture.external.utility.stickerview.Sticker;
import com.xlteam.textonpicture.external.utility.stickerview.StickerView;
import com.xlteam.textonpicture.external.utility.stickerview.TextSticker;
import com.xlteam.textonpicture.external.utility.stickerview.ZoomIconEvent;
import com.xlteam.textonpicture.external.utility.utils.Constant;
import com.xlteam.textonpicture.external.utility.utils.FileUtils;
import com.xlteam.textonpicture.external.utility.utils.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

    //align
    private RelativeLayout layoutAlign;
    private ImageView imgAlignCenter;
    private ImageView imgAlignLeft;
    private ImageView imgAlignRight;

    private boolean isHasText = false;

    private RecyclerView rvToolText;
    private ToolTextAdapter mToolTextAdapter;

    // sticker view
    private StickerView stickerView;
    private TextSticker currentTextSticker;

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
        initStickerView();
        relativeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (layoutText.getVisibility() == View.VISIBLE) {
                showTextMode(false);
//                }
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

    private void initStickerView() {
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.ic_close_text_sticker),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.ic_zoom_text_sticker),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon editTextIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.ic_edit_text_sticker),
                BitmapStickerIcon.RIGHT_TOP);
        editTextIcon.setIconEvent(new FlipHorizontallyEvent());

        BitmapStickerIcon balanceIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.ic_balance_text_sticker),
                BitmapStickerIcon.LEFT_BOTTOM);
        balanceIcon.setIconEvent(new FlipHorizontallyEvent());

        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, editTextIcon, balanceIcon));

        //default icon layout
        //stickerView.configDefaultIcons();

        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setLocked(false);
        stickerView.setConstrained(true);
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

        //sticker view
        stickerView = findViewById(R.id.sticker_view);
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
        rvFont.setVisibility(View.GONE);
        layoutAlign.setVisibility(View.GONE);
        layoutShadow.setVisibility(View.GONE);
        if (currentTextSticker != null) {
            sbOpacity.setProgress(currentTextSticker.getItemText().getOpacityText());
        }
    }

    public void onTextBgColorClicked() {
        layoutOpacityColor.setVisibility(View.VISIBLE);
        rvColor.setVisibility(View.VISIBLE);
        rvFont.setVisibility(View.GONE);
        layoutShadow.setVisibility(View.GONE);
        layoutAlign.setVisibility(View.GONE);
        if (currentTextSticker != null) {
            //nếu màu nền trong suốt thì cho bằng 20 để user nhận biết sự thay đổi màu
            sbOpacity.setProgress(currentTextSticker.getItemText().getOpacityBackground());
        }
    }

    public void onTextShadowClicked() {
        rvColor.setVisibility(View.VISIBLE);
        layoutShadow.setVisibility(View.VISIBLE);
        rvFont.setVisibility(View.GONE);
        layoutAlign.setVisibility(View.GONE);
        layoutOpacityColor.setVisibility(View.GONE);
        sbOpacityShadow.setProgress(currentTextSticker.getItemText().getOpacityShadow());
        sbSaturationShadow.setProgress(currentTextSticker.getItemText().getSaturationShadow());
    }

    // Thay đổi font
    public void onTextFontClicked() {
        rvFont.setVisibility(View.VISIBLE);
        layoutOpacityColor.setVisibility(View.GONE);
        rvColor.setVisibility(View.GONE);
        layoutShadow.setVisibility(View.GONE);
        layoutAlign.setVisibility(View.GONE);
        if (currentTextSticker != null) {
            ItemText itemText = currentTextSticker.getItemText();
            fontAdapter.setNumberFont(itemText.getFont());
            fontAdapter.notifyDataSetChanged();
            rvFont.smoothScrollToPosition(itemText.getFont());
            if (isFirstClickFont) {
                rvFont.smoothScrollToPosition(itemText.getFont());
                isFirstClickFont = false;
            }
        }
    }

    public void onTextBendClicked() {
//        mItemTextViewClicked.setBg(mNumberBg);
    }

    public void onTextAlignClicked() {
        layoutAlign.setVisibility(View.VISIBLE);
        rvFont.setVisibility(View.GONE);
        layoutOpacityColor.setVisibility(View.GONE);
        layoutShadow.setVisibility(View.GONE);
        rvColor.setVisibility(View.GONE);
        if (currentTextSticker != null) {
            ItemText itemText = currentTextSticker.getItemText();
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
        // text sticker
        TextSticker sticker = new TextSticker(this);
        sticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.sticker_transparent_background));
        sticker.setText(text);
        sticker.setTextColor(Color.BLACK);
        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        sticker.resizeText();
        currentTextSticker = sticker;
        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                currentTextSticker = (TextSticker) sticker;
                showTextMode(true);
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                currentTextSticker = (TextSticker) sticker;
                showTextMode(true);
                Timber.d("onStickerClicked");
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                Timber.d("onStickerDeleted");
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
                Timber.d("onStickerDragFinished");
                currentTextSticker = (TextSticker) sticker;
                showTextMode(true);
            }

            @Override
            public void onStickerTouchedDown(@NonNull Sticker sticker) {
                Timber.d("onStickerTouchedDown");
                currentTextSticker = (TextSticker) sticker;
                showTextMode(true);
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
                Timber.d("onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
                Timber.d("onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                Timber.d("onDoubleTapped: double tap will be with two click");
            }

            @Override
            public void onStickerBalanced(@NonNull Sticker sticker) {

            }
        });
        stickerView.addSticker(sticker);

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

        ItemText itemText = new ItemText(text);
        currentTextSticker.setItemText(itemText);
        showTextMode(true);
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
                    onTextBendClicked();
                    break;
                case 6:
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
        fontAdapter = new FontAdapter(this);
        fontAdapter.setNumberFont(DEFAULT_FONT_NUMBER);
        rvFont.setAdapter(fontAdapter);

        /* init color*/
        rvColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        ColorAdapter mColorAdapter = new ColorAdapter(this);
        rvColor.setAdapter(mColorAdapter);
        sbOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvValueOpacity.setText(progress + "%");
                ItemText itemText = currentTextSticker.getItemText();
                switch (mToolTextAdapter.getCurrentNumberTool()) {
                    case 1:
                        itemText.setOpacityText(progress);
                        currentTextSticker.setTextColor(Color.parseColor("#" + Utility.convertOpacityToHexString(progress) + itemText.getColorText()));
                        break;
                    case 2:
                        itemText.setOpacityBackground(progress);
                        currentTextSticker.setBackgroundColor("#" + Utility.convertOpacityToHexString(progress) + itemText.getColorBackground());
                        break;
                }
                currentTextSticker.setItemText(itemText);
                stickerView.invalidate();
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
                itemText = currentTextSticker.getItemText();
                currentTextSticker.setShadowLayer((progress + 1) / 5f, itemText.getDxShadow(), itemText.getDyShadow(),
                        "#" + Utility.convertOpacityToHexString(itemText.getOpacityShadow()) + itemText.getColorShadow());
                stickerView.invalidate();
                value = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                itemText.setSaturationShadow(value);
                currentTextSticker.setItemText(itemText);
            }
        });

        sbOpacityShadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value;
            ItemText itemText;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvValueOpacityShadow.setText(progress + "%");
                itemText = currentTextSticker.getItemText();
                currentTextSticker.setShadowLayer((itemText.getSaturationShadow() + 1) / 5f, itemText.getDxShadow(), itemText.getDyShadow(),
                        "#" + Utility.convertOpacityToHexString(progress) + itemText.getColorShadow());
                stickerView.invalidate();
                value = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                itemText.setOpacityShadow(value);
                currentTextSticker.setItemText(itemText);
            }
        });

        View.OnClickListener shadowArrowClick = v -> {
            ItemText itemText = currentTextSticker.getItemText();
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
            currentTextSticker.setShadowLayer((itemText.getSaturationShadow() + 1) / 5f, itemText.getDxShadow(), itemText.getDyShadow(),
                    "#" + Utility.convertOpacityToHexString(itemText.getOpacityShadow()) + itemText.getColorShadow());
            stickerView.invalidate();
            currentTextSticker.setItemText(itemText);
        };
        imgShadowLeft.setOnClickListener(shadowArrowClick);
        imgShadowRight.setOnClickListener(shadowArrowClick);
        imgShadowTop.setOnClickListener(shadowArrowClick);
        imgShadowBottom.setOnClickListener(shadowArrowClick);
        imgShadowCenter.setOnClickListener(shadowArrowClick);

        // init align
        imgAlignRight.setOnClickListener(v -> {
            currentTextSticker.setTextAlign(Layout.Alignment.ALIGN_OPPOSITE);
            currentTextSticker.resizeText();
            stickerView.invalidate();
            ItemText itemText = currentTextSticker.getItemText();
            itemText.setGravity(2);
            setIconGravity(2);
            currentTextSticker.setItemText(itemText);
        });

        imgAlignLeft.setOnClickListener(v -> {
            currentTextSticker.setTextAlign(Layout.Alignment.ALIGN_NORMAL);
            currentTextSticker.resizeText();
            stickerView.invalidate();
            ItemText itemText = currentTextSticker.getItemText();
            itemText.setGravity(0);
            setIconGravity(0);
            currentTextSticker.setItemText(itemText);
        });

        imgAlignCenter.setOnClickListener(v -> {
            currentTextSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
            currentTextSticker.resizeText();
            stickerView.invalidate();
            ItemText itemText = currentTextSticker.getItemText();
            itemText.setGravity(1);
            setIconGravity(1);
            currentTextSticker.setItemText(itemText);
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
            stickerView.setShowBorder(true);
            stickerView.setShowIcons(true);
            layoutText.setVisibility(View.VISIBLE);
            if (currentTextSticker != null) {
                showToolAndBorderOfText(true);
                updateOldStateTool(currentTextSticker.getItemText());
            }
        } else {
            stickerView.setShowBorder(false);
            stickerView.setShowIcons(false);
            layoutText.setVisibility(View.GONE);
            if (currentTextSticker != null) {
                showToolAndBorderOfText(false);
                currentTextSticker = null;
            }
        }
    }

    private void showToolAndBorderOfText(boolean isShow) {
        // show lại 4 icon góc
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
        currentTextSticker.setTypeface(typeface);
        stickerView.invalidate();
        ItemText itemText = currentTextSticker.getItemText();
        itemText.setFont(numberFont);
        currentTextSticker.setItemText(itemText);
    }

    @Override
    public void selectColor(int color) {
        String colorCSS = ColorDataSource.getInstance().getAllData().get(color);
        ItemText itemText = currentTextSticker.getItemText();
        switch (mToolTextAdapter.getCurrentNumberTool()) {
            case 1:
                itemText.setColorText(colorCSS);
                int opacityText = itemText.getOpacityText();
                if (opacityText == 0) { // nếu màu trong suốt thì trả lại 100% màu
                    itemText.setOpacityText(100);
                    currentTextSticker.setItemText(itemText);
                    sbOpacity.setProgress(100);
                } else {
                    currentTextSticker.setTextColor(Color.parseColor("#" + Utility.convertOpacityToHexString(opacityText) + colorCSS));
                    stickerView.invalidate();
                    currentTextSticker.setItemText(itemText);
                }
                break;
            case 2:
                itemText.setColorBackground(colorCSS);
                int opacityBackground = itemText.getOpacityBackground();
                if (opacityBackground == 0) {
                    itemText.setOpacityBackground(20);
                    currentTextSticker.setItemText(itemText);
                    sbOpacity.setProgress(20);
                } else {
                    currentTextSticker.setBackgroundColor("#" + Utility.convertOpacityToHexString(opacityBackground) + colorCSS);
                    stickerView.invalidate();
                    currentTextSticker.setItemText(itemText);
                }

                break;
            case 3:
                itemText.setColorShadow(colorCSS);
                int opacityShadow = itemText.getOpacityShadow();
                if (opacityShadow == 0) {
                    itemText.setOpacityShadow(100);
                    currentTextSticker.setItemText(itemText);
                    sbOpacityShadow.setProgress(100);
                } else {
                    currentTextSticker.setShadowLayer((itemText.getSaturationShadow() + 1) / 5f, itemText.getDxShadow(), itemText.getDyShadow(),
                            "#" + Utility.convertOpacityToHexString(opacityShadow) + colorCSS);
                    stickerView.invalidate();
                    currentTextSticker.setItemText(itemText);
                }
                break;
        }
    }
}