package com.xlteam.socialcaption.common.utility;

import android.content.Context;

import com.xlteam.socialcaption.model.Category;

import java.util.ArrayList;

public class SharedPreferencesController {

    public static ArrayList<String> getCategoryList(Context context) {
        return PrefUtils.getStringArrayList(context, Constant.FIREBASE, Constant.FIREBASE_CATEGORY_LIST);
    }

    public static void putCategoryList(Context context, ArrayList<Category> categoryList) {
        ArrayList<String> categoryString = new ArrayList<>();
        for (Category category : categoryList) {
            categoryString.add(category.getCategoryName());
        }
        PrefUtils.putStringArrayList(context, Constant.FIREBASE, Constant.FIREBASE_CATEGORY_LIST, categoryString);
    }
}
