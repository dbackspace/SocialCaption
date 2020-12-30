package com.xlteam.socialcaption.model;

import java.util.List;

public class ItemCategory {
    private final String category;
    private final List<Caption> mCaptions;

    public ItemCategory(String category, List<Caption> captions) {
        this.category = category;
        mCaptions = captions;
    }

    public List<Caption> getCaptions() {
        return mCaptions;
    }

    public String getCategory() {
        return category;
    }
}
