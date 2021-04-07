package com.xlteam.textonpicture.external.utility.gesture;

import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import timber.log.Timber;

public class OnZoomListener implements View.OnTouchListener {
    boolean freeze = false;
    int baseh;
    int basew;
    int basex;
    int basey;
    int margl;
    int margt;
    private final float sizeTextDefault;
    private final float canhHuyenDefault;
    private final int heightDefault;
    private final int widthDefault;
    float startDegree;
    RelativeLayout layGroup;
    RelativeLayout layBg;
    RelativeLayout.LayoutParams layoutParams;
    TextView currentTextView;

    public OnZoomListener(RelativeLayout layoutGroup, RelativeLayout relativeLayout, TextView tv) {
        layGroup = layoutGroup;
        layBg = relativeLayout;
        currentTextView = tv;
        sizeTextDefault = currentTextView.getTextSize();
        heightDefault = currentTextView.getMeasuredHeight() + 150;
        widthDefault = currentTextView.getMeasuredWidth() + 150;
        canhHuyenDefault = (float) Math.sqrt(heightDefault * heightDefault + widthDefault * widthDefault);
        basex= 0;
        basey = 0;

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (!freeze) {
            int j = (int) event.getRawX();
            int i = (int) event.getRawY();
            layoutParams = (RelativeLayout.LayoutParams) layGroup.getLayoutParams();
            float mScaleFactor = 1.f;
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    layGroup.invalidate();
                    basex = j;
                    basey = i;
                    basew = layGroup.getWidth();
                    baseh = layGroup.getHeight();
                    int[] loaction = new int[2];
                    layGroup.getLocationOnScreen(loaction);
                    margl = layoutParams.leftMargin;
                    margt = layoutParams.topMargin;
                    break;
                case MotionEvent.ACTION_MOVE:

                    float f2 = (float) Math.toDegrees(Math.atan2(i - basey, j - basex));
                    float f1 = f2;
                    if (f2 < 0.0F) {
                        f1 = f2 + 360.0F;
                    }
                    j -= basex;
                    int k = i - basey;
                    i = (int) (Math.sqrt(j * j + k * k) * Math.cos(Math.toRadians(f1
                            - layGroup.getRotation())));
                    j = (int) (Math.sqrt(i * i + k * k) * Math.sin(Math.toRadians(f1
                            - layGroup.getRotation())));
                    k = i * 2 + basew;
                    int m = j * 2 + baseh;
                    if (k > widthDefault) {
                        layoutParams.width = k;
                        layoutParams.leftMargin = (margl - i);
                    }
                    if (m > heightDefault) {
                        layoutParams.height = m;
                        layoutParams.topMargin = (margt - j);
                    }

                    float scaleCurrent;

                    if (k != 150 || m != 150) {
                        mScaleFactor = (float) Math.sqrt(k * k + m * m);
                        scaleCurrent = mScaleFactor / canhHuyenDefault;
                    } else {
                        scaleCurrent = mScaleFactor;
                    }
                    Timber.e("widthDefault: " + widthDefault + ", 'heightDefault: " + heightDefault);
                    Timber.e("k: " + k + ", m: " + m + " ,width: " + layoutParams.width + " ,height: " + layoutParams.height + " , scaleCurrent: " + scaleCurrent);
                    if ((k > 0 || m > 0) && (k > -widthDefault && m > -heightDefault)) {
                        scaleCurrent = Math.max(1.0f, scaleCurrent);
                        currentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeTextDefault * scaleCurrent);
                    }

                    layGroup.setLayoutParams(layoutParams);
                    layGroup.performLongClick();
                    break;
            }
            return true;

        }
        return freeze;
    }
}
