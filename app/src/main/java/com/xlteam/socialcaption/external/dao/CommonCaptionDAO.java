package com.xlteam.socialcaption.external.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

@Dao
public interface CommonCaptionDAO {
    @Insert
    void insertCaption(CommonCaption caption);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCaption(CommonCaption newCaption);

    @Query("select * from common_caption_table where _category_type like :categoryType")
    List<CommonCaption> getCaptionByCategoryType(int categoryType);

    @Query("select * from common_caption_table")
    List<CommonCaption> getAllCaption();

    @Query("select * from common_caption_table where _saved like :saved")
    List<CommonCaption> getCaptionBySaved(boolean saved);

    @Query("select * from common_caption_table where (_saved like :saved and _category_type like :categoryType)")
    List<CommonCaption> getCaptionBySavedAndCategoryType(int categoryType, boolean saved);

    @Query("select distinct * from common_caption_table inner join common_caption_fts_table on common_caption_fts_table._content = common_caption_table._content where common_caption_fts_table match '' || :content || ''")
    List<CommonCaption> searchByContainingContent(String content);
}