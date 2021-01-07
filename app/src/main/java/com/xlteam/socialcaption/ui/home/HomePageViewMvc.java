package com.xlteam.socialcaption.ui.home;

import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

import java.util.List;

public interface HomePageViewMvc extends ObservableViewMvc<HomePageViewMvc.Listener> {

    interface Listener {
        void getCaptionList(int categoryNumber, boolean isBookmark);

        void searchClicked();

        void createCaptionClicked();
    }

    void binCaptions(List<Caption> captions);
}
