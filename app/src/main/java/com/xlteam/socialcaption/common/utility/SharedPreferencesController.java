package com.xlteam.socialcaption.common.utility;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.xlteam.socialcaption.model.Category;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesController {

    public static ArrayList<Category> getCategoryList(Context context) {
        Gson gson = new Gson();
        String json = PrefUtils.getString(context, Constant.FIREBASE, Constant.FIREBASE_CATEGORY_LIST, "");
        if (TextUtils.isEmpty(json)) return new ArrayList<>();
        Type type = new TypeToken<List<Category>>() {
        }.getType();
        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            return new ArrayList<>();
        }
    }

    public static void putCategoryList(Context context, ArrayList<Category> categoryList) {
        Gson gson = new Gson();
        String json = gson.toJson(categoryList);
        PrefUtils.putString(context, Constant.FIREBASE, Constant.FIREBASE_CATEGORY_LIST, json);
    }
}
