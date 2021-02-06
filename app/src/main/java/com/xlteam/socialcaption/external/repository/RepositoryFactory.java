package com.xlteam.socialcaption.external.repository;

import android.content.Context;

import static com.xlteam.socialcaption.external.utility.utils.Constant.RepositoryType.COMMON_REPOSITORY;
import static com.xlteam.socialcaption.external.utility.utils.Constant.RepositoryType.USER_REPOSITORY;

public class RepositoryFactory {
    public static AbsRepository createRepository(Context context, ILoader callback, int repositoryType) {
        if (repositoryType == COMMON_REPOSITORY)
            return new CommonCaptionRepository(context, callback);
        else if (repositoryType == USER_REPOSITORY)
            return new UserCaptionRepository(context, callback);
        throw new IllegalArgumentException("No repository was created - repositoryType = " + repositoryType);
    }
}
