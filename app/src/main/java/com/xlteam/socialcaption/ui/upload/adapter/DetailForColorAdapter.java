package com.xlteam.socialcaption.ui.upload.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.common.datasource.ColorDataSource;
import com.xlteam.socialcaption.common.utility.UiUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailForColorAdapter extends RecyclerView.Adapter<DetailForColorAdapter.ViewHolder>{
    private ArrayList<String> mColors;
    private ColorSelectCallback mCallback;
    private int mNumberSelect;

    public interface ColorSelectCallback {
        void selectColor(int color);
    }

    public DetailForColorAdapter(ColorSelectCallback callBack) {
        mColors = ColorDataSource.getInstance().getAllDataMini();
        mCallback = callBack;
        mNumberSelect = 0; // mặc định màu ban đầu là đen
    }

    @NonNull
    @Override
    public DetailForColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_mini, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailForColorAdapter.ViewHolder holder, int position) {
        String color = mColors.get(position);
        UiUtils.setColorForView(holder.itemView, color);
        holder.itemView.setOnClickListener(v -> {
            mNumberSelect = position;
            mCallback.selectColor(position);
            notifyDataSetChanged();
        });
    }

    public void setNumberSelect(int number) {
        mNumberSelect = number;
    }

    @Override
    public int getItemCount() {
        return mColors.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        ImageView imgSelected;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            imgSelected = itemView.findViewById(R.id.imgSelect);
        }
    }
}
