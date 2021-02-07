package com.xlteam.socialcaption.external.utility.gesture;


import android.view.View;

public interface OnPhotoEditorListener {
    void onAddViewListener(int numberOfAddedViews);
    void onRemoveViewListener(int numberOfAddedViews);

    void onEventDownChangeListener(View view);
    void onEventMoveChangeListener(View view);
    void onEventUpChangeListener(View view);
}
