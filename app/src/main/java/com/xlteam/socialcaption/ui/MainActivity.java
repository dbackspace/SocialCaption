package com.xlteam.socialcaption.ui;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.xlteam.socialcaption.external.utility.Constant;
import com.xlteam.socialcaption.external.utility.Utility;
import com.xlteam.socialcaption.external.utility.animation.ViManager;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;
import com.xlteam.socialcaption.ui.gallery.GalleryFragment;
import com.xlteam.socialcaption.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private final int HOME = 0, GALLERY = 1;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Fragment currentFragment;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asyncLayoutInflate();
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_home);
        setSupportActionBar(toolbar);
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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        selectNavigation(HOME);
    }

    private void asyncLayoutInflate() {
        int[] layoutList = new int[]{R.layout.fragment_caption_list,
                R.layout.fragment_home,
                R.layout.fragment_dialog_search,
                R.layout.fragment_gallery};
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
        // TODO: phát triển thêm tính năng rate in app
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
        // TODO: phát triển thêm tính năng hiện dialog show các app gợi ý trực tiếp
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
        if (type == HOME) {//giữ trạng thái khi chọn lại item
            if (!(currentFragment instanceof HomeFragment)) {
                currentFragment = new HomeFragment();
                navigationView.setCheckedItem(R.id.nav_home);
                toolbar.setTitle(R.string.title_home);
            }
        } else if (type == GALLERY) { //giữ trạng thái khi chọn lại item
            if (!(currentFragment instanceof GalleryFragment)) {
                currentFragment = new GalleryFragment();

                navigationView.setCheckedItem(R.id.nav_gallery);
                toolbar.setTitle(R.string.menu_gallery);
            }
        }
        replaceFragment(currentFragment);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START, true);
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