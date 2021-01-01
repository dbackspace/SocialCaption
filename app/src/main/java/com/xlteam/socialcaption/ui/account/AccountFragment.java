package com.xlteam.socialcaption.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.ui.common.controllers.BaseFragment;

public class AccountFragment extends BaseFragment implements AccountViewMvc.Listener {
    private Context mContext;
    private AccountViewMvc mViewMvc;
    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getControllerCompositionRoot().getViewMvcFactory().getAccountViewMvc(null);
        //code ui
        return mViewMvc.getRootView();
    }

    @Override
    public void onProfileClicked() {

    }

    @Override
    public void onPostedClicked() {

    }

    @Override
    public void onMarkedPostClicked() {

    }

    @Override
    public void onSettingClicked() {

    }

    @Override
    public void onLogOutClicked() {

    }
}
