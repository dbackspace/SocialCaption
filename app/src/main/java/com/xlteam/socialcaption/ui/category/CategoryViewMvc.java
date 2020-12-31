package com.xlteam.socialcaption.ui.category;

import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

import java.util.List;

public interface CategoryViewMvc extends ObservableViewMvc<CategoryViewMvc.Listener> {

    interface Listener {
        void onSearchViewClicked();

        void onBackClicked();

        void getDataOnFirebase(int categoryNumber, int typeCategory);
    }

    void binCaptions(List<Caption> captions);

    void binNumberCategory(int number);

    void binTypeCategory(int type);
}
