package com.xlteam.socialcaption.ui.category;

import android.view.View;

import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

public interface CategoryViewMvc extends ObservableViewMvc<CategoryViewMvc.Listener> {

    interface Listener {
        void onSearchViewClicked();

        void onTypeCategoryClicked(View view);

        void onBackClicked();

        void getDataOnFirebase(int categoryNumber, int typeCategory);
    }

    void binCaptions();

    void updateTypeCategory(int type);
}
