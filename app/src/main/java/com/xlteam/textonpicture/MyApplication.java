package com.xlteam.textonpicture;

import android.app.Application;
import android.os.Environment;

import com.xlteam.textonpicture.external.utility.logger.Log;
import com.xlteam.textonpicture.external.utility.logger.LogcatLogWriter;
import com.xlteam.textonpicture.external.utility.utils.FileUtils;
import com.xlteam.textonpicture.external.utility.utils.MyCustomLogDebugTree;

import timber.log.Timber;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initAppDataFolder();
        Log.init(new LogcatLogWriter());
        if (Timber.treeCount() == 0) {
            if (BuildConfig.DEBUG) Timber.plant(new MyCustomLogDebugTree());
        }
    }

    private void initAppDataFolder() {
        FileUtils.StoragePathUtils.setRootPath(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
    }
}
