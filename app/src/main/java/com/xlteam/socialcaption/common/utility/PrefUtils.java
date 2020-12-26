package com.xlteam.socialcaption.common.utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class PrefUtils {
    public static void putInt(Context context, String key, String keyWord, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(keyWord, value);
        editor.apply();
    }

    public static void putString(Context context, String key, String keyWord, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyWord, value);
        editor.apply();
    }

    public static void putFloat(Context context, String key, String keyWord, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(keyWord, value);
        editor.apply();
    }

    public static void putBoolean(Context context, String key, String keyWord, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyWord, value);
        editor.apply();
    }

    public static void putStringArrayList(Context context, String key, String keyWord, ArrayList<String> strings) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(strings);
        editor.putStringSet(keyWord, set);
        editor.apply();
    }

    public static int getInt(Context context, String key, String keyWord, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) return sharedPreferences.getInt(keyWord, defaultValue);
        return defaultValue;
    }

    public static String getString(Context context, String key, String keyWord, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) return sharedPreferences.getString(keyWord, defaultValue);
        return defaultValue;
    }

    public static float getFloat(Context context, String key, String keyWord, float defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) return sharedPreferences.getFloat(keyWord, defaultValue);
        return defaultValue;
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

    public static ArrayList<String> getStringArrayList(Context context, String key, String keyWord) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        if (sharedPreferences != null) {
            Set<String> set = sharedPreferences.getStringSet(keyWord, new HashSet<>());
            return new ArrayList<>(set);
        }
        return new ArrayList<>();
    }
}
