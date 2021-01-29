package com.xlteam.socialcaption.external.repository;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.xlteam.socialcaption.external.datasource.CaptionDataSource;
import com.xlteam.socialcaption.external.utility.SearchQueryUtils;
import com.xlteam.socialcaption.external.utility.thread.ThreadExecutor;
import com.xlteam.socialcaption.model.CommonCaption;
import com.xlteam.socialcaption.model.CommonCaptionComparator;

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
        ThreadExecutor.runOnDatabaseThread(() -> {
            for (CommonCaption caption : listCaption) {
                mDatabase.commonCaptionDAO().insertCaption(caption);
            }
        });
    }

    public void insertSingleCaption(CommonCaption caption) {
        ThreadExecutor.runOnDatabaseThread(() -> mDatabase.commonCaptionDAO().insertCaption(caption));
    }

    public void updateCaptionBySaved(long id, boolean saved) {
        ThreadExecutor.runOnDatabaseThread(() -> mDatabase.commonCaptionDAO().updateCaptionBySaved(id, saved));
    }


    public void getAllCaption() {
        ThreadExecutor.runOnDatabaseThread(() -> {
            final List<CommonCaption> result = mDatabase.commonCaptionDAO().getAllCaption();
            sortListCaption(result);
            execute(LOAD_ALL, result);
        });
    }

    public void getAllCaption(boolean isBookmark) {
        if (isBookmark)
            ThreadExecutor.runOnDatabaseThread(() -> {
                final List<CommonCaption> result = mDatabase.commonCaptionDAO().getAllCaptionBySaved(isBookmark);
                sortListCaption(result);
                execute(LOAD_ALL, result);
            });
        else getAllCaption();
    }

    public void getCaptionByCategoryType(int categoryType) {
        ThreadExecutor.runOnDatabaseThread(() -> {
            final List<CommonCaption> result = mDatabase.commonCaptionDAO().getCaptionByCategoryType(categoryType);
            sortListCaption(result);
            execute(LOAD_BY_CATEGORY_TYPE, result);
        });
    }

    public void getCaptionBySavedAndCategoryType(int categoryType, boolean isBookmark) {
        if (isBookmark)
            ThreadExecutor.runOnDatabaseThread(() -> {
                final List<CommonCaption> result = mDatabase.commonCaptionDAO().getCaptionBySavedAndCategoryType(categoryType, isBookmark);
                sortListCaption(result);
                execute(LOAD_BY_CATEGORY_TYPE_AND_SAVED, result);
            });
        else getCaptionByCategoryType(categoryType);
    }

    public void searchCaptionByContainingContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            StringBuilder query = SearchQueryUtils.getSelectionArgs(content);
            ThreadExecutor.runOnDatabaseThread(() -> {
                final List<CommonCaption> result = mDatabase.commonCaptionDAO().searchByContainingContent(new SimpleSQLiteQuery(query.toString()));
                sortListCaption(result);
                execute(SEARCH_BY_CONTENT, result);
            });
        } else {
            execute(SEARCH_BY_CONTENT, new ArrayList());
        }
    }

    private void sortListCaption(final List<CommonCaption> result) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result.sort(new CommonCaptionComparator());
        }
    }
}
