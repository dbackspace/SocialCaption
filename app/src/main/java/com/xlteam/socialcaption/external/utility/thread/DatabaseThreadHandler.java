package com.xlteam.socialcaption.external.utility.thread;

import android.os.Handler;
import android.os.HandlerThread;

import com.xlteam.socialcaption.external.utility.logger.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class DatabaseThreadHandler {

    private static final String TAG = "DatabaseThreadHandler";
    private static final int TIME_OUT = 5000;
    private final HandlerThread mDatabaseThread;
    private final Handler mDatabaseHandler;

    private static class InstanceHolder {
        private static final DatabaseThreadHandler INSTANCE = new DatabaseThreadHandler();
    }

    private DatabaseThreadHandler() {
        mDatabaseThread = new HandlerThread("database-thread");
        mDatabaseThread.start();
        mDatabaseHandler = new Handler(mDatabaseThread.getLooper());
    }

    static DatabaseThreadHandler getInstance() {
        return InstanceHolder.INSTANCE;
    }

    void run(Runnable r) {
        if (ThreadManager.isMainThread()) {
            mDatabaseHandler.post(r);
        } else {
            r.run();
        }
    }

    <T> T run(Callable<T> callable) {
        return run(callable, null);
    }

    <T> T run(Callable<T> callable, T defaultValue) {
        long startTime = System.currentTimeMillis();
        T ret = defaultValue;
        Exception exception = null;
        try {
            if (ThreadManager.isMainThread()) {
                FutureTask<T> futureTask = new FutureTask<>(callable);
                mDatabaseHandler.post(futureTask);
                ret = futureTask.get(TIME_OUT, TimeUnit.MILLISECONDS);
            } else {
                ret = callable.call();
            }
        } catch (Exception e) {
            exception = e;
        } finally {
            long executeTime = (System.currentTimeMillis() - startTime);
            if (executeTime >= TIME_OUT || exception != null) {
                Log.w(TAG, "Execution Time = " + executeTime + "Thread Info : id = "
                        + Thread.currentThread().getId() + " name = " + Thread.currentThread().getName());
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        }
        return ret;
    }
}
