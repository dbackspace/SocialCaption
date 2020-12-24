package com.xlteam.socialcaption.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.common.controllers.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends BaseFragment implements HomePageViewMvc.Listener {

    public static HomePageFragment newInstance() {
        // uncomment if need to putParchelable
        // Bundle args = new Bundle();
        // args.putParcelable(ARG_HOME_PAGE_ID, "id");
        HomePageFragment fragment = new HomePageFragment();
        // fragment.setArguments(args);
        return fragment;
    }

    private HomePageViewMvc mViewMvc;
    private List<ItemCategory> mCategories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init use case, networking, screen navigator,...

        // fake data
        mCategories = new ArrayList<>();
        List<Caption> mCaptions1 = new ArrayList<>();
        mCaptions1.add(new Caption("HelloEveryone", "/sdcard/Download/"));
        mCaptions1.add(new Caption("Hello", "/sdcard/Download/"));
        mCaptions1.add(new Caption("HelloEvery", "/sdcard/Download/"));
        mCategories.add(new ItemCategory("Funny", mCaptions1));

        List<Caption> mCaptions2 = new ArrayList<>();
        mCaptions2.add(new Caption("HelloEveryone", "/sdcard/Download/"));
        mCaptions2.add(new Caption("Hello", "/sdcard/Download/"));
        mCaptions2.add(new Caption("HelloEvery", "/sdcard/Download/"));
        mCategories.add(new ItemCategory("Love", mCaptions2));

        List<Caption> mCaptions3 = new ArrayList<>();
        mCaptions3.add(new Caption("HelloEveryone", "/sdcard/Download/"));
        mCaptions3.add(new Caption("Hello", "/sdcard/Download/"));
        mCaptions3.add(new Caption("HelloEvery", "/sdcard/Download/"));
        mCategories.add(new ItemCategory("Friends", mCaptions3));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getControllerCompositionRoot().getViewMvcFactory().getHomePageViewMvc(null);

        // This is bind after get response data from Firebase
        mViewMvc.bindCategories(mCategories);
        return mViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save state after rotate screen, dark mode, ....
    }

    @Override
    public void onSearchViewClicked() {

    }

    @Override
    public void onViewPagerItemClicked() {

    }
}
