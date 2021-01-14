package com.xlteam.socialcaption.external.repository;

import android.content.Context;

import com.xlteam.socialcaption.external.database.MyDatabase;

public class AbsRepository {
    private static final String TAG = "AbsRepository";
    protected MyDatabase mDatabase;
    protected ILoader mCallback;
    protected Context mContext;

    public AbsRepository(Context context, ILoader callback) {
        mContext = context;
        mDatabase = MyDatabase.getInstance(context);
        mCallback = callback;
    }
}
