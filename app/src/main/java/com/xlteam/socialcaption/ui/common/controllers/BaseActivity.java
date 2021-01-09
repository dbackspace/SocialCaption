package com.xlteam.socialcaption.ui.common.controllers;

import androidx.appcompat.app.AppCompatActivity;

import com.xlteam.socialcaption.common.dependencyinjection.ActivityCompositionRoot;
import com.xlteam.socialcaption.common.dependencyinjection.ControllerCompositionRoot;
import com.xlteam.socialcaption.ui.MyApplication;

public class BaseActivity extends AppCompatActivity {
    private ActivityCompositionRoot mActivityCompositionRoot;
    private ControllerCompositionRoot mControllerCompositionRoot;

    public ActivityCompositionRoot getActivityCompositionRoot() {
        if (mActivityCompositionRoot == null) {
            mActivityCompositionRoot = new ActivityCompositionRoot(
                    ((MyApplication) getApplication()).getCompositionRoot(),
                    this
            );
        }
        return mActivityCompositionRoot;
    }

    protected ControllerCompositionRoot getControllerCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(getActivityCompositionRoot());
        }
        return mControllerCompositionRoot;
    }
}
