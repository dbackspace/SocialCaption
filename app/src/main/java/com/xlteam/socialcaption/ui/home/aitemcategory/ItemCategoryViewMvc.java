package com.xlteam.socialcaption.ui.home.aitemcategory;

import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

import java.util.List;

interface ItemCategoryViewMvc extends ObservableViewMvc<ItemCategoryViewMvc.Listener> {

    public interface Listener {
        void onTvShowMoreClicked();
    }

    void bindItemCategory(List<ItemCategory> listItemCategory);
}
