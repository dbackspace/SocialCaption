package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xlteam.socialcaption.common.utility.Constant;
import com.xlteam.socialcaption.firebase.FirebaseController;
import com.xlteam.socialcaption.firebase.FirebaseListener;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.controllers.BaseFragment;

import java.util.ArrayList;

public class HomePageFragment extends BaseFragment implements HomePageViewMvc.Listener {

    public static HomePageFragment newInstance() {
        // uncomment if need to putParchelable
        // Bundle args = new Bundle();
        // args.putParcelable(ARG_HOME_PAGE_ID, "id");
        // fragment.setArguments(args);
        return new HomePageFragment();
    }

    private Context mContext;
    private HomePageViewMvc mViewMvc;
    private FirebaseController mController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mController = new FirebaseController();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init use case, networking, screen navigator,...
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getControllerCompositionRoot().getViewMvcFactory().getHomePageViewMvc(null);

        for (int index = 0; index < Constant.CATEGORY_ARRAY.length; index++) {
            int finalIndex = index;
            mController.getCaptionByCategoryNumber(index, new FirebaseListener<ArrayList<Caption>>() {
                @Override
                public void onResponse(ArrayList<Caption> captions) {
                    mViewMvc.bindCategory(mContext, finalIndex, captions);
                }

                @Override
                public void onError() {

                }
            });
        }
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
