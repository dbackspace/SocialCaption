package com.xlteam.socialcaption.model;

import java.util.List;

public class ItemCategory {
    private final Category category;
    private final List<Caption> mCaptions;

    public ItemCategory(Category category, List<Caption> captions) {
        this.category = category;
        mCaptions = captions;
    }

    public List<Caption> getCaptions() {
        return mCaptions;
    }

    public Category getCategory() {
        return category;
    }
}
