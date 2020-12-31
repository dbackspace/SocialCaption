package com.xlteam.socialcaption.ui.category;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.common.utility.Constant;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryNameListAdapter extends RecyclerView.Adapter<CategoryNameListAdapter.ViewHolder> {

    private ArrayList<String> mCategoryNames;
    private int numberSelect;
    private Context mContext;

    public CategoryNameListAdapter(Context context) {
        mContext = context;
        mCategoryNames = new ArrayList<>(Arrays.asList(Constant.CATEGORY_ARRAY));
        mCategoryNames.add(0, "Tất cả");
        mCategoryNames.add("Khác");
        Log.d("binh.ngk ", "size = " + mCategoryNames.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_name, parent, false);
        return new CategoryNameListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCategoryName.setText(mCategoryNames.get(position));
        if (position == numberSelect) {
            holder.tvCategoryName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
            holder.tvCategoryName.setTextColor(mContext.getColor(R.color.color_3cc2f5_legend));
        } else {
            holder.tvCategoryName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            holder.tvCategoryName.setTextColor(mContext.getColor(R.color.color_6E));
        }
        holder.tvCategoryName.setOnClickListener(view -> {
            numberSelect = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryNames.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCategoryName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
        }
    }
}
