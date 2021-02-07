package com.xlteam.socialcaption.ui.edit;

import android.Manifest;
import android.annotation.SuppressLint;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.xlteam.socialcaption.external.repository.UserCaptionRepository;
import com.xlteam.socialcaption.external.utility.gesture.MultiTouchListener;
import com.xlteam.socialcaption.external.utility.gesture.OnGestureControl;
import com.xlteam.socialcaption.external.utility.gesture.OnMultiTouchListener;
import com.xlteam.socialcaption.external.utility.gesture.OnPhotoEditorListener;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.thread.BitmapLruCache;
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.external.utility.utils.FileUtils;
import com.xlteam.socialcaption.external.utility.utils.MapViewUtils;
import com.xlteam.socialcaption.external.utility.utils.PrefUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.model.Color;
import com.xlteam.socialcaption.model.CommonCaption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.xlteam.socialcaption.external.utility.utils.Constant.SAVE_DATE_TIME_FORMAT;

public class EditCaptionActivity extends AppCompatActivity implements DialogAddTextBuilder.SavedCallback,
        OnPhotoEditorListener, OnMultiTouchListener {
    private static final int RESULT_LOAD_IMG = 1;
    private ImageView imgBack, imgCancelText, imgDoneText;
    private TextView tvDone;
    private CommonCaption mCommonCaption;
    private ImageView mImgBackground;
    private ConstraintLayout layoutMenu, layoutMenuText;
    private RelativeLayout layoutTop, layoutBottom;
    private RelativeLayout layoutSaveCancel, layoutSaveCancelText;

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
    private Context mContext;

    // relative background
    private RelativeLayout relativeBackground;

    private List<View> addedViews;
    private RelativeLayout rlTrash;
    private RelativeLayout containerTrash;

    //text editor
    private RecyclerView rvFont, rvColor;
    private int mGravityText, mNumberBg, mNumberColor, mNumberFont;
    private ImageView imgGravity;
    private TextView tvBg;
    private TextView mTextViewClicked;
    private ItemText mItemTextViewClicked;
    private RelativeLayout containerBgImage;
    private FontAdapter mFontAdapter;
    private ColorAdapter mColorAdapter;
    private MapViewUtils mapViewUtils;

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

        mapViewUtils = MapViewUtils.getInstance();
        addedViews = new ArrayList<>();
        Intent intent = getIntent();
        mCommonCaption = (CommonCaption) intent.getSerializableExtra(Constant.EXTRA_CAPTION);
        if (mCommonCaption != null) {
            //init old data
        } else {
            //show dialog
        }
        findViewById();
        initTextEditor();
        tvDone.setOnClickListener(v -> {
//            UserCaption userCaption = new UserCaption(mEdtCaption.getText().toString(), mPathImg, mColorTextDefault, mFontDefault, mAlignTextDefault, true);
//            mRepository.insertUserCaption(userCaption);
//            Toast.makeText(this, "Đã lưu caption. Để xem lại, bạn có thể vào Menu -> Caption đã tạo!", Toast.LENGTH_LONG).show();
//            this.finish();
            saveImageCreatedToSdcard(relativeBackground);
        });
        tvDone.setClickable(false);
        imgBack.setOnClickListener(v -> finish());
        imgCancelText.setOnClickListener(view -> showTextMode(false));
        imgDoneText.setOnClickListener(view -> showTextMode(false));
    }

    private void findViewById() {
        imgBack = findViewById(R.id.btn_edit_back_and_cancel);
        tvDone = findViewById(R.id.tv_edit_save);
        mImgBackground = findViewById(R.id.img_edit_background);
        layoutTop = findViewById(R.id.layout_top);
        layoutBottom = findViewById(R.id.layout_bottom);
        layoutMenu = findViewById(R.id.layout_menu);
        layoutSaveCancel = findViewById(R.id.layout_save_cancel);
        relativeBackground = findViewById(R.id.relative_background_save_img);
        rlTrash = findViewById(R.id.rlTrash);
        containerTrash = findViewById(R.id.container_trash);
        containerBgImage = findViewById(R.id.container_background_image);

        //text editor
        imgCancelText = findViewById(R.id.btn_cancel_edit_text);
        imgDoneText = findViewById(R.id.image_save_text);
        layoutMenuText = findViewById(R.id.layout_menu_text);
        layoutSaveCancelText = findViewById(R.id.layout_save_cancel_text);
        rvColor = findViewById(R.id.rvColor);
        rvFont = findViewById(R.id.rvFont);
        imgGravity = findViewById(R.id.imgGravity);
        tvBg = findViewById(R.id.tvBg);
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

    public void onAddTextClicked(View view) {
        layoutTop.setVisibility(View.INVISIBLE);
        layoutBottom.setVisibility(View.INVISIBLE);
        rvColor.setVisibility(View.GONE);
        rvFont.setVisibility(View.GONE);
        Dialog addTextDialog = new DialogAddTextBuilder(this, null).build();
        addTextDialog.setOnCancelListener(dialog -> {
            layoutTop.setVisibility(View.VISIBLE);
            layoutBottom.setVisibility(View.VISIBLE);
        });
        addTextDialog.show();
    }

    public void onAddImageClicked(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void onRotateClicked(View view) {
        mImgBackground.setRotation(mImgBackground.getRotation() + -90);
        enableBtnSave();
    }

    public void onCropClicked(View view) {
        // start cropping activity for pre-acquired image saved on the device
        CropImage.activity(Utility.getImageUri(getApplicationContext(), getBitmapFromImageView(mImgBackground)))
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void onFlipClicked(View view) {
        flipCurrent = (flipCurrent + 180) % 360;
        mImgBackground.setRotationY(flipCurrent);
        enableBtnSave();
    }

    public void onTextChangeClicked(View view) {
        layoutTop.setVisibility(View.INVISIBLE);
        layoutBottom.setVisibility(View.INVISIBLE);
        rvColor.setVisibility(View.GONE);
        rvFont.setVisibility(View.GONE);
        Dialog addTextDialog = new DialogAddTextBuilder(this, null).build(); //truyền text vào đây
        addTextDialog.setOnCancelListener(dialog -> {
            layoutTop.setVisibility(View.VISIBLE);
            layoutBottom.setVisibility(View.VISIBLE);
        });
        addTextDialog.show();
    }

    public void onTextColorClicked(View view) {
        rvColor.setVisibility(View.VISIBLE);
        rvFont.setVisibility(View.GONE);
    }

    public void onTextFontClicked(View view) {
        rvFont.setVisibility(View.VISIBLE);
        rvColor.setVisibility(View.GONE);
    }

    public void onTextBgClicked(View view) {
        Color color = ColorDataSource.getInstance().getAllData().get(mNumberColor);
        if (mNumberBg == Constant.BACKGROUND_COLOR_0) {
            mNumberBg = Constant.BACKGROUND_COLOR_50;
            Utility.setColorForView(tvBg, "#60FFFFFF");
            Utility.setColorForTextView(tvBg, "#FFFFFFFF");
            String colorBlur = "#80" + color.getColor().substring(3);
            Utility.setColorForView(mTextViewClicked, colorBlur);
            Utility.setColorForTextView(mTextViewClicked, color.getTextColor());

        } else if (mNumberBg == Constant.BACKGROUND_COLOR_50) {
            mNumberBg = Constant.BACKGROUND_COLOR_100;
            Utility.setColorForView(tvBg, "#FFFFFFFF");
            Utility.setColorForTextView(tvBg, "#FF000000");
            Utility.setColorForView(mTextViewClicked, color.getColor());
            Utility.setColorForTextView(mTextViewClicked, color.getTextColor());
        } else {
            mNumberBg = Constant.BACKGROUND_COLOR_0;
            Utility.setColorForView(tvBg, "#00FFFFFF");
            Utility.setColorForTextView(tvBg, "#FFFFFFFF");
            Utility.setColorForView(mTextViewClicked, "#00FFFFFF");
            Utility.setColorForTextView(mTextViewClicked, color.getColor());
        }
        mItemTextViewClicked.setBg(mNumberBg);
        mapViewUtils.put(mTextViewClicked, mItemTextViewClicked);
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
        mTextViewClicked.setGravity(mGravityText);
        mItemTextViewClicked.setGravity(mGravityText);
        mapViewUtils.put(mTextViewClicked, mItemTextViewClicked);
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
                                Log.e(this, savePath);
                                BitmapLruCache.getInstance().saveBitmapToCache(savePath, bitmap);
                                PrefUtils.setListItemGallery(mContext, savePath);
                                finish();
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onSaveClicked(String text) {
        /**
         * Không tạo empty text.
         * TODO: Xử lý trong textChange ở DialogAddText, có cho enable nút DONE hay không
         */
        if (TextUtils.isEmpty(text.trim())) {
            return;
        }
        final View textAddedView = getTextStickerLayout();
        final TextView textInputTv = textAddedView.findViewById(R.id.text_tv);
        final FrameLayout frameBorder = textAddedView.findViewById(R.id.text_border);
        textInputTv.setBackgroundResource(R.drawable.bg_text_view_edit);
        textInputTv.setText(text);
        textInputTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                getResources().getDimension(R.dimen.text_added_default_size));

        MultiTouchListener multiTouchListener = new MultiTouchListener(
                rlTrash,
                relativeBackground,
                mImgBackground,
                this, mContext);
        multiTouchListener.setOnGestureControl(new OnGestureControl() {
            boolean isDownAlready = false;

            @Override
            public void onClick() {
                boolean isBackgroundVisible = frameBorder.getTag() != null && (boolean) frameBorder.getTag();
                if (isBackgroundVisible && isDownAlready) {
                    String textInput = textInputTv.getText().toString();
                    int currentTextColor = textInputTv.getCurrentTextColor();
                    float textSize = textInputTv.getTextSize();
                    int textAlignment = textInputTv.getTextAlignment();
                    ItemText itemText = new ItemText(textInput, currentTextColor, textSize, textAlignment);

                    // Solution tạm thời cho việc get itemText từ map
                    ItemText itemTextTemp = mapViewUtils.get(textInputTv);
                    if (itemTextTemp != null) {
                        // editClickTextByClickTextView()
                        mTextViewClicked = textInputTv;
                        mItemTextViewClicked = itemTextTemp;
                        mNumberBg = mItemTextViewClicked.getBg();
                        mNumberColor = mItemTextViewClicked.getColor();
                        mGravityText = mItemTextViewClicked.getGravity();
                        mNumberFont = mItemTextViewClicked.getFont();
                    }

                    editTextByClickTextView(textAddedView, itemText);
                }
            }

            @Override
            public void onDown() {
                boolean isBackgroundVisible = frameBorder.getTag() != null && (boolean) frameBorder.getTag();
                if (!isBackgroundVisible) {
                    frameBorder.setBackgroundResource(R.drawable.background_border_text_added);
                    frameBorder.setTag(true);
                    updateViewsBordersVisibilityExcept(textAddedView);
                    isDownAlready = true;
                } else {
                    isDownAlready = false;
                }
            }

            @Override
            public void onLongClick() {
            }
        });

        // Solution tạm thời cho việc put vào map
        ItemText itemText = new ItemText(text);

        textAddedView.setOnTouchListener(multiTouchListener);
        addViewToParent(textAddedView, textInputTv, itemText);
    }

    public void editTextByClickTextView(View view, ItemText itemText) {
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
            Utility.setColorForView(tvBg, "#00FFFFFF");
            Utility.setColorForTextView(tvBg, "#FFFFFFFF");
        } else if (mNumberBg == Constant.BACKGROUND_COLOR_50) {
            Utility.setColorForView(tvBg, "#60FFFFFF");
            Utility.setColorForTextView(tvBg, "#FFFFFFFF");
        } else {
            Utility.setColorForView(tvBg, "#FFFFFFFF");
            Utility.setColorForTextView(tvBg, "#FF000000");
        }
        showTextMode(true);

    }

    private void editText(View view, String inputText, int colorCode) {
        TextView inputTextView = view.findViewById(R.id.text_tv);
        if (inputTextView != null && addedViews.contains(view) && !TextUtils.isEmpty(inputText)) {
            inputTextView.setText(inputText);
            inputTextView.setTextColor(colorCode);
            relativeBackground.updateViewLayout(view, view.getLayoutParams());
            int i = addedViews.indexOf(view);
            if (i > -1) addedViews.set(i, view);
        }
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

    private void clickBackgroundImage() {
        // disable all border and delete button in all text added
        for (View view : addedViews) {
            FrameLayout border = view.findViewById(R.id.text_border);
            border.setBackgroundResource(0);
            border.setTag(false);
        }
    }

    private void addViewToParent(View view, TextView textInputView, ItemText itemText) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        relativeBackground.addView(view, params);
        addedViews.add(view);
        mapViewUtils.put(textInputView, itemText);
        updateViewsBordersVisibilityExcept(view);
    }

    private void deleteViewFromParent(View view) {
        relativeBackground.removeView(view);
        addedViews.remove(view);
        mapViewUtils.remove(view);
        relativeBackground.invalidate();
        updateViewsBordersVisibilityExcept(null);
    }

    private void updateViewsBordersVisibilityExcept(@Nullable View keepView) {
        for (View view : addedViews) {
            if (view != keepView) {
                FrameLayout border = view.findViewById(R.id.text_border);
                border.setBackgroundResource(0);
                border.setTag(false);
            }
        }
    }

    @Override
    public void onAddViewListener(int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(int numberOfAddedViews) {

    }

    @Override
    public void onEventDownChangeListener() {

    }

    @Override
    public void onEventMoveChangeListener() {
        containerTrash.setVisibility(View.VISIBLE);
        layoutTop.setVisibility(View.INVISIBLE);
        rvColor.setVisibility(View.GONE);
        rvFont.setVisibility(View.GONE);
        layoutBottom.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onEventUpChangeListener() {
        containerTrash.setVisibility(View.GONE);
        layoutTop.setVisibility(View.VISIBLE);
        layoutBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRemoveViewListener(View removedView) {
        deleteViewFromParent(removedView);
    }


    private void initTextEditor() {
        rvFont.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mFontAdapter = new FontAdapter(this, numberFont -> {
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "font/" + FontDataSource.getInstance().getAllFonts().get(numberFont).getFont());
            mTextViewClicked.setTypeface(typeface);
            mItemTextViewClicked.setFont(numberFont);
            mapViewUtils.put(mTextViewClicked, mItemTextViewClicked);
        });
        rvFont.setAdapter(mFontAdapter);

        rvColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mColorAdapter = new ColorAdapter(colorPosition -> {
            mNumberColor = colorPosition;
            Color color = ColorDataSource.getInstance().getAllData().get(colorPosition);
            if (mNumberBg == Constant.BACKGROUND_COLOR_0) {
                Utility.setColorForView(mTextViewClicked, "#00FFFFFF");
                Utility.setColorForTextView(mTextViewClicked, color.getColor());
            } else if (mNumberBg == Constant.BACKGROUND_COLOR_50) {
                String colorBlur = "#80" + color.getColor().substring(3);//loại bỏ #FF, thay bằng #80
                Utility.setColorForView(mTextViewClicked, colorBlur);
                Utility.setColorForTextView(mTextViewClicked, color.getTextColor());
            } else {
                Utility.setColorForView(mTextViewClicked, color.getColor());
                Utility.setColorForTextView(mTextViewClicked, color.getTextColor());
            }
            mItemTextViewClicked.setColor(colorPosition);
            mapViewUtils.put(mTextViewClicked, mItemTextViewClicked);
        });
        rvColor.setAdapter(mColorAdapter);

        containerBgImage.setOnClickListener(v -> clickBackgroundImage());
    }

    private void showTextMode(boolean isShow) {
        if (isShow && !Utility.isEmpty(addedViews)) {
            layoutSaveCancelText.setVisibility(View.VISIBLE);
            layoutMenuText.setVisibility(View.VISIBLE);
            layoutSaveCancel.setVisibility(View.GONE);
            layoutMenu.setVisibility(View.GONE);
        } else {
            layoutSaveCancelText.setVisibility(View.GONE);
            layoutMenuText.setVisibility(View.GONE);
            layoutSaveCancel.setVisibility(View.VISIBLE);
            layoutMenu.setVisibility(View.VISIBLE);
            rvFont.setVisibility(View.GONE);
            rvColor.setVisibility(View.GONE);
        }
    }
}
