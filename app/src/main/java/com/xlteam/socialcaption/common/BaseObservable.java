package com.xlteam.socialcaption.common;

import android.content.Context;

import com.xlteam.socialcaption.ui.common.views.BaseViewMvc;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BaseObservable <LISTENER_CLASS> extends BaseViewMvc {
    // thread-safe set of listeners
    private final Set<LISTENER_CLASS> mListener = Collections.newSetFromMap(
            new ConcurrentHashMap<LISTENER_CLASS, Boolean>(1));

    public final void registerListener(LISTENER_CLASS listener) {
        mListener.add(listener);
    }

    public final void unregisterListener(LISTENER_CLASS listener) {
        mListener.remove(listener);
    }

    protected final Set<LISTENER_CLASS> getListeners(){
        return Collections.unmodifiableSet(mListener);
    }

    protected Context getApplicationContext(){
        return getRootView().getContext();
    }
}
