package com.xlteam.socialcaption.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.xlteam.socialcaption.ui.category.CategoryViewMvc;
import com.xlteam.socialcaption.ui.category.CategoryViewMvcImpl;
import com.xlteam.socialcaption.ui.common.bottomnavigation.BottomNavViewMvc;
import com.xlteam.socialcaption.ui.common.bottomnavigation.BottomNavViewMvcImpl;
import com.xlteam.socialcaption.ui.home.HomePageViewMvc;
import com.xlteam.socialcaption.ui.home.HomePageViewMvcImpl;
import com.xlteam.socialcaption.ui.upload.DialogUploadCaptionsViewMvc;
import com.xlteam.socialcaption.ui.upload.DialogUploadCaptionsViewMvcImpl;

public class ViewMvcFactory {
    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public BottomNavViewMvc getBottomNavViewMvc(@Nullable ViewGroup parent) {
        return new BottomNavViewMvcImpl(mLayoutInflater, parent);
    }

    public HomePageViewMvc getHomePageViewMvc(@Nullable ViewGroup parent) {
        return new HomePageViewMvcImpl(mLayoutInflater, parent);
    }

    public DialogUploadCaptionsViewMvc getDialogUploadCaptionsViewMvc(@Nullable ViewGroup parent) {
        return new DialogUploadCaptionsViewMvcImpl(mLayoutInflater, parent);
    }

    public CategoryViewMvc getCategoryViewMvc(@Nullable ViewGroup parent) {
        return new CategoryViewMvcImpl(mLayoutInflater, parent);
    }
}
