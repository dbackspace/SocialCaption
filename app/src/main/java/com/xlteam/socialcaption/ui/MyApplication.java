package com.xlteam.socialcaption.ui;

import android.app.Application;

import com.xlteam.socialcaption.common.dependencyinjection.CompositionRoot;
import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.IRepository;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;


public class MyApplication extends Application implements IRepository {
    private static final String TAG = "MyApplication";

    private CompositionRoot mCompositionRoot;
    private CommonCaptionRepository mRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
        mRepository = new CommonCaptionRepository(this, this);
        mRepository.insertFirstTimeToDatabase();
    }

    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }

    @Override
    public void loadResult(int loaderTaskType, List<CommonCaption> result) {
    }
}
