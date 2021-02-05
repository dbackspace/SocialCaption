package com.xlteam.socialcaption.ui.edit;

import android.text.TextUtils;

public class ItemText {
    private String text = "";
    private int color = -1;
    private float size = -1;
    private int alignment = -1;

    public ItemText(String text, int color, float size, int alignment) {
        this.text = text;
        this.color = color;
        this.size = size;
        this.alignment = alignment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public boolean isEmpty() {
        return !TextUtils.isEmpty(text) || size != -1 || color != -1 || alignment != -1;
    }
}
