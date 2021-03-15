package com.xlteam.socialcaption.ui;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.xlteam.socialcaption.BuildConfig;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.animation.ViManager;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;
import com.xlteam.socialcaption.ui.home.created.PictureCreatedDialogFragment;
import com.xlteam.socialcaption.ui.home.firebase.PictureFirebaseDialogFragment;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EDIT_CAPTION_ACTIVITY = 1;
    private NavigationView navigationView;
    private final int HOME = 0, GALLERY = 1, SAVED = 2;
    private DrawerLayout drawer;
    private Fragment currentFragment;
    private AdView mAdView;
    private ImageView imgMenu, imgCreatePicture;
    private TextView tvTitle;
    private RelativeLayout toolbarCustom;

    // custom tool bar for gallery onItemLongClicked
    private RelativeLayout toolbarGallerySecond;
    public ImageView imgCheckAll;
    public TextView tvTotalChecked;
    private boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        asyncLayoutInflate();
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        tvTitle = findViewById(R.id.tv_title);
        imgMenu = findViewById(R.id.btn_menu);
        imgCreatePicture = findViewById(R.id.btn_create_picture);
        toolbarCustom = findViewById(R.id.toolbarCustom);

        toolbarGallerySecond = findViewById(R.id.toolbar_gallery_second);
        tvTotalChecked = findViewById(R.id.tv_number_image_checked);
        imgCheckAll = findViewById(R.id.image_check_all);

        isChecked = imgCheckAll.isActivated();
        imgCheckAll.setOnClickListener(view -> {
            Log.e("TEST", "isChecked = " + isChecked);

            imgCheckAll.setActivated(!isChecked);
            ((PictureCreatedDialogFragment) currentFragment).isImageCheckAllChecked(!isChecked);
            isChecked = !isChecked;
        });

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectNavigation(HOME);
                    drawer.closeDrawer(GravityCompat.START, true);
                    return true;
                case R.id.nav_gallery:
                    selectNavigation(GALLERY);
                    drawer.closeDrawer(GravityCompat.START, true);
                    return true;
                case R.id.nav_rate:
                    rateApp();
                    drawer.closeDrawer(GravityCompat.START, true);
                    break;
                case R.id.nav_feedback:
                    Utility.sendEmailFeedback(this);
                    drawer.closeDrawer(GravityCompat.START, true);
                    break;
                case R.id.nav_share:
                    shareApp();
                    drawer.closeDrawer(GravityCompat.START, true);
                    break;
                case R.id.nav_other_app:
                    recommendApp();
                    drawer.closeDrawer(GravityCompat.START, true);
                    break;
                case R.id.nav_term_of_use:
                    showDialogFullScreen("term_of_use.html", R.string.term_of_use);
                    drawer.closeDrawer(GravityCompat.START, true);
                    break;
                case R.id.nav_license:
                    showDialogFullScreen("license.html", R.string.license);
                    drawer.closeDrawer(GravityCompat.START, true);
                    break;
            }
            return false;
        });
        imgMenu.setOnClickListener(view -> drawer.openDrawer(GravityCompat.START, true));
        imgCreatePicture.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditCaptionActivity.class);
            startActivityForResult(intent, REQUEST_CODE_EDIT_CAPTION_ACTIVITY);
        });
        selectNavigation(HOME);
    }

    private void asyncLayoutInflate() {
        int[] layoutList = new int[]{R.layout.fragment_picture_list_firebase,
                R.layout.fragment_dialog_firebase,
                R.layout.fragment_dialog_search,
                R.layout.fragment_dialog_created,
        };
        for (int asyncLayoutInflate : layoutList) {
            AsyncLayoutInflateManager.getInstance(this).doAsyncInflate(asyncLayoutInflate, null);
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

    private void selectNavigation(int type) {
        if (type == HOME) {//giá»¯ tráº¡ng thÃ¡i khi chá»n láº¡i item
            if (!(currentFragment instanceof PictureFirebaseDialogFragment)) {
                currentFragment = new PictureFirebaseDialogFragment();
                navigationView.setCheckedItem(R.id.nav_home);
                tvTitle.setText(R.string.title_home);
            }
        } else if (type == GALLERY) { //giá»¯ tráº¡ng thÃ¡i khi chá»n láº¡i item
            if (!(currentFragment instanceof PictureCreatedDialogFragment)) {

            }
        }
        replaceFragment(currentFragment);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START, true);
        } else if (currentFragment instanceof PictureCreatedDialogFragment && toolbarCustom.getVisibility() == View.INVISIBLE) {

            ((PictureCreatedDialogFragment) currentFragment).clearSelectMode();
        } else if (navigationView.getMenu().findItem(R.id.nav_gallery).isChecked()) {
            selectNavigation(HOME);
        } else {
            super.onBackPressed();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the EditCaptionActivity with an OK result
        if (requestCode == REQUEST_CODE_EDIT_CAPTION_ACTIVITY) {
            if (resultCode == EditCaptionActivity.RESULT_CODE_NEW_IMAGE_WAS_CREATED) {
                if (currentFragment instanceof PictureCreatedDialogFragment) {
                    ((PictureCreatedDialogFragment) currentFragment).updateUI();
                }
            }
        }
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
        AsyncLayoutInflateManager.getInstance(this).onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }
}