package com.xlteam.socialcaption.ui.home.itemcategory;

import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

public interface ItemCategoryViewMvc extends ObservableViewMvc<ItemCategoryViewMvc.Listener> {

    interface Listener {
        void onTvShowMoreClicked(String categoryId);
    }

    void bindItemCategory(ItemCategory itemCategory);
}
