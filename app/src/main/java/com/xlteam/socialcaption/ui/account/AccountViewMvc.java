package com.xlteam.socialcaption.ui.account;

import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

public interface AccountViewMvc extends ObservableViewMvc<AccountViewMvc.Listener> {
    interface Listener {
        void onProfileClicked();

        void onPostedClicked();

        void onMarkedPostClicked();

        void onSettingClicked();

        void onLogOutClicked();
    }

    void bindProfile();
}
