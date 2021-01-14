package com.xlteam.socialcaption.external.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.xlteam.socialcaption.model.UserCaption;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_ALL_USER_CAPTION;

public class UserCaptionRepository extends AbsRepository {
    private static final String TAG = "UserCaptionRepository";

    public UserCaptionRepository(Context context, ILoader callback) {
        super(context, callback);
    }

    // save caption user into database
    public void insertUserCaption(UserCaption userCaption) {
        new Thread(() -> {
            mDatabase.userCaptionDAO().insetUserCaption(userCaption);
        }).start();
    }

    public void getAllUserCaption() {
        new Thread(() -> {
            final List<UserCaption> results = mDatabase.userCaptionDAO().getAllUserCaption();
            execute(LOAD_ALL_USER_CAPTION, results);
        }).start();
    }

}
