package com.xlteam.socialcaption.external.utility;

import android.os.Build;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchQueryUtils {
    private static final String TAG = "SearchQueryUtils";

    static final String[] SPECIAL_CHARS = {"*", "+", "^", "$", "[", "]", "{", "}", "(", ")", "."};

    public static String getSelectionArgs(String keyword) {
        StringBuilder params = new StringBuilder();
        if (!TextUtils.isEmpty(keyword)) {
            String[] selectionArray = convertRegexString(keyword).split("\\s+");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String[] selectionArgs = Arrays.stream(selectionArray)
                        .map(args -> "*" + args + "* ")
                        .collect(Collectors.toList())
                        .toArray(new String[0]);
                for (String args : selectionArgs) {
                    params.append(args);
                }
            }
        }
        return params.toString();
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
