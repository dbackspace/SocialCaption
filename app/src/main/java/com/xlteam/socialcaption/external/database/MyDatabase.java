package com.xlteam.socialcaption.external.database;

import android.content.Context;

import com.xlteam.socialcaption.external.dao.CommonCaptionDAO;
import com.xlteam.socialcaption.external.dao.UserCaptionDAO;
import com.xlteam.socialcaption.model.CommonCaption;
import com.xlteam.socialcaption.model.UserCaption;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import timber.log.Timber;

@Database(entities = {CommonCaption.class, UserCaption.class}, version = 6, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static volatile MyDatabase sInstance;

    public abstract CommonCaptionDAO commonCaptionDAO();

    public abstract UserCaptionDAO userCaptionDAO();

    public static MyDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (MyDatabase.class) {
                if (sInstance == null) {
                    Timber.v("init database");
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "MyDatabase.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sInstance;
    }
}
