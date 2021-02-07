package com.xlteam.socialcaption.external.utility.thread;

import android.graphics.Bitmap;
import android.util.LruCache;

import timber.log.Timber;

public class BitmapLruCache {
    private static LruCache<String, Bitmap> cacheBitmaps;
    private BitmapLruCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/4th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 4;
        Timber.d("max memory " + maxMemory + " cache size " + cacheSize);

        // LruCache takes key-value pair in constructor
        // key is the string to refer bitmap
        // value is the stored bitmap
        cacheBitmaps = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    private static class Holder {
        public static final BitmapLruCache INSTANCE = new BitmapLruCache();
    }

    public static BitmapLruCache getInstance() {
        return BitmapLruCache.Holder.INSTANCE;
    }

    public LruCache<String, Bitmap> getLru() {
        return cacheBitmaps;
    }

    public void saveBitmapToCache(String key, Bitmap bitmap){
        cacheBitmaps.put(key, bitmap);
    }

    public Bitmap retrieveBitmapFromCache(String key){
        Bitmap bitmap = cacheBitmaps.get(key);
        return bitmap;
    }
}
