package com.xlteam.socialcaption.ui;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.xlteam.socialcaption.BuildConfig;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.animation.ViManager;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.ui.home.PictureHomeAdapter;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity implements PictureHomeAdapter.PictureHomeCallback {
    RecyclerView rvPictureGallery, rvPictureUrl, rvPictureCreated;
    TextView tvViewMorePictureUrl, tvViewMorePictureCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        rvPictureGallery = findViewById(R.id.rv_picture_gallery);
//        rvPictureGallery.setDrawingCacheEnabled(true);
//        rvPictureGallery.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvPictureGallery.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvPictureGallery.setAdapter(new PictureHomeAdapter(1, getAllShownImagesPath(), this));

//        rvPictureUrl = findViewById(R.id.rv_picture_url);
//        rvPictureCreated = findViewById(R.id.rv_picture_created);
//        rvPictureUrl.setDrawingCacheEnabled(true);
//        rvPictureUrl.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        rvPictureUrl.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        rvPictureUrl.setAdapter(new PictureHomeAdapter(2, new ArrayList<>()));
//
//        rvPictureCreated.setDrawingCacheEnabled(true);
//        rvPictureCreated.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        rvPictureCreated.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        rvPictureCreated.setAdapter(new PictureHomeAdapter(3, FileUtils.getListPathsIfFolderExist()));
//
//        tvViewMorePictureUrl = findViewById(R.id.view_more_picture_url);
//        tvViewMorePictureUrl = findViewById(R.id.view_more_picture_created);
//        tvViewMorePictureUrl.setOnClickListener(view -> {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            ViManager.getInstance().setFragmentDefaultAnimation(HomePageActivity.this, fragmentTransaction);
//            HomeFragment homeFragment = new HomeFragment();
//            homeFragment.show(fragmentTransaction, "dialog_home");
//        });
    }

    public ArrayList<String> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);
            Log.d("binh.ngk", "" + absolutePathOfImage);
        }
        return listOfAllImages;
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
        // TODO: phÃ¡t triá»ƒn thÃªm tÃ­nh nÄƒng rate in app
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
        // TODO: phÃ¡t triá»ƒn thÃªm tÃ­nh nÄƒng hiá»‡n dialog show cÃ¡c app gá»£i Ã½ trá»±c tiáº¿p
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