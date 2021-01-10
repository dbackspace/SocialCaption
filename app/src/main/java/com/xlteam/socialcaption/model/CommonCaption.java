package com.xlteam.socialcaption.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "common_caption_table")
public class CommonCaption implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "_content")
    private String content;
    @ColumnInfo(name = "_path_image")
    private String pathImage;
    @ColumnInfo(name = "_category_type")
    private int categoryType;
    @ColumnInfo(name = "_saved")
    private boolean saved;

    public CommonCaption() {
    }

    public CommonCaption(String content, String pathImage, int categoryType, boolean saved) {
        this.content = content;
        this.pathImage = pathImage;
        this.categoryType = categoryType;
        this.saved = saved;
    }

    protected CommonCaption(Parcel in) {
        id = in.readLong();
        content = in.readString();
        pathImage = in.readString();
        categoryType = in.readInt();
        saved = in.readByte() != 0;
    }

    public static final Creator<CommonCaption> CREATOR = new Creator<CommonCaption>() {
        @Override
        public CommonCaption createFromParcel(Parcel in) {
            return new CommonCaption(in);
        }

        @Override
        public CommonCaption[] newArray(int size) {
            return new CommonCaption[size];
        }
    };

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(content);
        dest.writeString(pathImage);
        dest.writeInt(categoryType);
        dest.writeByte((byte) (saved ? 1 : 0));
    }
}
