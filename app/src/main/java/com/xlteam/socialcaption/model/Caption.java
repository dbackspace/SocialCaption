package com.xlteam.socialcaption.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "caption_table")
public class Caption {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "_content")
    private String content;
    @ColumnInfo(name = "_path_image")
    private String pathImage;
    @ColumnInfo(name = "_category_type")
    private int categoryType;
    @ColumnInfo(name = "_saved")
    private boolean saved;

    public Caption() {
    }

    public Caption(String content, String pathImage, int categoryType, boolean saved) {
        this.content = content;
        this.pathImage = pathImage;
        this.categoryType = categoryType;
        this.saved = saved;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
