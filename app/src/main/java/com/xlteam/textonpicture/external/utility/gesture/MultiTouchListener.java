package com.xlteam.textonpicture.external.utility.gesture;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.xlteam.textonpicture.R;

public class MultiTouchListener implements OnTouchListener {

    private static final int INVALID_POINTER_ID = -1;
    private static final float DEFAULT_ICON_RADIUS = 30f;
    private final GestureDetector gestureListener;
    boolean isRotateEnabled = true;
    boolean isTranslateEnabled = true;
    boolean isScaleEnabled = true;
    float minimumScale = 0.2f;
    float maximumScale = 10.0f;
    private int activePointerId = INVALID_POINTER_ID;
    private static final int CLICK_THRESHOLD_DURATION = 300;
    private static final float CLICK_THRESHOLD_DISTANCE = 2f;
    private float prevX, prevY, prevRawX, prevRawY;
    private ScaleGestureDetector scaleGestureDetector;

    private int[] location = new int[2];
    private Rect outRect;
    private ImageView photoEditImageView;
    private Context mContext;

    private OnMultiTouchListener onMultiTouchListener;
    private OnGestureControl onGestureControl;
    boolean isTextPinchZoomable;
    private OnPhotoEditorListener onPhotoEditorListener;

    private View currentView;
    private ImageView zoomRotateBtn;

    private PointF midPoint = new PointF();
    private float oldDistance, oldRotation;
    private Matrix moveMatrix = new Matrix();

    public static MultiTouchListener create(Context context) {
        return new MultiTouchListener(context);
    }

    private MultiTouchListener(Context context) {
        isTextPinchZoomable = true;
        scaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener(this));
        gestureListener = new GestureDetector(context, new GestureListener());
        outRect = new Rect(0, 0, 0, 0);
        mContext = context;
    }

    public MultiTouchListener setBackgroundImage(ImageView photoEditImageView) {
        this.photoEditImageView = photoEditImageView;
        return this;
    }

    public MultiTouchListener setPhotoEditorListener(OnPhotoEditorListener onPhotoEditorListener) {
        this.onPhotoEditorListener = onPhotoEditorListener;
        return this;
    }

    public MultiTouchListener setZoomRotateBtn(ImageView zoomRotateBtn) {
        this.zoomRotateBtn = zoomRotateBtn;
        return this;
    }

    public MultiTouchListener setTextAddedView(View textAddedView) {
        this.currentView = textAddedView;
        return this;
    }

    private float adjustAngle(float degrees) {
        if (degrees > 180.0f) {
            degrees -= 360.0f;
        } else if (degrees < -180.0f) {
            degrees += 360.0f;
        }

        return degrees;
    }

    void move(View view, TransformInfo info) {
        computeRenderOffset(view, info.pivotX, info.pivotY);
        adjustTranslation(view, info.deltaX, info.deltaY);

        float scale = view.getScaleX() * info.deltaScale;
        scale = Math.max(info.minimumScale, Math.min(info.maximumScale, scale));
        view.setScaleX(scale);
        view.setScaleY(scale);

        keepScaleForFuncButton(view, scale);
        float rotation = adjustAngle(view.getRotation() + info.deltaAngle);
        view.setRotation(rotation);
    }

    void keepScaleForFuncButton(View view, float scale) {
        ImageView deleteButton = view.findViewById(R.id.image_text_remove);
        deleteButton.setScaleX(1.0f / scale);
        deleteButton.setScaleY(1.0f / scale);

        ImageView balanceButton = view.findViewById(R.id.image_text_balance);
        balanceButton.setScaleX(1.0f / scale);
        balanceButton.setScaleY(1.0f / scale);

        ImageView editButton = view.findViewById(R.id.image_text_edit);
        editButton.setScaleX(1.0f / scale);
        editButton.setScaleY(1.0f / scale);

        ImageView zoomButton = view.findViewById(R.id.image_text_zoom);
        zoomButton.setScaleX(1.0f / scale);
        zoomButton.setScaleY(1.0f / scale);
    }

    private void adjustTranslation(View view, float deltaX, float deltaY) {
        float[] deltaVector = {deltaX, deltaY};
        view.getMatrix().mapVectors(deltaVector);
        view.setTranslationX(view.getTranslationX() + deltaVector[0]);
        view.setTranslationY(view.getTranslationY() + deltaVector[1]);
    }

    private void computeRenderOffset(View view, float pivotX, float pivotY) {
        if (view.getPivotX() == pivotX && view.getPivotY() == pivotY) {
            return;
        }

        float[] prevPoint = {0.0f, 0.0f};
        view.getMatrix().mapPoints(prevPoint);

        view.setPivotX(pivotX);
        view.setPivotY(pivotY);

        float[] currPoint = {0.0f, 0.0f};
        view.getMatrix().mapPoints(currPoint);

        float offsetX = currPoint[0] - prevPoint[0];
        float offsetY = currPoint[1] - prevPoint[1];

        view.setTranslationX(view.getTranslationX() - offsetX);
        view.setTranslationY(view.getTranslationY() - offsetY);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(view, event);
        gestureListener.onTouchEvent(event);

        if (!isTranslateEnabled) {
            return true;
        }

        int action = event.getAction();

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (action & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                prevRawX = event.getRawX();
                prevRawY = event.getRawY();
                activePointerId = event.getPointerId(0);
                view.bringToFront();

                midPoint = calculateMidPoint();
                oldDistance = calculateDistance(midPoint.x, midPoint.y, prevX, prevY);
                oldRotation = calculateRotation(midPoint.x, midPoint.y, prevX, prevY);
                notifyWhenEventChangeListener(view, EventType.ON_DOWN);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndexMove = event.findPointerIndex(activePointerId);
                if (pointerIndexMove != -1) {
                    float zoomX = event.getX(pointerIndexMove);
                    float zoomY = event.getY(pointerIndexMove);
                    if (isZoomIconTouched(zoomX, zoomY, event)) {
                        float newDistance = calculateDistance(midPoint.x, midPoint.y, event.getX(), event.getY());
                        float newRotation = calculateRotation(midPoint.x, midPoint.y, event.getX(), event.getY());
                        currentView.setRotation((newRotation - oldRotation));
                        currentView.setScaleX(newDistance / oldDistance);
                        currentView.setScaleY(newDistance / oldDistance);
                    } else {
                        float currX = event.getX(pointerIndexMove);
                        float currY = event.getY(pointerIndexMove);
                        if (!scaleGestureDetector.isInProgress()) {
                            adjustTranslation(view, currX - prevX, currY - prevY);
                        }
                        long duration = event.getEventTime() - event.getDownTime();
                        if (!(duration < CLICK_THRESHOLD_DURATION && isSingleTapEvent(prevRawX, x, prevRawY, y))) {
                            notifyWhenEventChangeListener(view, EventType.ON_MOVE);
                        }
                    }
                    midPoint = calculateMidPoint();
                    oldDistance = calculateDistance(midPoint.x, midPoint.y, prevX, prevY);
                    oldRotation = calculateRotation(midPoint.x, midPoint.y, prevX, prevY);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                activePointerId = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_UP:
                activePointerId = INVALID_POINTER_ID;
                if (!isViewInBounds(photoEditImageView, x, y)) {
                    view.animate().translationY(0).translationY(0);
                }
                long duration = event.getEventTime() - event.getDownTime();
                if (!(duration < CLICK_THRESHOLD_DURATION && isSingleTapEvent(prevRawX, x, prevRawY, y))) {
                    notifyWhenEventChangeListener(view, EventType.ON_UP);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int pointerIndexPointerUp = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                int pointerId = event.getPointerId(pointerIndexPointerUp);
                if (pointerId == activePointerId) {
                    int newPointerIndex = pointerIndexPointerUp == 0 ? 1 : 0;
                    prevX = event.getX(newPointerIndex);
                    prevY = event.getY(newPointerIndex);
                    activePointerId = event.getPointerId(newPointerIndex);
                }
                break;
        }
        return true;
    }

    private boolean isZoomIconTouched(float x, float y, MotionEvent event) {
        if (x == zoomRotateBtn.getX() && y == zoomRotateBtn.getY())
            return true;
        return false;
    }

    private PointF calculateMidPoint() {
        midPoint.set(currentView.getWidth() * 1f / 2, currentView.getHeight() * 1f / 2);
        return midPoint;
    }

    protected float calculateDistance(@Nullable MotionEvent event) {
        if (event == null || event.getPointerCount() < 2) {
            return 0f;
        }
        return calculateDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
    }

    protected float calculateDistance(float x1, float y1, float x2, float y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        return (float) Math.sqrt(x * x + y * y);
    }

    private float calculateRotation(float x1, float y1, float x2, float y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        double radians = Math.atan2(y, x);
        return (float) Math.toDegrees(radians);
    }

    enum EventType {
        ON_DOWN,
        ON_MOVE,
        ON_UP
    }

    ;

    private boolean isSingleTapEvent(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return (CLICK_THRESHOLD_DISTANCE > differenceX) || (CLICK_THRESHOLD_DISTANCE > differenceY);
    }

    private void notifyWhenEventChangeListener(View view, EventType eventType) {
        if (onPhotoEditorListener != null) {
            switch (eventType) {
                case ON_DOWN:
                    onPhotoEditorListener.onEventDownChangeListener(view);
                    break;
                case ON_MOVE:
                    onPhotoEditorListener.onEventMoveChangeListener(view);
                    break;
                case ON_UP:
                    onPhotoEditorListener.onEventUpChangeListener(view);
                    break;
            }
        }
    }

    private boolean isViewInBounds(View view, int x, int y) {
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

    private void setOnMultiTouchListener(OnMultiTouchListener onMultiTouchListener) {
        this.onMultiTouchListener = onMultiTouchListener;
    }

    public void setOnGestureControl(OnGestureControl onGestureControl) {
        this.onGestureControl = onGestureControl;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (onGestureControl != null) {
                onGestureControl.onDoubleClick(currentView);
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            if (onGestureControl != null) {
                onGestureControl.onDown(currentView);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            if (onGestureControl != null) {
                onGestureControl.onLongClick();
            }
        }
    }
}
