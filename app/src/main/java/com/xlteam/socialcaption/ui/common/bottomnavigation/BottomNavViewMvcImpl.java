package com.xlteam.socialcaption.ui.common.bottomnavigation;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

public class BottomNavViewMvcImpl extends BaseObservableViewMvc<BottomNavViewMvc.Listener>
        implements BottomNavViewMvc {
    private final FrameLayout mFrameLayout;
    private final BottomNavigationView mBottomNavigationView;

    public BottomNavViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        setRootView(layoutInflater.inflate(R.layout.activity_main, parent, false));
        mFrameLayout = findViewById(R.id.frame_content);
        mBottomNavigationView = findViewById(R.id.bottom_nav_view);

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_home:
                    for(Listener listener: getListeners()){
                        listener.onHomePageClicked();
                    }
                    break;
                case R.id.bottom_nav_category:
                    for(Listener listener: getListeners()){
                        listener.onCategoryClicked();
                    }
                    break;
                case R.id.bottom_nav_upload:
                    for(Listener listener: getListeners()){
                        listener.onUploadClicked();
                    }
                    return false;
                case R.id.bottom_nav_account:
                    for(Listener listener: getListeners()){
                        listener.onAccountClicked();
                    }
                    break;
            }
            return true;
        });
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return mFrameLayout;
    }
}
