package com.xlteam.socialcaption.common.dependencyinjection;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentActivity;

import com.xlteam.socialcaption.ui.ViewMvcFactory;
import com.xlteam.socialcaption.ui.common.screensnavigator.ScreensNavigator;

public class ControllerCompositionRoot {
    private final ActivityCompositionRoot mActivityCompositionRoot;

    public ControllerCompositionRoot(ActivityCompositionRoot activityCompositionRoot) {
        mActivityCompositionRoot = activityCompositionRoot;
    }

    public FragmentActivity getActivity() {
        return mActivityCompositionRoot.getActivity();
    }

    private Context getContext() {
        return getActivity();
    }


//    public PlayActivityController getPlayActivityController() {
//        return new PlayActivityController(getDbController());
//    }
//
//    public MenuActivityController getMenuActivityController() {
//        return new MenuActivityController();
//    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater());
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(getContext());
    }

    public ScreensNavigator getScreensNavigator() {
        return mActivityCompositionRoot.getScreensNavigator();
    }

//    private DBController getDbController() {
//        return DBController.getInstance(getContext());
//    }

//    public CustomKeyboard getCustomKeyboard() {
//        return new CustomKeyboard(getActivity());
//    }
}
