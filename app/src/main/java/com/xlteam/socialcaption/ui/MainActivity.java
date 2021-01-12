package com.xlteam.socialcaption.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.xlteam.socialcaption.BuildConfig;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.Constant;
import com.xlteam.socialcaption.external.utility.Utility;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;
import com.xlteam.socialcaption.ui.gallery.GalleryFragment;
import com.xlteam.socialcaption.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private final int HOME = 0, GALLERY = 1;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditCaptionActivity.class);
            startActivity(intent);
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
                    break;
                case R.id.nav_feedback:
                    Utility.sendEmailFeedback(this);
                    break;
                case R.id.nav_share:
                    shareApp();
                    break;
                case R.id.nav_other_app:
                    recommendApp();
                    break;
                case R.id.nav_term_of_use:
                    break;
                case R.id.nav_license:
                    break;
            }
            return false;
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        selectNavigation(HOME);
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
}