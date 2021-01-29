package com.xlteam.socialcaption.external.utility.animation;

import android.content.Context;

import androidx.fragment.app.FragmentTransaction;

import com.xlteam.socialcaption.R;

public class ViManager {
    private static class ViManagerHolder {
        private static final ViManager INSTANCE = new ViManager();
    }

    public static ViManager getInstance() {
        return ViManagerHolder.INSTANCE;
    }

    private ViManager() {
    }

    public void setFragmentDefaultAnimation(Context context, FragmentTransaction ft) {
        if (context != null) {
            ft.setCustomAnimations(R.anim.custom_fragment_close_enter, R.anim.custom_fragment_close_exit);
        }
    }
}
