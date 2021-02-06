package com.xlteam.socialcaption.external.utility.utils;

import android.os.Build;
import android.view.View;

import com.xlteam.socialcaption.ui.edit.ItemText;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

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

    public void put(View textView, ItemText itemText) {
        if (textView != null) {
            int viewId = textView.getId();
            if (!mapViews.containsKey(viewId)) {
                mapViews.put(viewId, itemText);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mapViews.replace(viewId, itemText);
                }
            }
            Timber.v("put view item: resId = " + viewId + ", item = " + itemText);
        }
    }

    public ItemText get(View textView) {
        ItemText itemText = null;
        if (textView != null) {
            int viewId = textView.getId();
            itemText = mapViews.get(viewId);
        }
        Timber.v("get item by view: item = " + itemText);
        return itemText;
    }

    public void remove(View textView) {
        int viewId = textView.getId();
        if (mapViews.containsKey(viewId)) {
            Timber.v("remove view: resId = " + viewId);
            mapViews.remove(viewId);
        }
    }
}
