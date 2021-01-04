package com.xlteam.socialcaption.ui.home;

import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

import java.util.List;

public interface HomePageViewMvc extends ObservableViewMvc<HomePageViewMvc.Listener> {

    interface Listener {
        void onSearchViewClicked();

        void onBackClicked();

        void getDataOnFirebase(int categoryNumber, int typeCategory);
    }

    void binCaptions(List<Caption> captions);

    void binNumberCategory(int number);

    void binTypeCategory(int type);
}
