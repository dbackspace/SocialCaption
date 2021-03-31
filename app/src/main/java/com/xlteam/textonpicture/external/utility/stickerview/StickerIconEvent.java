package com.xlteam.textonpicture.external.utility.stickerview;

import android.view.MotionEvent;

import com.xlteam.textonpicture.external.utility.stickerview.StickerView;

public interface StickerIconEvent {
    void onActionDown(StickerView stickerView, MotionEvent event);

    void onActionMove(StickerView stickerView, MotionEvent event);

    void onActionUp(StickerView stickerView, MotionEvent event);
}
