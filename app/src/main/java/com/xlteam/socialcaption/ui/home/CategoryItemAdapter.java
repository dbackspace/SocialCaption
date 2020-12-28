package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.common.utility.Constant;
import com.xlteam.socialcaption.model.ItemCategory;
import com.xlteam.socialcaption.ui.category.CategoryActivity;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {
    private final List<ItemCategory> mItemCategories;
    private final Context mContext;

    public CategoryItemAdapter(Context context, List<ItemCategory> itemCategories) {
        mContext = context;
        mItemCategories = itemCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCategory itemCategory = mItemCategories.get(position);
        holder.rvCaptions.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        CaptionAdapter adapter = new CaptionAdapter(mContext, itemCategory.getCaptions());
        holder.rvCaptions.setAdapter(adapter);
        holder.tvCategoryName.setText(itemCategory.getCategory().getCategoryName());
        holder.tvShowMore.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, CategoryActivity.class);
            intent.putExtra(Constant.EXTRA_CATEGORY_NUMBER, position);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mItemCategories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCategoryName;
        private final TextView tvShowMore;
        private final RecyclerView rvCaptions;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            tvShowMore = itemView.findViewById(R.id.tv_show_more);
            rvCaptions = itemView.findViewById(R.id.rv_all_captions_of_a_category);
        }
    }
}
