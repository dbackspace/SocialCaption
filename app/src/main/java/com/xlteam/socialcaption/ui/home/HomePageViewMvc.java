package com.xlteam.socialcaption.ui.home;

import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

import java.util.List;

public interface HomePageViewMvc extends ObservableViewMvc<HomePageViewMvc.Listener> {
    interface Listener {
        void onSearchViewClicked();

        void onViewPagerItemClicked();
    }

    void bindCategories(List<ItemCategory> listCategory);
}
