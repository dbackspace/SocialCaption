package com.xlteam.textonpicture.ui.edit;

import android.graphics.Color;
import android.view.Gravity;

import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.utility.utils.Constant;

public class ItemText {
    private static final int DEFAULT_COLOR = Color.WHITE;
    private static final int DEFAULT_SIZE = R.dimen.text_added_default_size;
    private static final int DEFAULT_GRAVITY = Gravity.CENTER;
    private static final int DEFAULT_FONT = 3;
    private static final int DEFAULT_BG = Constant.BACKGROUND_COLOR_0;

    private String text;
    private int color;
    private float size;
    private int gravity;
    private int font;
    private int bg;

    // Khởi tạo giá trị default
    public ItemText(String text) {
        this.text = text;
        this.color = DEFAULT_COLOR;
        this.size = DEFAULT_SIZE;
        this.gravity = DEFAULT_GRAVITY;
        this.font = DEFAULT_FONT;
        this.bg = DEFAULT_BG;
    }

    public ItemText(String text, int color, float size, int alignment) {
        this.text = text;
        this.color = color;
        this.size = size;
        this.gravity = alignment;
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

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }
}
