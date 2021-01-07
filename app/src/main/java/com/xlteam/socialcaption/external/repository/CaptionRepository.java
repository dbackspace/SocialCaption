package com.xlteam.socialcaption.external.repository;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;

import com.xlteam.socialcaption.external.database.MyDatabase;
import com.xlteam.socialcaption.external.datasource.CaptionDataSource;
import com.xlteam.socialcaption.model.Caption;

import java.util.List;

public class CaptionRepository {
    private MyDatabase mDatabase;
    private ICaptionRepository mCallback;

    public CaptionRepository(Context context, ICaptionRepository callback) {
        mDatabase = MyDatabase.getInstance(context);
        mCallback = callback;
    }

    public void insertDatabase() {
        new Thread(() -> {
            for (Caption caption : CaptionDataSource.getInstance().getDataFirstTime()) {
                mDatabase.captionDAO().insertCaption(caption);
            }
        }).start();
    }

    public void insertItem(Caption caption) {
        new Thread(() -> mDatabase.captionDAO().insertCaption(caption));
    }

    public void getAllCaption() {
        new Thread(() -> {
            final List<Caption> result = mDatabase.captionDAO().getAllCaption();
            if (result != null) {
                new Handler(Looper.getMainLooper()).post(() -> mCallback.loadResult(result));
            }
        }).start();
    }

    public List<Caption> getCaptionByCategoryType(int categoryType) {
        return mDatabase.captionDAO().getCaptionByCategoryType(categoryType);
    }
}
