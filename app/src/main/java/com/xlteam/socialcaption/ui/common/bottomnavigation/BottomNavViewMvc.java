package com.xlteam.socialcaption.ui.common.bottomnavigation;

import android.widget.FrameLayout;

import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

public interface BottomNavViewMvc extends ObservableViewMvc<BottomNavViewMvc.Listener> {

    interface Listener {
        void onHomePageClicked();

        void onTopsListClicked();

        void onUploadClicked();

        void onAccountClicked();
    }

    FrameLayout getFragmentFrame();
}
