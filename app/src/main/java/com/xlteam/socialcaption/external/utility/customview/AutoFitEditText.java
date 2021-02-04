package com.xlteam.socialcaption.external.utility.customview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.widget.EditText;

import com.xlteam.socialcaption.R;

public class AutoFitEditText extends EditText {
    private final static int WITHOUT_SIZE = 0;
    private final static float DEFAULT_TEXT_SCALE = 1f; // default giảm size còn 0.7 so với ban đầu
    private final static int DEFAULT_ANIMATION_DURATION = 300;
    private final static float DEFAULT_LINES_LIMIT = 3f; // default sau 3 line pixel sẽ giảm size
    private static final int DEF_STYLE_ATTR = 0;
    private static final int DEF_STYLE_RES = 0;
    private int originalViewWidth = WITHOUT_SIZE;
    private float originalTextSize = WITHOUT_SIZE;
    private boolean resizeInProgress = false;
    private float linesLimit = DEFAULT_LINES_LIMIT;
    private int animationDuration = DEFAULT_ANIMATION_DURATION;
    private float textScale = DEFAULT_TEXT_SCALE;
    private Paint textMeasuringPaint;

    public AutoFitEditText(Context context) {
        super(context);
    }

    public AutoFitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        configureAttributes(attrs);
    }

    public AutoFitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        configureAttributes(attrs);
    }

    @TargetApi(21)
    public AutoFitEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        configureAttributes(attrs);
    }

    @SuppressWarnings("unused")
    public void setLinesLimit(float linesLimit) {
        this.linesLimit = linesLimit;
    }

    @SuppressWarnings("unused")
    public void setTextScale(float textScale) {
        this.textScale = textScale;
    }

    @SuppressWarnings("unused")
    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    private void configureAttributes(AttributeSet attrs) {
        TypedArray attributes = getContext().getTheme()
                .obtainStyledAttributes(attrs, R.styleable.AutoFitEditText, DEF_STYLE_ATTR, DEF_STYLE_RES);
        linesLimit = attributes
                .getFloat(R.styleable.AutoFitEditText_linesLimit, DEFAULT_LINES_LIMIT);
        textScale = attributes
                .getFloat(R.styleable.AutoFitEditText_textScale, DEFAULT_TEXT_SCALE);
        animationDuration = attributes
                .getInteger(R.styleable.AutoFitEditText_animationDuration, DEFAULT_ANIMATION_DURATION);
        attributes.recycle();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (!resizeInProgress) {
            float numOfLinesOnScreen = calculateNumberOfLinesNedeed();
//            if (numOfLinesOnScreen > linesLimit) {
//                resizeTextToSmallSize();
//            } else {
                resizeTextToNormalSize();
//            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (originalViewWidth == WITHOUT_SIZE) {
            originalViewWidth = getMeasuredWidth();
            originalViewWidth -= getPaddingRight() + getPaddingLeft();
        }
        if (originalTextSize == WITHOUT_SIZE) {
            originalTextSize = getTextSize();
            initializeTextMeasurerPaint();
        }
    }

    private void initializeTextMeasurerPaint() {
        textMeasuringPaint = new Paint();
        textMeasuringPaint.setTypeface(getTypeface());
        textMeasuringPaint.setTextSize(originalTextSize);
    }

    private void resizeTextToSmallSize() {
        float smallTextSize = (originalTextSize * textScale);
        float currentTextSize = getTextSize();
        if (currentTextSize > smallTextSize) {
            playAnimation(currentTextSize, smallTextSize);
        }
    }

    private void resizeTextToNormalSize() {
        float currentTextSize = getTextSize();
        if (currentTextSize < originalTextSize) {
            playAnimation(currentTextSize, originalTextSize);
        }
    }

    private float calculateNumberOfLinesNedeed() {
        float textSizeInPixels = measureText();
        return (textSizeInPixels / originalViewWidth);
    }

    private float measureText() {
        float result = 0f;
        if (textMeasuringPaint != null) {
            result = textMeasuringPaint.measureText(getText().toString());
        }
        return result;
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_ENTER) {
//            return true;
            /**
             * Xem xét trường hợp khi enter thì giảm size của text lại 1 bậc (giống zalo).
             * Có số lượng check enter phù hợp, nếu đạt đến giới hạn giảm size thì return true if keycode = ENTER.
             */
        }
        return super.onKeyDown(keyCode, event);
    }

    private void playAnimation(float origin, float destination) {
        ObjectAnimator animator
                = ObjectAnimator.ofFloat(this, "textSize", origin, destination);
        animator.setTarget(this);
        animator.setDuration(animationDuration);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                resizeInProgress = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                resizeInProgress = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }
}
