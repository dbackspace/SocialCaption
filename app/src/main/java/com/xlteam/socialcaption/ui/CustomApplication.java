package com.xlteam.socialcaption.ui;

import android.app.Application;

import com.xlteam.socialcaption.common.dependencyinjection.CompositionRoot;
import com.xlteam.socialcaption.external.repository.CaptionRepository;
import com.xlteam.socialcaption.external.repository.ICaptionRepository;
import com.xlteam.socialcaption.model.Caption;

import java.util.List;


public class CustomApplication extends Application implements ICaptionRepository {
    private CompositionRoot mCompositionRoot;
    private CaptionRepository mRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
        mRepository = new CaptionRepository(this, this);
        mRepository.insertDatabase();
    }

    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }

    @Override
    public void loadResult(List<Caption> result) {

    }
}
