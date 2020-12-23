package com.xlteam.socialcaption.ui.home.aitemcategory.acaptionofcategory;

import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.views.ObservableViewMvc;

public interface ItemCaptionViewMvc extends ObservableViewMvc<ItemCaptionViewMvc.Listener> {
    interface Listener {
        void onCaptionClicked(Caption caption);
    }

    void bindCaption(Caption caption);
}
