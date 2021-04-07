package com.xlteam.textonpicture.external.utility.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.utility.utils.Utility;

public class ClipArt extends RelativeLayout {

    int baseh;
    int basew;
    int basex;
    int basey;
    ImageButton imgRemove;
    ImageButton imgRotate;
    ImageButton imgBalance;
    ImageButton imgZoom;
    RelativeLayout clip;
    Context mContext;
    boolean freeze = false;
    int h;
    int i;
    TextView currentTextView;
    String imageUri;
    ImageView imgring;
    boolean isShadow;
    int iv;
    RelativeLayout layBg;
    RelativeLayout layGroup;
    LayoutParams layoutParams;
    public LayoutInflater mInflater;
    int margl;
    int margt;
    // DisplayImageOptions op;
    float opacity = 1.0F;
    Bitmap originalBitmap;
    int pivx;
    int pivy;
    int pos;
    Bitmap shadowBitmap;
    float startDegree;
    String[] v;

    private final int heightDefault;
    private final int widthDefault;
    private float mScaleFactor = 1.f;
    private float sizeTextDefault;
    private float canhHuyenDefault;

    @SuppressLint("ClickableViewAccessibility")
    public ClipArt(Context paramContext, String text) {
        super(paramContext);
        mContext = paramContext;
        layGroup = this;
        // this.clip = paramRelativeLayout;
        basex = 0;
        basey = 0;
        pivx = 0;
        pivy = 0;
        // .v = paramArrayOfString;
        // this.op = paramDisplayImageOptions;
//        mInflater = ((LayoutInflater) paramContext.getSystemService("layout_inflater"));
        mInflater = LayoutInflater.from(paramContext);
        mInflater.inflate(R.layout.clipart, this, true);
        imgRemove = findViewById(R.id.image_text_remove);
        imgRotate = findViewById(R.id.image_text_rotate);
        imgBalance = findViewById(R.id.image_text_balance);
        imgZoom = findViewById(R.id.image_text_zoom);
        imgring = findViewById(R.id.image_border);
        // imageUri = ("assets://Cliparts/" + paramArrayOfString[paramInt1]);


        currentTextView = ((TextView) findViewById(R.id.clipart));
        currentTextView.setText(text);
        Typeface type = Typeface.createFromAsset(mContext.getAssets(), "font/" + "dancingscript_bold.ttf");
        currentTextView.setTypeface(type);
        sizeTextDefault = currentTextView.getTextSize();
        Utility.setColorForView(currentTextView, "#00FFFFFF");
        currentTextView.measure(0, 0);

        heightDefault = (int) (currentTextView.getMeasuredHeight() + 150);
        widthDefault = (int) (currentTextView.getMeasuredWidth() + 150);
        Log.e("TEST", "heightDefaultText: " + currentTextView.getMeasuredHeight() + " ,widthDefaultText: " + currentTextView.getMeasuredWidth());
        Log.e("TEST", "heightDefault: " + heightDefault + " ,widthDefault: " + widthDefault);


//        currentTextView.setMinWidth(widthDefault);
//        currentTextView.setMinHeight(heightDefault);

        canhHuyenDefault = (float) Math.sqrt(heightDefault * heightDefault + widthDefault * widthDefault);

        layoutParams = new LayoutParams(widthDefault, heightDefault);
        layGroup.setLayoutParams(layoutParams);

        layGroup.setMinimumHeight(heightDefault);
        layGroup.setMinimumWidth(widthDefault);

        // ImageLoader.getInstance().displayImage(this.imageUri, this.image,
        // paramDisplayImageOptions);
        currentTextView.setTag(Integer.valueOf(0));
        setOnTouchListener(new OnTouchListener() {
            final GestureDetector gestureDetector = new GestureDetector(mContext,
                    new GestureDetector.SimpleOnGestureListener() {
                        public boolean onDoubleTap(MotionEvent paramAnonymous2MotionEvent) {
                            return false;
                        }
                    });

            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                visiball();
                if (!freeze) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            layGroup.invalidate();
                            gestureDetector.onTouchEvent(event);

                            layGroup.bringToFront();

                            layGroup.performClick();
                            basex = ((int) (event.getRawX() - layoutParams.leftMargin));
                            basey = ((int) (event.getRawY() - layoutParams.topMargin));
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int i = (int) event.getRawX();
                            int j = (int) event.getRawY();
                            layBg = (RelativeLayout) (getParent());
                            if ((i - basex > -(layGroup.getWidth() * 2 / 3))
                                    && (i - basex < layBg.getWidth() - layGroup.getWidth() / 3)) {
                                layoutParams.leftMargin = (i - basex);
                            }
                            if ((j - basey > -(layGroup.getHeight() * 2 / 3))
                                    && (j - basey < layBg.getHeight() - layGroup.getHeight() / 3)) {
                                layoutParams.topMargin = (j - basey);
                            }
                            layoutParams.rightMargin = -9999999;
                            layoutParams.bottomMargin = -9999999;
                            layGroup.setLayoutParams(layoutParams);
                            break;

                    }

                    return true;
                }
                return true;
                // freeze;
            }
        });

        this.imgZoom.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                if (!freeze) {
                    int j = (int) event.getRawX();
                    int i = (int) event.getRawY();
                    layoutParams = (LayoutParams) layGroup.getLayoutParams();
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
                            Log.e("TEST", "-------------------------------------------");
                            Log.e("TEST", "k: " + k + " ,width: " + (layoutParams.width));

                            if (m > heightDefault) {
                                layoutParams.height = m;
                                layoutParams.topMargin = (margt - j);
                            }
                            Log.e("TEST", "m: " + m + " ,height: " + (layoutParams.height));

                            if (k == 1 && m == 1) {
                                mScaleFactor = 1;
                            } else {
                                mScaleFactor = (float) Math.sqrt(k * k + m * m);
                            }

//                            Log.e("mScaleFactor", ((mScaleFactor)/scaleDefault) + "");
//                            Log.e("defaultSize", (defaultSize) + "");
                            float scaleCurrent = mScaleFactor / canhHuyenDefault;
                            Log.e("current", scaleCurrent + "");

                            scaleCurrent = Math.max(1.0f, scaleCurrent);
//                            currentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeTextDefault * scaleCurrent);
                            currentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getFitTextSize(new Paint(), layoutParams.width, layoutParams.height, currentTextView.getText().toString()));

                            Log.e("DEBUG", "textW: " + currentTextView.getMeasuredWidth() + " textH: " + currentTextView.getMeasuredHeight());
                            Log.e("TEST", "-------------------------------------------");

                            layGroup.setLayoutParams(layoutParams);
                            layGroup.performLongClick();

                            break;
                    }
                    return true;

                }
                return freeze;
            }
        });
        this.imgRotate.setOnTouchListener(new OnTouchListener() {
            @SuppressLint({"NewApi"})
            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                if (!freeze) {
                    layoutParams = (LayoutParams) layGroup.getLayoutParams();
                    layBg = ((RelativeLayout) getParent());
                    int[] arrayOfInt = new int[2];
                    layBg.getLocationOnScreen(arrayOfInt);
                    int i = (int) event.getRawX() - arrayOfInt[0];
                    int j = (int) event.getRawY() - arrayOfInt[1];
                    switch (event.getAction()) {

                        case MotionEvent.ACTION_DOWN:
                            layGroup.invalidate();
                            startDegree = layGroup.getRotation();
                            pivx = (layoutParams.leftMargin + getWidth() / 2);
                            pivy = (layoutParams.topMargin + getHeight() / 2);
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
        });
        this.imgRemove.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (!freeze) {
                    layBg = ((RelativeLayout) getParent());
                    layBg.performClick();
                    layBg.removeView(layGroup);
                }
            }
        });
    }

    public void disableAll() {
        this.imgRemove.setVisibility(View.INVISIBLE);
        this.imgRotate.setVisibility(View.INVISIBLE);
        this.imgZoom.setVisibility(View.INVISIBLE);
        this.imgring.setVisibility(View.INVISIBLE);
        this.imgBalance.setVisibility(INVISIBLE);
    }

    public TextView getImageView() {
        return this.currentTextView;
    }

    public float getOpacity() {
        return this.currentTextView.getAlpha();
    }

    public void resetImage() {
        this.originalBitmap = null;
        this.layGroup.performLongClick();
    }

    public void setColor(int paramInt) {
//		this.image.getDrawable().setColorFilter(null);
        ColorMatrixColorFilter localColorMatrixColorFilter = new ColorMatrixColorFilter(new float[]{0.33F, 0.33F,
                0.33F, 0.0F, Color.red(paramInt), 0.33F, 0.33F, 0.33F, 0.0F, Color.green(paramInt), 0.33F, 0.33F,
                0.33F, 0.0F, Color.blue(paramInt), 0.0F, 0.0F, 0.0F, 1.0F, 0.0F});
//		this.image.getDrawable().setColorFilter(localColorMatrixColorFilter);
        this.currentTextView.setTag(Integer.valueOf(paramInt));
        this.layGroup.performLongClick();
    }

    public void setFreeze(boolean paramBoolean) {
        this.freeze = paramBoolean;
    }

    public void setImageId() {
        this.currentTextView.setId(this.layGroup.getId() + this.i);
        this.i += 1;
    }

    public void setLocation() {
        this.layBg = ((RelativeLayout) getParent());
        LayoutParams localLayoutParams = (LayoutParams) this.layGroup.getLayoutParams();
        localLayoutParams.topMargin = ((int) (Math.random() * (this.layBg.getHeight() - 400)));
        localLayoutParams.leftMargin = ((int) (Math.random() * (this.layBg.getWidth() - 400)));
        this.layGroup.setLayoutParams(localLayoutParams);
    }

    public void visiball() {
        this.imgRemove.setVisibility(View.VISIBLE);
        this.imgRotate.setVisibility(View.VISIBLE);
        this.imgZoom.setVisibility(View.VISIBLE);
        this.imgring.setVisibility(View.VISIBLE);
        this.imgBalance.setVisibility(VISIBLE);
    }

    public ImageView getImgZoom() {
        return imgZoom;
    }

    public static abstract interface DoubleTapListener {
        public abstract void onDoubleTap();
    }

    private static int getFitTextSize(Paint paint, int width, int height, String text) {
        int maxSizeToFitWidth = (int) ((float) width / paint.measureText(text) * paint.getTextSize());
        return Math.min(maxSizeToFitWidth, height);
    }

    public void setText(String text) {
        currentTextView.setText(text);
    }
}
