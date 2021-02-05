package com.xlteam.socialcaption.external.utility.utils;

import android.os.Build;
import android.widget.TextView;

import com.xlteam.socialcaption.ui.edit.ItemText;

import java.util.HashMap;
import java.util.Map;

public class MapViewUtils {
    private static final Map<Integer, ItemText> mapViews = new HashMap<>();

    private MapViewUtils() {
    }

    private static class Holder {
        public static MapViewUtils INSTANCE = new MapViewUtils();
    }

    public static MapViewUtils getInstance() {
        return Holder.INSTANCE;
    }

    public void put(TextView textView, ItemText itemText) {
        if (textView != null && !itemText.isEmpty()) {
            int viewId = textView.getId();
            if (!mapViews.containsKey(viewId)) {
                mapViews.put(viewId, itemText);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mapViews.replace(viewId, itemText);
                }
            }
        }
    }

    public ItemText get(TextView textView) {
        ItemText itemText = null;
        if (textView != null) {
            int viewId = textView.getId();
            itemText = mapViews.get(viewId);
        }
        return itemText;
    }
}
