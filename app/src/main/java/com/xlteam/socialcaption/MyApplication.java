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
        saveImageToLruCache();
        Log.init(new LogcatLogWriter());
        if (Timber.treeCount() == 0) {
            if(BuildConfig.DEBUG) Timber.plant(new MyCustomLogDebugTree());
        }
    }

    // TODO: thiếu cấp quyền Storage ngay từ đầu nên sẽ Force close
    private void saveImageToLruCache() {
        List<File> fileList = FileUtils.getListFilesIfFolderExist();
        if (!Utility.isEmpty(fileList)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                BitmapLruCache cacheBitmapLru = BitmapLruCache.getInstance();
                for (File file : fileList) {
                    String path = file.getAbsolutePath();
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    cacheBitmapLru.saveBitmapToCache(path, bitmap);
                }
            }
        }
    }
}
