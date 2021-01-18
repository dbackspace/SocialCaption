package com.xlteam.socialcaption.external.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.xlteam.socialcaption.external.datasource.CaptionDataSource;
import com.xlteam.socialcaption.external.utility.SearchQueryUtils;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.ArrayList;
import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_ALL;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_BY_CATEGORY_TYPE;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_BY_CATEGORY_TYPE_AND_SAVED;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.SEARCH_BY_CONTENT;

public class CommonCaptionRepository extends AbsRepository {
    private static final String TAG = "CommonCaptionRepository";
    private Context mContext;

    public CommonCaptionRepository(Context context, ILoader callback) {
        super(context, callback);
        mContext = context;
    }

    public void insertToDatabase() {
        insertMultipleCaption(CaptionDataSource.getInstance().getDataLocal(mContext));
    }

    public void insertMultipleCaption(List<CommonCaption> listCaption) {
        new Thread(() -> {
            for (CommonCaption caption : listCaption) {
                mDatabase.commonCaptionDAO().insertCaption(caption);
            }
        }).start();
    }

    public void insertSingleCaption(CommonCaption caption) {
        new Thread(() -> mDatabase.commonCaptionDAO().insertCaption(caption));
    }

    public void updateCaptionBySaved(long id, boolean saved) {
        new Handler(Looper.getMainLooper()).post(() -> mDatabase.commonCaptionDAO().updateCaptionBySaved(id, saved));
    }


    public void getAllCaption() {
        new Thread(() -> {
            final List<CommonCaption> result = mDatabase.commonCaptionDAO().getAllCaption();
            execute(LOAD_ALL, result);
        }).start();
    }

    public void getAllCaption(boolean isBookmark) {
        if (isBookmark)
            new Thread(() -> {
                final List<CommonCaption> result = mDatabase.commonCaptionDAO().getAllCaptionBySaved(isBookmark);
                execute(LOAD_ALL, result);
            }).start();
        else getAllCaption();
    }

    public void getCaptionByCategoryType(int categoryType) {
        new Thread(() -> {
            final List<CommonCaption> result = mDatabase.commonCaptionDAO().getCaptionByCategoryType(categoryType);
            execute(LOAD_BY_CATEGORY_TYPE, result);
        }).start();
    }

    public void getCaptionBySavedAndCategoryType(int categoryType, boolean isBookmark) {
        if (isBookmark)
            new Thread(() -> {
                final List<CommonCaption> result = mDatabase.commonCaptionDAO().getCaptionBySavedAndCategoryType(categoryType, isBookmark);
                execute(LOAD_BY_CATEGORY_TYPE_AND_SAVED, result);
            }).start();
        else getCaptionByCategoryType(categoryType);
    }

    public void searchCaptionByContainingContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            new Thread(() -> {
                StringBuilder query = SearchQueryUtils.getSelectionArgs(content);
                final List<CommonCaption> result = mDatabase.commonCaptionDAO().searchByContainingContent(new SimpleSQLiteQuery(query.toString()));
                execute(SEARCH_BY_CONTENT, result);
            }).start();
        } else {
            execute(SEARCH_BY_CONTENT, new ArrayList());
        }
    }
}
