package com.xlteam.socialcaption.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

public class CategoryViewMvcImpl extends BaseObservableViewMvc<CategoryViewMvc.Listener> implements
        CategoryViewMvc {

    public CategoryViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.activity_category, parent, false);
        setRootView(view);
        findViewById(R.id.image_search).setOnClickListener(view1 -> {
            for (Listener listener : getListeners()) {
                listener.onSearchViewClicked();
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(view1 -> {
            for (Listener listener : getListeners()) {
                listener.onBackClicked();
            }
        });
        findViewById(R.id.tv_type_view_list).setOnClickListener(view1 -> {
            for (Listener listener : getListeners()) {
                listener.onTypeCategoryClicked();
            }
        });

    }

    @Override
    public void binCaptions() {

    }
}
