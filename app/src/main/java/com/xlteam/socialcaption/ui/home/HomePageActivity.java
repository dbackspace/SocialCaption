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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
import com.xlteam.socialcaption.ui.home.created.PictureCreatedDialogFragment;
import com.xlteam.socialcaption.ui.home.firebase.PictureFirebaseDialogFragment;
import com.xlteam.socialcaption.ui.home.local.PictureLocalDialogFragment;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity implements PictureHomeAdapter.PictureHomeCallback {
    RecyclerView rvLocal, rvFirebase, rvCreated;
    TextView tvViewMoreFirebase, tvViewMoreCreated, tvViewMoreLocal;
    TextView tvEmptyCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        rvLocal = findViewById(R.id.rv_picture_local);
        rvFirebase = findViewById(R.id.rv_picture_firebase);
        rvCreated = findViewById(R.id.rv_picture_created);
        tvViewMoreLocal = findViewById(R.id.view_more_picture_local);
        tvViewMoreFirebase = findViewById(R.id.view_more_picture_firebase);
        tvViewMoreCreated = findViewById(R.id.view_more_picture_created);
        tvEmptyCreated = findViewById(R.id.tv_empty_picture_created);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        rvLocal.setAdapter(new PictureHomeAdapter(1, Utility.getAllShownImagesPath(HomePageActivity.this), HomePageActivity.this));
                        rvCreated.setAdapter(new PictureHomeAdapter(3, FileUtils.getListPathsIfFolderExist(), HomePageActivity.this));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        rvLocal.setAdapter(new PictureHomeAdapter(1, new ArrayList<>(), HomePageActivity.this));
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
//        rvGallery.setDrawingCacheEnabled(true);
//        rvGallery.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvLocal.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

//        rvUrl.setDrawingCacheEnabled(true);
//        rvUrl.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvFirebase.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvFirebase.setAdapter(new PictureHomeAdapter(2, Utility.getUrlPictureHome(), this));

//        rvCreated.setDrawingCacheEnabled(true);
//        rvCreated.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvCreated.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        if (FileUtils.getListPathsIfFolderExist().isEmpty()) {
            tvEmptyCreated.setVisibility(View.VISIBLE);
            rvCreated.setVisibility(View.GONE);
        }
//
        tvViewMoreLocal.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ViManager.getInstance().setFragmentDefaultAnimation(HomePageActivity.this, fragmentTransaction);
            PictureLocalDialogFragment pictureLocalDialogFragment = new PictureLocalDialogFragment();
            pictureLocalDialogFragment.show(fragmentTransaction, "dialog_local");
        });

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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ViManager.getInstance().setFragmentDefaultAnimation(this, fragmentTransaction);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
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

    @Override
    public void selectPhoto(int numberFont) {

    }

    @Override
    public void pickPhoto() {

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