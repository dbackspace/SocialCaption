package com.xlteam.socialcaption.ui.category;

import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

public interface CategoryViewMvc extends ObservableViewMvc<CategoryViewMvc.Listener> {

    interface Listener {
        void onSearchViewClicked();

        void onTypeCategoryClicked();

        void onBackClicked();
    }

    void binCaptions();
}
