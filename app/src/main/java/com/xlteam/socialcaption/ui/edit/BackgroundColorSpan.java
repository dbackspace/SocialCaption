package com.xlteam.socialcaption.ui.edit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

import com.xlteam.socialcaption.external.utility.utils.Constant;

public class BackgroundColorSpan implements LineBackgroundSpan {
    private float padding;
    private float radius;

    private RectF rect = new RectF();
    private Paint paint = new Paint();
    private Paint paintStroke = new Paint();
    private Path path = new Path();

    private float prevWidth = -1f;
    private float prevLeft = -1f;
    private float prevRight = -1f;
    private float prevBottom = -1f;
    private float prevTop = -1f;

    private int align = Constant.ALIGN_CENTER;

    public BackgroundColorSpan(String backgroundColor,
                               float padding,
                               float radius) {
        this.padding = padding;
        this.radius = radius;

        paint.setColor(Color.parseColor(backgroundColor));
        //paintStroke.setStyle(Paint.Style.STROKE);
        //paintStroke.setStrokeWidth(5f);
        paintStroke.setColor(Color.parseColor(backgroundColor));
    }

    public void setColor(String color) {
        paint.setColor(Color.parseColor(color));
        paintStroke.setColor(Color.parseColor(color));
    }

    public void setAlignment(int alignment) {
        align = alignment;
    }

    @Override
    public void drawBackground(
            final Canvas c,
            final Paint p,
            final int left,
            final int right,
            final int top,
            final int baseline,
            final int bottom,
            final CharSequence text,
            final int start,
            final int end,
            final int lnum) {

        float width = p.measureText(text, start, end) + 2f * padding;
        float shiftLeft, shiftRight;

        if (align == Constant.ALIGN_START) {
            shiftLeft = 0f - padding;
            shiftRight = width + shiftLeft;
        } else if (align == Constant.ALIGN_END) {
            shiftLeft = right - width + padding;
            shiftRight = right + padding;
        } else {
            shiftLeft = (right - width) / 2;
            shiftRight = right - shiftLeft;
        }

        rect.set(shiftLeft, top, shiftRight, bottom);

        if (lnum == 0) {
            c.drawRoundRect(rect, radius, radius, paint);
        } else {
            path.reset();
            float dr = width - prevWidth;
            float diff = -Math.signum(dr) * Math.min(2f * radius, Math.abs(dr / 2f)) / 2f;
            path.moveTo(
                    prevLeft, prevBottom - radius
            );
            if (align != Constant.ALIGN_START) {
                path.cubicTo(//1
                        prevLeft, prevBottom - radius,
                        prevLeft, rect.top,
                        prevLeft + diff, rect.top
                );
            } else {
                path.lineTo(prevLeft, prevBottom + radius);
            }
            path.lineTo(
                    rect.left - diff, rect.top
            );
            path.cubicTo(//2
                    rect.left - diff, rect.top,
                    rect.left, rect.top,
                    rect.left, rect.top + radius
            );
            path.lineTo(
                    rect.left, rect.bottom - radius
            );
            path.cubicTo(//3
                    rect.left, rect.bottom - radius,
                    rect.left, rect.bottom,
                    rect.left + radius, rect.bottom
            );
            path.lineTo(
                    rect.right - radius, rect.bottom
            );
            path.cubicTo(//4
                    rect.right - radius, rect.bottom,
                    rect.right, rect.bottom,
                    rect.right, rect.bottom - radius
            );
            path.lineTo(
                    rect.right, rect.top + radius
            );

            if (align != Constant.ALIGN_END) {
                path.cubicTo(//5
                        rect.right, rect.top + radius,
                        rect.right, rect.top,
                        rect.right + diff, rect.top
                );
                path.lineTo(
                        prevRight - diff, rect.top
                );
                path.cubicTo(//6
                        prevRight - diff, rect.top,
                        prevRight, rect.top,
                        prevRight, prevBottom - radius
                );

            } else {
                path.lineTo(prevRight, prevBottom - radius);
            }
            path.cubicTo(//7
                    prevRight, prevBottom - radius,
                    prevRight, prevBottom,
                    prevRight - radius, prevBottom
            );

            path.lineTo(
                    prevLeft + radius, prevBottom
            );

            path.cubicTo(//8
                    prevLeft + radius, prevBottom,
                    prevLeft, prevBottom,
                    prevLeft, rect.top - radius
            );
            c.drawPath(path, paintStroke);
        }

        prevWidth = width;
        prevLeft = rect.left;
        prevRight = rect.right;
        prevBottom = rect.bottom;
        prevTop = rect.top;
    }
}