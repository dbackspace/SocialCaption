package com.xlteam.socialcaption.ui.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

import androidx.annotation.Nullable;

public class AccountViewMvcImpl extends BaseObservableViewMvc<AccountViewMvc.Listener> implements
        AccountViewMvc {

    public AccountViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.fragment_account, parent, false);
        setRootView(view);
    }

    @Override
    public void bindProfile() {

    }
}
