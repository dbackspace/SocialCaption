package com.xlteam.socialcaption.ui.upload;

import com.xlteam.socialcaption.model.Category;
import com.xlteam.socialcaption.model.User;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

import java.util.List;

public interface DialogUploadCaptionsViewMvc extends ObservableViewMvc<DialogUploadCaptionsViewMvc.Listener> {
    public interface Listener {
        void onPostButtonClicked();

        void onBackButtonClicked();
    }

    void bindUser(User user);

    void bindCategory(List<Category> categories);
    void bindBackgroundForCaption(List<String> stringPaths);
}
