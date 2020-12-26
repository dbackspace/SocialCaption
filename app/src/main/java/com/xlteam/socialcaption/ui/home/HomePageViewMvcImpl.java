package com.xlteam.socialcaption.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.ViewMvcFactory;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

import java.util.List;

public class HomePageViewMvcImpl extends BaseObservableViewMvc<HomePageViewMvc.Listener> implements
        HomePageViewMvc,
        CategoryItemRecyclerAdapter.Listener {
    private final RecyclerView rvCategoryItem;
    private final SearchView searchViewButton;
    private final CategoryItemRecyclerAdapter adapter;

    public HomePageViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        View view = layoutInflater.inflate(R.layout.fragment_home_page, parent, false);
        setRootView(view);

        // init view
        rvCategoryItem = findViewById(R.id.rv_category_item);
        searchViewButton = findViewById(R.id.searchView);

        // set layout for recyclerview
        rvCategoryItem.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CategoryItemRecyclerAdapter(this, viewMvcFactory);
        rvCategoryItem.setAdapter(adapter);

    }

    @Override
    public void bindCategories(List<ItemCategory> listCategory) {
        adapter.bindCategories(listCategory);
    }

    @Override
    public void onTvShowMoreClicked() {

    }
}