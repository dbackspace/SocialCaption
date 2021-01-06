package com.xlteam.socialcaption.ui;

import android.app.Application;

import com.xlteam.socialcaption.common.database.MyDatabase;
import com.xlteam.socialcaption.common.dependencyinjection.CompositionRoot;
import com.xlteam.socialcaption.common.repository.CaptionRepository;


public class CustomApplication extends Application {
    private CompositionRoot mCompositionRoot;
    private CaptionRepository mRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
        mRepository = new CaptionRepository(this);
        mRepository.insertDatabase();
    }

    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }
}
