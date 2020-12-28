package com.xlteam.socialcaption.ui.home;

import android.content.Context;

import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

import java.util.List;

public interface HomePageViewMvc extends ObservableViewMvc<HomePageViewMvc.Listener> {
    interface Listener {
        void onSearchViewClicked();

        void onViewPagerItemClicked();
    }

    void bindCategory(Context context,  int numberCategory, List<Caption> captions);
}
