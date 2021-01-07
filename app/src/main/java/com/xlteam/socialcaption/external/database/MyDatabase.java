package com.xlteam.socialcaption.external.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.xlteam.socialcaption.external.dao.CaptionDAO;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.model.CaptionFts;

@Database(entities = {Caption.class, CaptionFts.class}, version = 2, exportSchema = false)
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
