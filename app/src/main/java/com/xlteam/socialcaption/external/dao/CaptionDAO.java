package com.xlteam.socialcaption.external.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.xlteam.socialcaption.model.Caption;

import java.util.List;

@Dao
public interface CaptionDAO {
    @Insert
    public void insertCaption(Caption caption);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateCaption(Caption newCaption);

    @Query("select * from caption_table where _category_type like :categoryType")
    public List<Caption> getCaptionByCategoryType(int categoryType);

    @Query("select * from caption_table")
    public List<Caption> getAllCaption();

    @Query("select * from caption_table where _saved like :saved")
    public List<Caption> getCaptionBySaved(boolean saved);

    @Query("select * from caption_table where _saved like :saved and _category_type like :categoryType")
    public List<Caption> getCaptionBySavedAndCategoryType(int categoryType, boolean saved);

    // TODO: apply query for search
}
