package com.xlteam.socialcaption.model;

import java.util.List;

public class ItemCategory {
    private final String headerName;
    private final List<Caption> mCaptions;

    public ItemCategory(String headerName, List<Caption> captions) {
        this.headerName = headerName;
        mCaptions = captions;
    }

    public List<Caption> getCaptions() {
        return mCaptions;
    }

    public String getHeaderName() {
        return headerName;
    }
}
