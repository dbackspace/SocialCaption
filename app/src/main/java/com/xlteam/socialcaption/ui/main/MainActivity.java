package com.xlteam.socialcaption.ui.main;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.techyourchance.fragmenthelper.FragmentContainerWrapper;
import com.xlteam.socialcaption.ui.common.bottomnavigation.BottomNavViewMvc;
import com.xlteam.socialcaption.ui.common.controllers.BaseActivity;
import com.xlteam.socialcaption.ui.common.screensnavigator.ScreensNavigator;
import com.xlteam.socialcaption.ui.upload.DialogUploadBuilder;

public class MainActivity extends BaseActivity implements
        FragmentContainerWrapper,
        BottomNavViewMvc.Listener {

    private ScreensNavigator mScreensNavigator;
    private BottomNavViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScreensNavigator = getControllerCompositionRoot().getScreensNavigator();
        mViewMvc = getControllerCompositionRoot().getViewMvcFactory().getBottomNavViewMvc(null);
        setContentView(mViewMvc.getRootView());

        if (savedInstanceState == null) {
            mScreensNavigator.toHomePageScreen();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    private void replaceFragment(int id, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }

    private void showDialogUpload() {
        DialogUploadBuilder dialogUploadBuilder = new DialogUploadBuilder(this);
        dialogUploadBuilder.build().show();
    }

    @NonNull
    @Override
    public ViewGroup getFragmentContainer() {
        return mViewMvc.getFragmentFrame();
    }

    @Override
    public void onHomePageClicked() {
        mScreensNavigator.toHomePageScreen();
    }

    @Override
    public void onTopsListClicked() {

    }

    @Override
    public void onCategoryClicked() {
        mScreensNavigator.toCategoryFragment();
    }

    @Override
    public void onUploadClicked() {
        mScreensNavigator.toUploadDialogFragment();
    }

    @Override
    public void onAccountClicked() {
        mScreensNavigator.toAccountFragment();
    }
}