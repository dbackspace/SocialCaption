package com.xlteam.textonpicture.external.utility.stickerview;

import android.view.MotionEvent;

public class EditIconEvent implements StickerIconEvent {

    @Override
    public void onActionDown(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionMove(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionUp(StickerView stickerView, MotionEvent event) {
        if (stickerView.getOnStickerOperationListener() != null) {
            stickerView.getOnStickerOperationListener()
                    .onStickerEdited(stickerView.getCurrentSticker());
        }
    }
}
