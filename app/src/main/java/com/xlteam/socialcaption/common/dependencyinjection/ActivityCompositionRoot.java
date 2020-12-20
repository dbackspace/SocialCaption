package com.xlteam.socialcaption.common.dependencyinjection;

import androidx.fragment.app.FragmentActivity;

public class ActivityCompositionRoot {
    private final CompositionRoot mCompostionRoot;
    private final FragmentActivity mActivity;

    public ActivityCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        mCompostionRoot = compositionRoot;
        mActivity = activity;
    }

    FragmentActivity getActivity() {
        return mActivity;
    }
}
