package com.xlteam.socialcaption.ui;

import android.app.Application;

import com.xlteam.socialcaption.common.dependencyinjection.CompositionRoot;
import com.xlteam.socialcaption.firebase.FirebaseController;


public class CustomApplication extends Application {
    private CompositionRoot mCompositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
        FirebaseController.updateTopicList(getApplicationContext());
    }

    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }
}
