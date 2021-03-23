package com.xlteam.textonpicture.external.utility.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public final class AsyncLayoutInflateThread extends HandlerThread implements Handler.Callback {
    private static final String TAG = "HandlerThread";
    private static AsyncLayoutInflateThread sInstance;

    private Handler mHandler;

    public static synchronized AsyncLayoutInflateThread getInstance() {
        if (sInstance == null) {
            sInstance = new AsyncLayoutInflateThread(TAG);
        }
        return sInstance;
    }

    private AsyncLayoutInflateThread(String name) {
        super(name);
        start();
        mHandler = new Handler(getLooper(), this);
    }

    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return true;
    }

    @Override
    public boolean quit() {
        mHandler.removeCallbacksAndMessages(null);
        sInstance = null;
        return super.quit();
    }
}
