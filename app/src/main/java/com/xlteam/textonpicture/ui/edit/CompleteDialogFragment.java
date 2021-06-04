package com.xlteam.textonpicture.ui.edit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.utility.utils.Constant;
import com.xlteam.textonpicture.external.utility.utils.FileUtils;
import com.xlteam.textonpicture.external.utility.utils.Utility;
import com.xlteam.textonpicture.ui.commondialog.DialogSaveChangesBuilder;
import com.xlteam.textonpicture.ui.home.HomePageActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CompleteDialogFragment extends DialogFragment {

    private Context mContext;
    private Bitmap mBitmap;
    private Dialog mDialogBack;
    private ImageView imgSavedPicture, imgBack, imgShare;
    private AdView mAdView;
    private TextView tvSavedPath, tvSaveSuccess;
    private LinearLayout layoutSuccess;
    private ProgressBar loading;

    public CompleteDialogFragment() {

    }

    public static CompleteDialogFragment newInstance(Bitmap bitmap) {
        CompleteDialogFragment completeDialogFragment = new CompleteDialogFragment();
        Bundle args = new Bundle();
        if (bitmap != null) args.putParcelable("bitmap_picture", bitmap);
        completeDialogFragment.setArguments(args);
        return completeDialogFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_TextOnPicture);
        if (getArguments() != null) {
            mBitmap = getArguments().getParcelable("bitmap_picture");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(mContext, getTheme()) {
            @Override
            public void onBackPressed() {
                showDialogBack(mContext);
            }
        };
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.dialog_complete, container, false);
        MobileAds.initialize(mContext, initializationStatus -> {
        });
        imgSavedPicture = root.findViewById(R.id.image_saved_picture);
        imgBack = root.findViewById(R.id.image_back);
        imgShare = root.findViewById(R.id.image_share);
        mAdView = root.findViewById(R.id.adView);
        tvSavedPath = root.findViewById(R.id.tvSavedPath);
        layoutSuccess = root.findViewById(R.id.layout_success);
        tvSaveSuccess = root.findViewById(R.id.tvSaveSuccess);
        loading = root.findViewById(R.id.loading);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        imgSavedPicture.setImageBitmap(mBitmap);
        imgBack.setOnClickListener(view -> showDialogBack(mContext));

        imgShare.setOnClickListener(view -> {
            //share ảnh
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                File saveFolder = FileUtils.findExistingFolderSaveImage();
                if (saveFolder != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat(Constant.SAVE_DATE_TIME_FORMAT, Locale.getDefault());
                    String savedPath = saveFolder.getAbsolutePath() + File.separator + sdf.format(new Date(Utility.now())) + ".png";
                    File savedFile = Utility.bitmapToFile(mBitmap, savedPath);
                    if (savedFile != null) {
                        MediaScannerConnection.scanFile(mContext,
                                new String[]{savedPath}, null, (path, uri) -> {
                                    tvSavedPath.setText(mContext.getString(R.string.file_path, savedPath));
                                    layoutSuccess.setVisibility(View.VISIBLE);
                                    loading.setVisibility(View.GONE);
                                }
                        );
                    } else {
                        layoutSuccess.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        tvSaveSuccess.setText(R.string.save_fail);
                        tvSavedPath.setText(R.string.try_again);
                    }
                }
            }
        }).start();

        return root;
    }

    private void showDialogBack(Context context) {
        if (mDialogBack == null)
            mDialogBack = DialogSaveChangesBuilder.create(context).setTitleMessage("Bạn có muốn quay lại màn hình chỉnh sửa ảnh không?")
                    .setFirstButton(view -> mDialogBack.dismiss(), "Đóng")
                    .setSecondButton(view -> dismiss(), "Quay lại")
                    .setThirdButton(view -> {
                        Intent intent = new Intent(mContext, HomePageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        ((Activity) mContext).finish();
                    }, "Trang chủ").build();
        mDialogBack.show();
    }
}
