package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.ViewMvcFactory;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

import java.util.List;

public class HomePageViewMvcImpl extends BaseObservableViewMvc<HomePageViewMvc.Listener> implements
        HomePageViewMvc {
    private final RecyclerView rvCategoryItem;
    private final ImageView imgSearch;
    private CategoryItemAdapter adapter;

    public HomePageViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        View view = layoutInflater.inflate(R.layout.fragment_home_page, parent, false);
        setRootView(view);

        // init view
        rvCategoryItem = findViewById(R.id.rv_category_item);
        imgSearch = findViewById(R.id.image_search);

        // set layout for recyclerview
        rvCategoryItem.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CategoryItemAdapter(getContext());
        rvCategoryItem.setAdapter(adapter);
    }

    @Override
    public void bindCategory(Context context, int numberCategory, List<Caption> captions) {
        adapter.binCaptionList(numberCategory, captions);

    }
}
