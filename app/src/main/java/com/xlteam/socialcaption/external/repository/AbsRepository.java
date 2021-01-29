package com.xlteam.socialcaption.external.repository;

import android.content.Context;

import com.xlteam.socialcaption.external.database.MyDatabase;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.thread.ThreadExecutor;

import java.util.List;

public class AbsRepository<CaptionType> {
    private static final String TAG = "AbsRepository";
    protected MyDatabase mDatabase;
    protected ILoader mCallback;
    protected Context mContext;

    public AbsRepository(Context context, ILoader callback) {
        mContext = context;
        mDatabase = MyDatabase.getInstance(context);
        mCallback = callback;
    }

    protected void execute(int loaderTaskType, List<CaptionType> result) {
        if (result != null) {
            ThreadExecutor.runOnMainThread(() -> mCallback.loadResult(loaderTaskType, result));
            Log.d(TAG, "execute done: type = " + loaderTaskType);
        }
    }
}
