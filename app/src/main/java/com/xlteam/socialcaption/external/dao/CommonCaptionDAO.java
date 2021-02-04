package com.xlteam.socialcaption.external.dao;

import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

@Dao
public abstract class CommonCaptionDAO {
    @Insert
    public abstract void insertCaption(CommonCaption caption);

    @Update
    public abstract int updateCaption(CommonCaption caption);

    public void updateCaptionBySaved(long id, boolean saved) {
        CommonCaption caption = getCaption(id);
        caption.setSaved(saved);
        updateCaption(caption);
    }

    @Query("SELECT * FROM common_caption_table WHERE id == :id")
    public abstract CommonCaption getCaption(long id);

    @Query("select * from common_caption_table where _category_type = :categoryType")
    public abstract List<CommonCaption> getCaptionByCategoryType(int categoryType);

    @Query("select * from common_caption_table")
    public abstract List<CommonCaption> getAllCaption();

    @Query("select * from common_caption_table where _saved = :saved")
    public abstract List<CommonCaption> getAllCaptionBySaved(boolean saved);

    @Query("select * from common_caption_table where (_saved = :saved and _category_type = :categoryType)")
    public abstract List<CommonCaption> getCaptionBySavedAndCategoryType(int categoryType, boolean saved);

    @RawQuery
    public abstract List<CommonCaption> searchByContainingContent(SimpleSQLiteQuery sqLiteQuery);
}