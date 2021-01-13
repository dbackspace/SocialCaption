package com.xlteam.socialcaption.external.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.xlteam.socialcaption.external.dao.CommonCaptionDAO;
import com.xlteam.socialcaption.external.dao.UserCaptionDAO;
import com.xlteam.socialcaption.model.CommonCaption;
import com.xlteam.socialcaption.model.UserCaption;

@Database(entities = {CommonCaption.class, UserCaption.class}, version = 6, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static final String TAG = "MyDatabase";
    private static volatile MyDatabase sInstance;

    public abstract CommonCaptionDAO commonCaptionDAO();

    public abstract UserCaptionDAO userCaptionDAO();

    public static MyDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (MyDatabase.class) {
                if (sInstance == null) {
                    Log.v(TAG, "init database");
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
