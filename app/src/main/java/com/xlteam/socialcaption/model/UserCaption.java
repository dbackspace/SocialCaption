package com.xlteam.socialcaption.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_caption_table")
public class UserCaption {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "_content")
    private String content;
    @ColumnInfo(name = "_path_image")
    private String pathImage;
    @ColumnInfo(name = "_color_index")
    private int color; // mã màu đang chọn gần nhất (trước khi thoát)
    @ColumnInfo(name = "_font_index")
    private int font; // mã font đang chọn gần nhất (trước khi thoát)
    @ColumnInfo(name = "_align_index")
    private int align; // mã align dang chọn gần nhất (trước khi thoát)
    @ColumnInfo(name = "_complete")
    private boolean complete; // true: caption hoàn thiện, false: caption đang sửa dở

    public UserCaption(String content, String pathImage, int color, int font, int align, boolean complete) {
        this.content = content;
        this.pathImage = pathImage;
        this.color = color;
        this.font = font;
        this.complete = complete;
        this.align = align;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }
}
