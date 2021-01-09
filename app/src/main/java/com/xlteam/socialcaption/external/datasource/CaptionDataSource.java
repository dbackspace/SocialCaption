package com.xlteam.socialcaption.external.datasource;

import com.xlteam.socialcaption.model.CommonCaption;

import java.util.ArrayList;
import java.util.List;

public class CaptionDataSource {
    private static final String TAG = "CaptionDataSource";
    private CaptionDataSource() {
    }

    private static class Holder {
        public static CaptionDataSource INSTANCE = new CaptionDataSource();
    }

    public static CaptionDataSource getInstance() {
        return Holder.INSTANCE;
    }

    public List<CommonCaption> getDataFirstTime() {
        List<CommonCaption> listData = new ArrayList<>();
        listData.add(new CommonCaption("huỳnh đức vũ", "pathImage1", 1, false));
        listData.add(new CommonCaption("mình ước được như anh ấy", "pathImage1", 1, false));
        listData.add(new CommonCaption("content1", "pathImage1", 2, false));
        listData.add(new CommonCaption("content1", "pathImage1", 3, false));
        listData.add(new CommonCaption("content1", "pathImage1", 4, false));
        listData.add(new CommonCaption("content1", "pathImage1", 2, false));
        listData.add(new CommonCaption("content1", "pathImage1", 5, false));
        listData.add(new CommonCaption("content1", "pathImage1", 3, false));
        return listData;
    }
}
