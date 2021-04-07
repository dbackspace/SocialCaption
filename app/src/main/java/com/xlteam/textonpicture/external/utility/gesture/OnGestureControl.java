package com.xlteam.textonpicture.external.utility.gesture;

import com.xlteam.textonpicture.external.utility.customview.ClipArt;

import org.jetbrains.annotations.NotNull;

public interface OnGestureControl {
    void onDown(@NotNull ClipArt currentView);

    void onLongClick();

    void onDoubleClick(@NotNull ClipArt currentView);
}
