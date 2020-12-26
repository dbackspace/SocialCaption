package com.xlteam.socialcaption.ui.home;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.ViewMvcFactory;
import com.xlteam.socialcaption.ui.home.itemcategory.ItemCategoryViewMvc;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemRecyclerAdapter extends RecyclerView.Adapter<CategoryItemRecyclerAdapter.CategoryViewHolder>
        implements ItemCategoryViewMvc.Listener {

    @Override
    public void onTvShowMoreClicked(String categoryId) {

    }

    public interface Listener {
        void onTvShowMoreClicked();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryViewMvc mViewMvc;

        public CategoryViewHolder(ItemCategoryViewMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewMvc = viewMvc;
        }
    }

    private final Listener mListener;
    private final ViewMvcFactory viewMvcFactory;
    private List<ItemCategory> listCategory = new ArrayList<>();

    public CategoryItemRecyclerAdapter(Listener listener, ViewMvcFactory viewMvcFactory) {
        mListener = listener;
        this.viewMvcFactory = viewMvcFactory;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryViewMvc viewMvc = viewMvcFactory.getItemCategoryViewMvc(parent);
        viewMvc.registerListener(this);
        return new CategoryViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.mViewMvc.bindItemCategory(listCategory.get(position));
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public void bindCategories(List<ItemCategory> categories) {
        listCategory = new ArrayList<>(categories);
        notifyDataSetChanged();
    }
}
