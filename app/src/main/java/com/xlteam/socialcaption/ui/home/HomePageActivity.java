package com.xlteam.socialcaption.ui.home;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.xlteam.socialcaption.BuildConfig;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.animation.ViManager;
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.external.utility.utils.FileUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;
import com.xlteam.socialcaption.ui.home.created.PictureCreatedDialogFragment;
import com.xlteam.socialcaption.ui.home.firebase.PictureFirebaseDialogFragment;

public class HomePageActivity extends AppCompatActivity {
    RecyclerView rvFirebase, rvCreated;
    TextView tvViewMoreFirebase, tvViewMoreCreated;
    TextView tvEmptyCreated;
    LinearLayout layoutTakePhoto, layoutGallery;

    private static final int REQUEST_CODE_PICK_PHOTO_HOME = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        rvFirebase = findViewById(R.id.rv_picture_firebase);
        rvCreated = findViewById(R.id.rv_picture_created);
        tvViewMoreFirebase = findViewById(R.id.view_more_picture_firebase);
        tvViewMoreCreated = findViewById(R.id.view_more_picture_created);
        tvEmptyCreated = findViewById(R.id.tv_empty_picture_created);
        layoutTakePhoto = findViewById(R.id.layout_take_photo);
        layoutGallery = findViewById(R.id.layout_gallery);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        rvCreated.setAdapter(new PictureHomeAdapter(HomePageActivity.this, 3, FileUtils.getListPathsIfFolderExist()));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
//        rvGallery.setDrawingCacheEnabled(true);
//        rvGallery.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

//        rvUrl.setDrawingCacheEnabled(true);
//        rvUrl.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvFirebase.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvFirebase.setAdapter(new PictureHomeAdapter(this, 2, Utility.getUrlPictureHome()));

//        rvCreated.setDrawingCacheEnabled(true);
//        rvCreated.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvCreated.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        if (FileUtils.getListPathsIfFolderExist().isEmpty()) {
            tvEmptyCreated.setVisibility(View.VISIBLE);
            rvCreated.setVisibility(View.GONE);
        }

        tvViewMoreFirebase.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ViManager.getInstance().setFragmentDefaultAnimation(HomePageActivity.this, fragmentTransaction);
            PictureFirebaseDialogFragment pictureFirebaseDialogFragment = new PictureFirebaseDialogFragment();
            pictureFirebaseDialogFragment.show(fragmentTransaction, "dialog_firebase");
        });

        tvViewMoreCreated.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ViManager.getInstance().setFragmentDefaultAnimation(HomePageActivity.this, fragmentTransaction);
            PictureCreatedDialogFragment pictureCreatedDialogFragment = new PictureCreatedDialogFragment();
            pictureCreatedDialogFragment.show(fragmentTransaction, "dialog_created");
        });
        layoutTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        layoutGallery.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 5:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        Toast.makeText(this, "Bạn chưa chọn ảnh.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(this, EditCaptionActivity.class);
                        intent.putExtra("EXTRA_PICK_PHOTO_URL", data.getData());
                        intent.putExtra("EXTRA_TYPE_PICTURE", 0);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    private void rateApp() {
        Uri uri = Uri.parse(Constant.LINK_MARKET_APP + BuildConfig.APPLICATION_ID);
        Intent intentMarket = new Intent(Intent.ACTION_VIEW, uri);
        intentMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(intentMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.LINK_GOOGLE_PLAY_APP + BuildConfig.APPLICATION_ID)));
        }
    }

    private void recommendApp() {
        Uri uri = Uri.parse(Constant.LINK_MARKET_DEVELOPER);
        Intent intentMarket = new Intent(Intent.ACTION_VIEW, uri);
        intentMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(intentMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.LINK_GOOGLE_PLAY_DEVELOPER)));
        }
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage = Constant.LINK_GOOGLE_PLAY_APP + BuildConfig.APPLICATION_ID;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void showDialogFullScreen(String assetName, int title) {
        Dialog dialog = new Dialog(this, R.style.Theme_SocialCaption);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_web_view_fullscreen);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        WebView webView = dialog.findViewById(R.id.web);
        webView.setBackgroundColor(ContextCompat.getColor(this, R.color.color_FC));
        String urlWebView = "file:///android_asset/" + assetName;
        webView.loadUrl(urlWebView);
        ImageView imgBack = dialog.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mAdView != null) {
//            mAdView.resume();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mAdView != null) {
//            mAdView.pause();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        AsyncLayoutInflateManager.getInstance(this).onDestroy();
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
//    }
}