package com.xlteam.socialcaption.ui.common.views;

public interface ObservableViewMvc<ListenerType> extends ViewMvc {
    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}
