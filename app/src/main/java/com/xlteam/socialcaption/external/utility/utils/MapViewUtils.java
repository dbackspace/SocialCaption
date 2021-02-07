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

    public void put(View textView, int id, ItemText itemText) {
        if (textView != null) {
            if (!mapViews.containsKey(id)) {
                mapViews.put(id, itemText);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mapViews.replace(id, itemText);
                }
            }
            Timber.v("put view item: Id = " + id + ", item = " + itemText);
        }
    }

    public ItemText get(View textView) {
        ItemText itemText = null;
        if (textView != null) {
            int id = (int) textView.getTag();
            itemText = mapViews.get(id);
        }
        Timber.v("get item by view: item = " + itemText);
        return itemText;
    }

    public void remove(View textView) {
        int id = (int) textView.getTag();
        if (mapViews.containsKey(id)) {
            Timber.v("remove view: id = " + id);
            mapViews.remove(id);
        }
    }
}
