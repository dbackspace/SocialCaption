package com.xlteam.socialcaption.external.dao;

import com.xlteam.socialcaption.model.UserCaption;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserCaptionDAO {
    @Insert
    void insetUserCaption(UserCaption userCaption);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUserCaption(UserCaption userCaption);

    @Query("select * from user_caption_table")
    List<UserCaption> getAllUserCaption();
}
