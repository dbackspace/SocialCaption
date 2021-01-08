package com.xlteam.socialcaption.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;

@Fts4(contentEntity = Caption.class)
@Entity(tableName = "caption_fts_table")
public class CaptionFts {
    @ColumnInfo(name = "_content")
    private String content;
    @ColumnInfo(name = "_path_image")
    private String pathImage;
    @ColumnInfo(name = "_category_type")
    private int categoryType;
    @ColumnInfo(name = "_saved")
    private boolean saved;

    public CaptionFts() {
    }

    public CaptionFts(String content, String pathImage, int categoryType, boolean saved) {
        this.content = content;
        this.pathImage = pathImage;
        this.categoryType = categoryType;
        this.saved = saved;
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

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
