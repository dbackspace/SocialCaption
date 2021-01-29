package com.xlteam.socialcaption.ui;

import android.app.Application;

import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.logger.LogcatLogWriter;
import com.xlteam.socialcaption.external.utility.thread.ThreadExecutorWithPool;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;


public class MyApplication extends Application implements ILoader<CommonCaption> {

    public MyApplication() {
        super();
        ThreadExecutorWithPool.getInstance().execute(() -> insertDatabase());
        Log.init(new LogcatLogWriter());
    }

    private void insertDatabase() {
        CommonCaptionRepository mRepository = new CommonCaptionRepository(this, this);
        mRepository.insertToDatabase();
    }

    @Override
    public void loadResult(int loaderTaskType, List<CommonCaption> result) {
    }
}
