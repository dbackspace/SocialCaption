package com.xlteam.socialcaption.common.dependencyinjection;

import androidx.fragment.app.FragmentActivity;

import com.techyourchance.dialoghelper.DialogHelper;
import com.techyourchance.fragmenthelper.FragmentContainerWrapper;
import com.techyourchance.fragmenthelper.FragmentHelper;

public class ActivityCompositionRoot {
    private final CompositionRoot mCompositionRoot;
    private final FragmentActivity mActivity;

    public ActivityCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    FragmentActivity getActivity() {
        return mActivity;
    }

    private FragmentHelper getFragmentHelper() {
        return new FragmentHelper(mActivity, getFragmentContainerWrapper(), mActivity.getSupportFragmentManager());
    }

    private FragmentContainerWrapper getFragmentContainerWrapper() {
        return (FragmentContainerWrapper) mActivity;
    }

    protected DialogHelper getDialogHelper() {
        return new DialogHelper(getActivity().getSupportFragmentManager());
    }
}
