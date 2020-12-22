package com.xlteam.socialcaption.ui.common.screensnavigator;

import com.techyourchance.fragmenthelper.FragmentHelper;
import com.xlteam.socialcaption.ui.account.AccountFragment;
import com.xlteam.socialcaption.ui.toplist.TopListFragment;
import com.xlteam.socialcaption.ui.home.HomePageFragment;

public class ScreensNavigator {
    private final FragmentHelper mFragmentHelper;

    public ScreensNavigator(FragmentHelper fragmentHelper) {
        mFragmentHelper = fragmentHelper;
    }

    public void toHomePageScreen() {
        mFragmentHelper.replaceFragmentAndClearHistory(HomePageFragment.newInstance());
    }

    public void toTopListFragment() {
        mFragmentHelper.replaceFragment(TopListFragment.newInstance());
    }

    public void toUploadDialogFragment() {

    }

    public void toAccountFragment() {
        mFragmentHelper.replaceFragment(AccountFragment.newInstance());
    }
}
