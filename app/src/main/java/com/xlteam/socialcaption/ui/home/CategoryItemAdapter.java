package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.common.utility.Constant;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.category.CategoryActivity;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {
    private final ItemCategory[] mItemCategories;
    private Context mContext;

    public CategoryItemAdapter(Context context) {
        mContext = context;
        mItemCategories = new ItemCategory[5];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCategory itemCategory = mItemCategories[position];
        if (itemCategory != null) {
            holder.progressLoading.setVisibility(View.GONE);
            holder.rvCaptions.setVisibility(View.VISIBLE);
            holder.rvCaptions.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            CaptionAdapter adapter = new CaptionAdapter(mContext, itemCategory.getCaptions());
            holder.rvCaptions.setAdapter(adapter);
        }
        holder.tvCategoryName.setText(Constant.CATEGORY_ARRAY[position]);
        holder.tvShowMore.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, CategoryActivity.class);
            intent.putExtra(Constant.EXTRA_CATEGORY_NUMBER, position);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return Constant.CATEGORY_ARRAY.length;
    }

    public void binCaptionList(int position, List<Caption> captions) {
        mItemCategories[position] = new ItemCategory(Constant.CATEGORY_ARRAY[position], captions);
        notifyItemChanged(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCategoryName;
        private final TextView tvShowMore;
        private final RecyclerView rvCaptions;
        private final ProgressBar progressLoading;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            tvShowMore = itemView.findViewById(R.id.tv_show_more);
            rvCaptions = itemView.findViewById(R.id.rv_all_captions_of_a_category);
            progressLoading = itemView.findViewById(R.id.progress_loading);
        }
    }
}
