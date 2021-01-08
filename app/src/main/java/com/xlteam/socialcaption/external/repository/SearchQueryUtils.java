package com.xlteam.socialcaption.external.repository;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchQueryUtils {
    private static final String TAG = "SearchQueryUtils";

    static final String[] SPECIAL_CHARS = {"*", "+", "^", "$", "[", "]", "{", "}", "(", ")", "."};

    public static String[] getSelectionArgs(String keyword) {
        String[] selectionArgs = null;
        if (!TextUtils.isEmpty(keyword)) {
            String[] selectionArray = convertRegexString(keyword).split("\\s+");
//            List<String> selectionList = new ArrayList<>();
//            for (String selection : selectionArray) {
//                selectionList.add("(?i).*[/][^/]*" + selection + "[^/]*");
//            }
//            selectionArgs = selectionList.toArray(new String[0]);
        }
        return selectionArgs;
    }

    private static String convertRegexString(String keyword) {
        for (String c : SPECIAL_CHARS) {
            if (keyword.contains(c)) {
                keyword = keyword.replace(c, "\\" + c);
            }
        }
        return keyword;
    }
}
