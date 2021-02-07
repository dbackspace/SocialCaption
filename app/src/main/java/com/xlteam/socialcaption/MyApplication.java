package com.xlteam.socialcaption;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.logger.LogcatLogWriter;
import com.xlteam.socialcaption.external.utility.thread.BitmapLruCache;
import com.xlteam.socialcaption.external.utility.thread.ThreadExecutor;
import com.xlteam.socialcaption.external.utility.thread.ThreadExecutorWithPool;
import com.xlteam.socialcaption.external.utility.utils.MyCustomLogDebugTree;
import com.xlteam.socialcaption.external.utility.utils.PrefUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.model.CommonCaption;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class MyApplication extends Application implements ILoader<CommonCaption> {

    public MyApplication() {
        super();
        ThreadExecutorWithPool.getInstance().execute(() -> insertDatabase());
        ThreadExecutor.runOnMainThread(() -> saveImageToLruCache());
        Log.init(new LogcatLogWriter());
        if (Timber.treeCount() == 0) {
            if(BuildConfig.DEBUG) Timber.plant(new MyCustomLogDebugTree());
        }
    }

    private void saveImageToLruCache() {
        List<String> pathImage = PrefUtils.getListItemGallery(this);
        if (!Utility.isEmpty(pathImage)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                BitmapLruCache cacheBitmapLru = BitmapLruCache.getInstance();
                for (String path : pathImage) {
                    File imgFile = new File(path);
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    cacheBitmapLru.saveBitmapToCache(path, bitmap);
                }
            }
        }
    }

    private void insertDatabase() {
        CommonCaptionRepository mRepository = new CommonCaptionRepository(this, this);
        mRepository.insertToDatabase();
    }
}
