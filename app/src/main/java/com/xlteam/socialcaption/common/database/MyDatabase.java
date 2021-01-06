package com.xlteam.socialcaption.common.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.xlteam.socialcaption.common.dao.CaptionDAO;
import com.xlteam.socialcaption.model.Caption;

@Database(entities = {Caption.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static final String TAG = "MyDatabase";
    private static volatile MyDatabase sInstance;

    public abstract CaptionDAO captionDAO();

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
