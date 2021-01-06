package com.xlteam.socialcaption.common.datasource;

import com.xlteam.socialcaption.model.Caption;

import java.util.ArrayList;
import java.util.List;

public class CaptionDataSource {
    private CaptionDataSource() {
    }

    private static class Holder {
        public static CaptionDataSource INSTANCE = new CaptionDataSource();
    }

    public static CaptionDataSource getInstance() {
        return Holder.INSTANCE;
    }

    public List<Caption> getDataFirstTime() {
        List<Caption> listData = new ArrayList<>();
        listData.add(new Caption("content1", "pathImage1", 1, false));
        listData.add(new Caption("content1", "pathImage1", 2, false));
        listData.add(new Caption("content1", "pathImage1", 3, false));
        listData.add(new Caption("content1", "pathImage1", 4, false));
        listData.add(new Caption("content1", "pathImage1", 2, false));
        listData.add(new Caption("content1", "pathImage1", 5, false));
        listData.add(new Caption("content1", "pathImage1", 3, false));
        return listData;
    }
}
