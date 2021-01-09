package com.xlteam.socialcaption.ui;

import android.app.Application;

import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;


public class MyApplication extends Application implements ILoader<CommonCaption> {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonCaptionRepository   mRepository = new CommonCaptionRepository(this, this);
        mRepository.insertFirstTimeToDatabase();
    }

    @Override
    public void loadResult(int loaderTaskType, List<CommonCaption> result) {
    }
}
