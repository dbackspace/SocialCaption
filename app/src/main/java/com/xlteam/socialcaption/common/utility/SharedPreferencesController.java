package com.xlteam.socialcaption.common.utility;

import android.content.Context;

import java.util.ArrayList;

public class SharedPreferencesController {

    public static ArrayList<String> getCategoryList(Context context) {
        return PrefUtils.getStringArrayList(context, Constant.FIREBASE, Constant.FIREBASE_CATEGORY_LIST);
    }

}
