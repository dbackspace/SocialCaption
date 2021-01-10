package com.xlteam.socialcaption.external.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.xlteam.socialcaption.external.datasource.CaptionDataSource;
import com.xlteam.socialcaption.external.utility.SearchQueryUtils;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

import androidx.sqlite.db.SimpleSQLiteQuery;

import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_ALL;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_BY_CATEGORY_TYPE;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_BY_CATEGORY_TYPE_AND_SAVED;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.SEARCH_BY_CONTENT;

public class CommonCaptionRepository extends AbsRepository {
    private static final String TAG = "CommonCaptionRepository";


    public CommonCaptionRepository(Context context, ILoader callback) {
        super(context, callback);
    }

    public void insertFirstTimeToDatabase() {
        // TODO: chỉnh lại data lần đầu nạp vào app
        insertMultipleCaption(CaptionDataSource.getInstance().getDataFirstTime());
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

    public void updateCaption(CommonCaption newCaption) {
        new Thread(() -> mDatabase.commonCaptionDAO().updateCaption(newCaption));
    }

    public void execute(int loaderTaskType, List<CommonCaption> result) {
        if (result != null) {
            new Handler(Looper.getMainLooper()).post(() -> mCallback.loadResult(loaderTaskType, result));
            Log.d(TAG, "execute done: type = " + loaderTaskType);
        }
    }

    public void getAllCaption(boolean isBookmark) {
        new Thread(() -> {
            final List<CommonCaption> result = mDatabase.commonCaptionDAO().getAllCaption();
            execute(LOAD_ALL, result);
        }).start();
    }

    public void getCaptionByCategoryType(int categoryType) {
        new Thread(() -> {
            final List<CommonCaption> result = mDatabase.commonCaptionDAO().getCaptionByCategoryType(categoryType);
            execute(LOAD_BY_CATEGORY_TYPE, result);
        }).start();
    }

    public void getCaptionBySavedAndCategoryType(int categoryType, boolean saved) {
        new Thread(() -> {
            final List<CommonCaption> result = mDatabase.commonCaptionDAO().getCaptionBySavedAndCategoryType(categoryType, saved);
            execute(LOAD_BY_CATEGORY_TYPE_AND_SAVED, result);
        }).start();
    }

//    public void searchCaptionByContainingsContent(String content) {
//        new Thread(() -> {
//            String selectionArgs = SearchQueryUtils.getSelectionArgs(content.trim());
//            final List<CommonCaption> result = mDatabase.commonCaptionDAO().searchByContainingContent(selectionArgs);
//            execute(SEARCH_BY_CONTENT, result);
//        }).start();
//    }

    public void searchCaptionByContainingContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            new Thread(() -> {
                StringBuilder query = new StringBuilder("select * from common_caption_table where ");
                String[] selectionArgs = SearchQueryUtils.getSelectionArgs(content);
                if (selectionArgs.length > 1) {
                    for (int i = 0; i < selectionArgs.length; ++i) {
                        query.append("(_content like ").append(selectionArgs[i]).append(")");
                        if (i < selectionArgs.length - 1) {
                            query.append(" and ");
                        }
                    }
                } else {
                    query.append("_content like ").append("").append(selectionArgs[0]).append(" ");
                }
                final List<CommonCaption> result = mDatabase.commonCaptionDAO().searchByContainingContent(new SimpleSQLiteQuery(query.toString()));
                execute(SEARCH_BY_CONTENT, result);
            }).start();
        }
    }
}
