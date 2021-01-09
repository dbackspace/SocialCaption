package com.xlteam.socialcaption.external.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.xlteam.socialcaption.external.datasource.CaptionDataSource;
import com.xlteam.socialcaption.external.utility.SearchQueryUtils;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_ALL;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_BY_CATEGORY_TYPE;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_BY_CATEGORY_TYPE_AND_SAVED;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.SEARCH_BY_CONTENT;

public class CommonCaptionRepository extends AbsRepository{
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
        }
    }

    public void getAllCaption() {
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

    public void searchCaptionByContainingContent(String content) {
        new Thread(() -> {
            String selectionArgs = SearchQueryUtils.getSelectionArgs(content.trim());
            final List<CommonCaption> result = mDatabase.commonCaptionDAO().searchByContainingContent(selectionArgs);
            execute(SEARCH_BY_CONTENT, result);
        }).start();
    }
}
