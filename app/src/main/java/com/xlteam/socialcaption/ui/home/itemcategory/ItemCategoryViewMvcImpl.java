package com.xlteam.socialcaption.ui.home.itemcategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.ViewMvcFactory;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

import java.util.List;

public class ItemCategoryViewMvcImpl extends BaseObservableViewMvc<ItemCategoryViewMvc.Listener> implements ItemCategoryViewMvc, ItemCaptionRecyclerAdapter.Listener {
    private final TextView tvCategoryName;
    private final TextView tvShowMore;
    private final RecyclerView rvCaptions;
    private final ItemCaptionRecyclerAdapter adapter;
    private List<Caption> mCaptions;

    public ItemCategoryViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(layoutInflater.inflate(R.layout.layout_a_category_item, parent, false));
        tvCategoryName = findViewById(R.id.tv_category_name);
        tvShowMore = findViewById(R.id.tv_show_more);
        rvCaptions = findViewById(R.id.rv_all_captions_of_a_category);

        // set layout for recycler view captions list
        rvCaptions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new ItemCaptionRecyclerAdapter(this, viewMvcFactory);
        rvCaptions.setAdapter(adapter);

        tvShowMore.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (Listener listener : getListeners()) {
                            listener.onTvShowMoreClicked("");
                        }
                    }
                }
        );

    }

    @Override
    public void bindItemCategory(ItemCategory itemCategory) {
        mCaptions = itemCategory.getCaptions();
        tvCategoryName.setText(itemCategory.getCategory().getCategoryName());
        adapter.bindCaptions(mCaptions);
    }

    @Override
    public void onCaptionClicked(Caption caption) {

    }
}
