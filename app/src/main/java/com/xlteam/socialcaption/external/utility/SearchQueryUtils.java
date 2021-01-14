package com.xlteam.socialcaption.external.utility;

import android.os.Build;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SearchQueryUtils {
    private static final String TAG = "SearchQueryUtils";

    static final String[] SPECIAL_CHARS = {"*", "+", "^", "$", "[", "]", "{", "}", "(", ")", "."};

    public static StringBuilder getSelectionArgs(String keyword) {
        StringBuilder query = new StringBuilder();
        if (!TextUtils.isEmpty(keyword)) {
            query = new StringBuilder("select * from common_caption_table where ");
            String[] selectionArray = convertRegexString(keyword).split("\\s+");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String[] selectionArgs = Arrays.stream(selectionArray)
                        .map(x -> "'%" + x + "%'")
                        .collect(Collectors.toList())
                        .toArray(new String[0]);
                if (selectionArgs.length > 1) {
                    for (int i = 0; i < selectionArgs.length; ++i) {
                        query.append("(_content like ").append(selectionArgs[i]).append(")");
                        if (i < selectionArgs.length - 1) {
                            query.append(" and ");
                        }
                    }
                } else {
                    query.append("_content like ").append("").append(selectionArgs[0]).append(" ");
                }
            }
        }
        return query;
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
