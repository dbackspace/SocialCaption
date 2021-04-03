package com.xlteam.textonpicture.external.utility.gesture;

import android.view.View;

import org.jetbrains.annotations.NotNull;

public interface OnGestureControl {
    void onDown(@NotNull View currentView);

    void onLongClick();

    void onDoubleClick(@NotNull View currentView);
}
