package com.xlteam.textonpicture.external.utility.stickerview;

import android.view.MotionEvent;

public class BalanceIconEvent implements StickerIconEvent {
    @Override
    public void onActionDown(StickerView stickerView, MotionEvent event) {
    }

    @Override
    public void onActionMove(StickerView stickerView, MotionEvent event) {
    }

    @Override
    public void onActionUp(StickerView stickerView, MotionEvent event) {
        stickerView.balanceCurrentSticker(event);
    }
}
