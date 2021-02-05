package com.xlteam.socialcaption.external.repository;

import android.content.Context;

import com.xlteam.socialcaption.external.utility.thread.ThreadExecutor;
import com.xlteam.socialcaption.model.UserCaption;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.utils.Constant.LoaderTaskType.LOAD_ALL_USER_CAPTION;

public class UserCaptionRepository extends AbsRepository {

    public UserCaptionRepository(Context context, ILoader callback) {
        super(context, callback);
    }

    // save caption user into database
    public void insertUserCaption(UserCaption userCaption) {
        ThreadExecutor.runOnDatabaseThread(() -> {
            mDatabase.userCaptionDAO().insetUserCaption(userCaption);
        });
    }

    public void getAllUserCaption() {
        ThreadExecutor.runOnDatabaseThread(() -> {
            final List<UserCaption> results = mDatabase.userCaptionDAO().getAllUserCaption();
            execute(LOAD_ALL_USER_CAPTION, results);
        });
    }

}
