package com.xlteam.textonpicture.external.utility.thread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import androidx.annotation.Nullable;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import timber.log.Timber;

public class AsyncLayoutInflateManager {
    private Context mContext;
    private AsyncLayoutInflater mInflater;
    private ConcurrentHashMap<Integer, ViewList> mPreLoadedViewList;
    private static volatile AsyncLayoutInflateManager sInstance;

    public static AsyncLayoutInflateManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AsyncLayoutInflateManager.class) {
                if (sInstance == null) {
                    sInstance = new AsyncLayoutInflateManager(context);
                }
            }
        }
        return sInstance;
    }

    public AsyncLayoutInflateManager(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mPreLoadedViewList = new ConcurrentHashMap<>();
        AsyncLayoutInflateThread.getInstance().post(() -> mInflater = new AsyncLayoutInflater(mContext));
    }

    public void onDestroy() {
        if (mPreLoadedViewList != null) {
            mPreLoadedViewList.clear();
            AsyncLayoutInflateThread.getInstance().quit();
        }
    }

    public void doAsyncInflate(int resId, final ViewGroup parent) {
        if (!mPreLoadedViewList.containsKey(resId)) {
            ViewList viewList = new ViewList();
            mPreLoadedViewList.put(resId, viewList);
        }

        submit(() -> mInflater.inflate(resId, parent, createInflaterCallBack()));
    }

    private void submit(Runnable r) {
        if (AsyncLayoutInflateThread.getInstance().getThreadId() == android.os.Process.myTid()) {
            r.run();
        } else {
            AsyncLayoutInflateThread.getInstance().post(r);
        }
    }

    private AsyncLayoutInflater.OnInflateFinishedListener createInflaterCallBack() {
        return (view, resId, parent) -> {
            ViewList viewList = mPreLoadedViewList.get(resId);
            if (parent != null) {
                ThreadExecutor.runOnMainThread(() -> parent.addView(view));
            }
            if (viewList != null) {
                viewList.add(view);
                Timber.d("add viewList : " + resId);
            }
        };
    }

    public View getView(int resId) {
        ViewList viewList = mPreLoadedViewList.get(resId);
        if (viewList == null) {
            Timber.d("getView is null - resId: " + resId);
            return null;
        }

        View view = viewList.remove();
        if (view != null) {
            Timber.d("getView " + resId + ", return view");
        } else {
            Timber.d("getView is null - remove()" + resId);
        }
        return view;
    }

    public View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        View view = getView(layoutId);
        if (view == null) {
            view = inflater.inflate(layoutId, container, false);
        }
        return view;
    }

    private static class ViewList extends ArrayList<View> {
        public View remove() {
            synchronized (this) {
                if (size() > 0) {
                    return remove(0);
                }
                return null;
            }
        }

        public View get() {
            synchronized (this) {
                if (size() > 0) {
                    return get(0);
                }
                return null;
            }
        }

        @Override
        public boolean add(View view) {
            synchronized (this) {
                return super.add(view);
            }
        }
    }
}