package com.xlteam.socialcaption.common.repository;

import android.content.Context;

import com.xlteam.socialcaption.common.database.MyDatabase;
import com.xlteam.socialcaption.common.datasource.CaptionDataSource;
import com.xlteam.socialcaption.model.Caption;

import java.util.List;

public class CaptionRepository {
    private MyDatabase mDatabase;

    public CaptionRepository(Context context) {
        mDatabase = MyDatabase.getInstance(context);
    }

    public void insertDatabase() {
        new Thread(() -> {
            for (Caption caption : CaptionDataSource.getInstance().getDataFirstTime()) {
                mDatabase.captionDAO().insertCaption(caption);
            }
        }).start();
    }

    public List<Caption> getAllCaption() {
        return mDatabase.captionDAO().getAllCaption();
    }

    public List<Caption> getCaptionByCategoryType(int categoryType) {
        return mDatabase.captionDAO().getCaptionByCategoryType(categoryType);
    }
}
