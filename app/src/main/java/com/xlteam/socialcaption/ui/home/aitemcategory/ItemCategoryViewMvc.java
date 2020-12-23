package com.xlteam.socialcaption.ui.home.aitemcategory;

import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

import java.util.List;

public interface ItemCategoryViewMvc extends ObservableViewMvc<ItemCategoryViewMvc.Listener> {

    public interface Listener {
        void onTvShowMoreClicked(String categoryId);
    }

    void bindItemCategory(ItemCategory itemCategory);
}
