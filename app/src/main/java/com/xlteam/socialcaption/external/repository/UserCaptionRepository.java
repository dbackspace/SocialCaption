package com.xlteam.socialcaption.external.repository;

import android.content.Context;

public class UserCaptionRepository extends AbsRepository{
    private static final String TAG = "UserCaptionRepository";

    public UserCaptionRepository(Context context, ILoader callback) {
        super(context, callback);
    }
}
