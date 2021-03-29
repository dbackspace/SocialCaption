package com.xlteam.textonpicture.external.utility.stickerview;

public class FlipHorizontallyEvent extends AbstractFlipEvent {
    @Override
    @StickerView.Flip
    protected int getFlipDirection() {
        return StickerView.FLIP_HORIZONTALLY;
    }
}
