package com.xlteam.socialcaption.external.utility.thread;

import android.os.Looper;

import com.xlteam.socialcaption.external.utility.logger.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private ExecutorService mThreadExecutor;

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public ExecutorService getExecutorService(int threadCount) {
        if (mThreadExecutor == null || mThreadExecutor.isShutdown()) {
            Log.d(this, "getExecutorService() ] Create new FixedThreadPool(" + (threadCount > 0 ? threadCount : 1) + ")");
            mThreadExecutor = Executors.newFixedThreadPool(threadCount > 0 ? threadCount : 1);
        }
        return mThreadExecutor;
    }

    public void shutdownThreadExecutor() {
        try {
            if ((mThreadExecutor != null) && !mThreadExecutor.isShutdown()) {
                mThreadExecutor.shutdownNow();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public boolean canExecute() {
        boolean isShutDown = mThreadExecutor.isShutdown();
        boolean isTerminated = mThreadExecutor.isTerminated();
        if (isShutDown || isTerminated) {
            Log.d(this, "mThreadExecutor is shutdown or terminated. isShutdown :" + isShutDown + ",isTerminated :" + isTerminated);
            return false;
        }
        return true;
    }
}