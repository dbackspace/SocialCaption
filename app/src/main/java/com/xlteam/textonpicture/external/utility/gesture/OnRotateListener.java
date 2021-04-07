package com.xlteam.textonpicture.external.utility.gesture;

import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.xlteam.textonpicture.external.utility.logger.Log;

public class OnRotateListener implements View.OnTouchListener {
    boolean freeze = false;
    int basex;
    int basey;
    int pivx;
    int pivy;
    float startDegree;
    RelativeLayout layGroup;
    RelativeLayout layBg;
    RelativeLayout.LayoutParams layoutParams;

    public OnRotateListener(RelativeLayout layoutGroup, RelativeLayout relativeLayout) {
        layGroup = layoutGroup;
        layBg = relativeLayout;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Log.d("binh.ngk", "as d ds");
        if (!freeze) {
            layoutParams = (RelativeLayout.LayoutParams) layGroup.getLayoutParams();
            int[] arrayOfInt = new int[2];
            layBg.getLocationOnScreen(arrayOfInt);
            int i = (int) event.getRawX() - arrayOfInt[0];
            int j = (int) event.getRawY() - arrayOfInt[1];
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    layGroup.invalidate();
                    startDegree = layGroup.getRotation();
                    pivx = (layoutParams.leftMargin + layGroup.getWidth() / 2);
                    pivy = (layoutParams.topMargin + layGroup.getHeight() / 2);
                    basex = (i - pivx);
                    basey = (pivy - j);
                    break;

                case MotionEvent.ACTION_MOVE:
                    int k = pivx;
                    int m = pivy;
                    j = (int) (Math.toDegrees(Math.atan2(basey, basex)) - Math
                            .toDegrees(Math.atan2(m - j, i - k)));
                    i = j;
                    if (j < 0) {
                        i = j + 360;
                    }
                    layGroup.setRotation((startDegree + i) % 360.0F);
                    break;
            }

            return true;
        }
        return freeze;
    }
}
