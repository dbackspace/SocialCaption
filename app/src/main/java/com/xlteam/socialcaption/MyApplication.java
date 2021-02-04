package com.xlteam.socialcaption;

import android.app.Application;

import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.logger.LogcatLogWriter;
import com.xlteam.socialcaption.external.utility.thread.ThreadExecutorWithPool;
import com.xlteam.socialcaption.external.utility.utils.MyCustomLogDebugTree;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

import timber.log.Timber;


public class MyApplication extends Application implements ILoader<CommonCaption> {

    public MyApplication() {
        super();
        ThreadExecutorWithPool.getInstance().execute(() -> insertDatabase());
        Log.init(new LogcatLogWriter());
        if (Timber.treeCount() == 0) {
            if(BuildConfig.DEBUG) Timber.plant(new MyCustomLogDebugTree());
        }
    }

    private void insertDatabase() {
        CommonCaptionRepository mRepository = new CommonCaptionRepository(this, this);
        mRepository.insertToDatabase();
    }

    @Override
    public void loadResult(int loaderTaskType, List<CommonCaption> result) {
    }
}
