package com.xlteam.socialcaption;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.logger.LogcatLogWriter;
import com.xlteam.socialcaption.external.utility.thread.BitmapLruCache;
import com.xlteam.socialcaption.external.utility.utils.FileUtils;
import com.xlteam.socialcaption.external.utility.utils.MyCustomLogDebugTree;
import com.xlteam.socialcaption.external.utility.utils.Utility;

import java.io.File;
import java.util.List;

import timber.log.Timber;


public class MyApplication extends Application {

    public MyApplication() {
        super();
        Log.init(new LogcatLogWriter());
        if (Timber.treeCount() == 0) {
            if(BuildConfig.DEBUG) Timber.plant(new MyCustomLogDebugTree());
        }
    }
}
