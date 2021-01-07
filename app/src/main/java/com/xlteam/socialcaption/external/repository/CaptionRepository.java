package com.xlteam.socialcaption.external.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.xlteam.socialcaption.external.database.MyDatabase;
import com.xlteam.socialcaption.external.datasource.CaptionDataSource;
import com.xlteam.socialcaption.model.Caption;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_ALL;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_BY_CATEGORY_TYPE;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_BY_CATEGORY_TYPE_AND_SAVED;
import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.SEARCH_BY_CONTENT;

public class CaptionRepository {
    private MyDatabase mDatabase;
    private ICaptionRepository mCallback;

    public CaptionRepository(Context context, ICaptionRepository callback) {
        mDatabase = MyDatabase.getInstance(context);
        mCallback = callback;
    }

    public void insertFirstTimeToDatabase() {
        // TODO: chỉnh lại data lần đầu nạp vào app
        insertMultipleCaption(CaptionDataSource.getInstance().getDataFirstTime());
    }

    public void insertMultipleCaption(List<Caption> listCaption) {
        new Thread(() -> {
            for (Caption caption : listCaption) {
                mDatabase.captionDAO().insertCaption(caption);
            }
        }).start();
    }

    public void insertSingleCaption(Caption caption) {
        new Thread(() -> mDatabase.captionDAO().insertCaption(caption));
    }

    public void execute(int loaderTaskType, List<Caption> result) {
        if (result != null) {
            new Handler(Looper.getMainLooper()).post(() -> mCallback.loadResult(loaderTaskType, result));
        }
    }

    public void getAllCaption() {
        new Thread(() -> {
            final List<Caption> result = mDatabase.captionDAO().getAllCaption();
            execute(LOAD_ALL, result);
        }).start();
    }

    public void getCaptionByCategoryType(int categoryType) {
        new Thread(() -> {
            final List<Caption> result = mDatabase.captionDAO().getCaptionByCategoryType(categoryType);
            execute(LOAD_BY_CATEGORY_TYPE, result);
        }).start();
    }

    public void getCaptionBySavedAndCategoryType(int categoryType, boolean saved) {
        new Thread(() -> {
            final List<Caption> result = mDatabase.captionDAO().getCaptionBySavedAndCategoryType(categoryType, saved);
            execute(LOAD_BY_CATEGORY_TYPE_AND_SAVED, result);
        }).start();
    }

    public void searchCaptionByContainingContent(String content) {
        new Thread(() -> {
            final List<Caption> result = mDatabase.captionDAO().searchByContainingContent(content);
            execute(SEARCH_BY_CONTENT, result);
        }).start();
    }
}
