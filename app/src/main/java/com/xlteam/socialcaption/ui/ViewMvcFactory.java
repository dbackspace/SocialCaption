package com.xlteam.socialcaption.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;


public class ViewMvcFactory {
    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

//    public PlayViewMvc getPlayViewMvc(@Nullable ViewGroup parent) {
//        return new PlayViewMvcImpl(mLayoutInflater, parent);
//    }
//
//    public MenuViewMvc getMenuViewMvc(@Nullable ViewGroup parent) {
//        return new MenuViewMvcImpl(mLayoutInflater, parent);
//    }
}
