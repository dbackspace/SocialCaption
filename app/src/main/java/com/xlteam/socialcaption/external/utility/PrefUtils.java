package com.xlteam.socialcaption.external.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class PrefUtils {
    public static final String VERSION_DATABASE = "VERSION_DATABASE";
    public static final String VERSION_DATABASE_LOCAL = "VERSION_DATABASE";

    public static void putInt(Context context, String key, String keyWord, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(keyWord, value);
        editor.apply();
    }

    public static int getInt(Context context, String key, String keyWord, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) return sharedPreferences.getInt(keyWord, defaultValue);
        return defaultValue;
    }

    public static void putString(Context context, String key, String keyWord, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyWord, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String keyWord, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) return sharedPreferences.getString(keyWord, defaultValue);
        return defaultValue;
    }

    public static void putFloat(Context context, String key, String keyWord, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(keyWord, value);
        editor.apply();
    }

    public static float getFloat(Context context, String key, String keyWord, float defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) return sharedPreferences.getFloat(keyWord, defaultValue);
        return defaultValue;
    }

    public static void putBoolean(Context context, String key, String keyWord, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyWord, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key, String keyWord) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) return sharedPreferences.getBoolean(keyWord, false);
        return false;
    }

    public static boolean getBoolean(Context context, String key, String keyWord, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) return sharedPreferences.getBoolean(keyWord, defaultValue);
        return false;
    }

    public static void putStringArrayList(Context context, String key, String keyWord, ArrayList<String> strings) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(strings);
        editor.putStringSet(keyWord, set);
        editor.apply();
    }

    public static ArrayList<String> getStringArrayList(Context context, String key, String keyWord) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) {
            Set<String> set = sharedPreferences.getStringSet(keyWord, new HashSet<>());
            if (!Utility.isEmpty(set)) {
                return Lists.newArrayList(set);
            }
        }
        return Lists.newArrayList();
    }

    public static ArrayList<String> getListItemGallery(Context context) {
        return getStringArrayList(context, Constant.PREF_GALLERY, Constant.GALLERY_PATH);
    }

    public static void setListItemGallery(Context context, String savePath) {
        ArrayList<String> temp = Lists.newArrayList(getListItemGallery(context));
        temp.add(savePath);
        putStringArrayList(context, Constant.PREF_GALLERY, Constant.GALLERY_PATH, temp);
    }

    public static int getVersionLocalDatabase(Context context) {
        return getInt(context, VERSION_DATABASE, VERSION_DATABASE_LOCAL, 0);
    }

    public static void setVersionLocalDatabase(Context context, int version) {
        putInt(context, VERSION_DATABASE, VERSION_DATABASE_LOCAL, version);
    }
}
