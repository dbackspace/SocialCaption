package com.xlteam.socialcaption;

import android.app.Application;

import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.logger.LogcatLogWriter;
import com.xlteam.socialcaption.external.utility.utils.MyCustomLogDebugTree;

import timber.log.Timber;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.init(new LogcatLogWriter());
        if (Timber.treeCount() == 0) {
            if (BuildConfig.DEBUG) Timber.plant(new MyCustomLogDebugTree());
        }
    }
}
