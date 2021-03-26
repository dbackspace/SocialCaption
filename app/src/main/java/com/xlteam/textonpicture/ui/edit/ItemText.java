package com.xlteam.textonpicture.ui.edit;

import com.xlteam.textonpicture.R;

public class ItemText {
    private static final String DEFAULT_COLOR_TEXT = "000000";
    private static final String DEFAULT_COLOR_BACKGROUND = "000000";
    private static final String DEFAULT_COLOR_SHADOW = "FF0000";
    private static final int DEFAULT_SIZE = R.dimen.text_added_default_size;
    private static final int DEFAULT_GRAVITY = 1;
    private static final int DEFAULT_FONT = 3;

    private static final int DEFAULT_OPACITY_TEXT = 100;
    private static final int DEFAULT_OPACITY_BACKGROUND = 0;
    private static final int DEFAULT_OPACITY_SHADOW = 0;
    private static final int DEFAULT_SATURATION_SHADOW = 0;
    private static final float DEFAULT_DX_SHADOW = 2f;
    private static final float DEFAULT_DY_SHADOW = 2f;

    private String text;
    private String colorText;
    private String colorBackground;
    private String colorShadow;
    private float size;
    private int gravity;
    private int font;
    private int opacityText;
    private int opacityBackground;
    private int saturationShadow, opacityShadow;
    private float dxShadow, dyShadow;

    // Khởi tạo giá trị default
    public ItemText(String text) {
        this.text = text;
        this.colorText = DEFAULT_COLOR_TEXT;
        this.colorBackground = DEFAULT_COLOR_BACKGROUND;
        this.colorShadow = DEFAULT_COLOR_SHADOW;
        this.size = DEFAULT_SIZE;
        this.gravity = DEFAULT_GRAVITY;
        this.font = DEFAULT_FONT;
        this.opacityText = DEFAULT_OPACITY_TEXT;
        this.opacityBackground = DEFAULT_OPACITY_BACKGROUND;
        this.saturationShadow = DEFAULT_SATURATION_SHADOW;
        this.opacityShadow = DEFAULT_OPACITY_SHADOW;
        this.dxShadow = DEFAULT_DX_SHADOW;
        this.dyShadow = DEFAULT_DY_SHADOW;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getColorShadow() {
        return colorShadow;
    }

    public void setColorShadow(String colorShadow) {
        this.colorShadow = colorShadow;
    }

    public int getOpacityText() {
        return opacityText;
    }

    public void setOpacityText(int opacityText) {
        this.opacityText = opacityText;
    }

    public int getOpacityBackground() {
        return opacityBackground;
    }

    public void setOpacityBackground(int opacityBackground) {
        this.opacityBackground = opacityBackground;
    }

    public int getSaturationShadow() {
        return saturationShadow;
    }

    public void setSaturationShadow(int saturationShadow) {
        this.saturationShadow = saturationShadow;
    }

    public int getOpacityShadow() {
        return opacityShadow;
    }

    public void setOpacityShadow(int opacityShadow) {
        this.opacityShadow = opacityShadow;
    }

    public float getDxShadow() {
        return dxShadow;
    }

    public void setDxShadow(float dxShadow) {
        this.dxShadow = dxShadow;
    }

    public float getDyShadow() {
        return dyShadow;
    }

    public void setDyShadow(float dyShadow) {
        this.dyShadow = dyShadow;
    }
}
