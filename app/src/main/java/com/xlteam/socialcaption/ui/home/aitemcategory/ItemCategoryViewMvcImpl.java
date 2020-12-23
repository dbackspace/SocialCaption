package com.xlteam.socialcaption.ui.home.aitemcategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

public class ItemCategoryViewMvcImpl extends BaseObservableViewMvc<ItemCategoryViewMvc.Listener> implements ItemCategoryViewMvc {
    private final TextView tvCategoryName;
    private final TextView tvShowMore;
    private final RecyclerView rvCaptions;

    public ItemCategoryViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        setRootView(layoutInflater.inflate(R.layout.layout_a_category_item, parent, false));
        tvCategoryName = findViewById(R.id.tv_category_name);
        tvShowMore = findViewById(R.id.tv_show_more);
        rvCaptions = findViewById(R.id.rv_all_captions_of_a_category);

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
        tvCategoryName.setText(itemCategory.getHeaderName());
        tvShowMore.setText("Show all captions");

    }
}
