package com.xlteam.socialcaption.external.utility.gesture;


public interface OnPhotoEditorListener {
    void onAddViewListener(int numberOfAddedViews);
    void onRemoveViewListener(int numberOfAddedViews);

    void onEventDownChangeListener();
    void onEventMoveChangeListener();
    void onEventUpChangeListener();
}
