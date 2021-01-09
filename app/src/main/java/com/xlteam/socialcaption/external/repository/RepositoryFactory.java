package com.xlteam.socialcaption.external.repository;

import android.content.Context;

import static com.xlteam.socialcaption.external.utility.Constant.RepositoryType.COMMON_REPOSITORY;
import static com.xlteam.socialcaption.external.utility.Constant.RepositoryType.USER_REPOSITORY;

public class RepositoryFactory {
    private static final String TAG = "RepositoryFactory";

    public static AbsRepository createRepository(Context context, ILoader callback, int repositoryType) {
        if (repositoryType == COMMON_REPOSITORY)
            return new CommonCaptionRepository(context, callback);
        else if (repositoryType == USER_REPOSITORY)
            return new UserCaptionRepository(context, callback);
        else
            return null;
    }
}
