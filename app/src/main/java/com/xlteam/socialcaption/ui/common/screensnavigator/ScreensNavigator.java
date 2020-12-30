package com.xlteam.socialcaption.ui.common.screensnavigator;

import android.content.Context;

import com.techyourchance.dialoghelper.DialogHelper;
import com.techyourchance.fragmenthelper.FragmentHelper;
import com.xlteam.socialcaption.ui.account.AccountFragment;
import com.xlteam.socialcaption.ui.toplist.TopListFragment;
import com.xlteam.socialcaption.ui.home.HomePageFragment;
import com.xlteam.socialcaption.ui.upload.DialogUploadCaptions;

public class ScreensNavigator {
    private final FragmentHelper mFragmentHelper;
    private final DialogHelper mDialogHelper;

    public ScreensNavigator(FragmentHelper fragmentHelper, DialogHelper dialogHelper) {
        mFragmentHelper = fragmentHelper;
        mDialogHelper = dialogHelper;
    }

    public void toHomePageScreen() {
        mFragmentHelper.replaceFragmentAndClearHistory(HomePageFragment.newInstance());
    }

    public void toTopListFragment() {
        mFragmentHelper.replaceFragment(TopListFragment.newInstance());
    }

    public void toUploadDialogFragment() {
        mDialogHelper.showDialog(DialogUploadCaptions.newInstance(), null);
    }

    public void toAccountFragment() {
        mFragmentHelper.replaceFragment(AccountFragment.newInstance());
    }
}
