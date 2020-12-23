package com.xlteam.socialcaption.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.xlteam.socialcaption.ui.common.bottomnavigation.BottomNavViewMvc;
import com.xlteam.socialcaption.ui.common.bottomnavigation.BottomNavViewMvcImpl;
import com.xlteam.socialcaption.ui.home.HomePageViewMvc;
import com.xlteam.socialcaption.ui.home.HomePageViewMvcImpl;
import com.xlteam.socialcaption.ui.home.aitemcategory.ItemCategoryViewMvc;
import com.xlteam.socialcaption.ui.home.aitemcategory.ItemCategoryViewMvcImpl;


public class ViewMvcFactory {
    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public BottomNavViewMvc getBottomNavViewMvc(@Nullable ViewGroup parent) {
        return new BottomNavViewMvcImpl(mLayoutInflater, parent);
    }

    public ItemCategoryViewMvc getItemCategoryViewMvc(@Nullable ViewGroup parent) {
        return new ItemCategoryViewMvcImpl(mLayoutInflater, parent);
    }

    public HomePageViewMvc getHomePageViewMvc(@Nullable ViewGroup parent) {
        return new HomePageViewMvcImpl(mLayoutInflater, parent, this);
    }

//    public PlayViewMvc getPlayViewMvc(@Nullable ViewGroup parent) {
//        return new PlayViewMvcImpl(mLayoutInflater, parent);
//    }
//
//    public MenuViewMvc getMenuViewMvc(@Nullable ViewGroup parent) {
//        return new MenuViewMvcImpl(mLayoutInflater, parent);
//    }
}
