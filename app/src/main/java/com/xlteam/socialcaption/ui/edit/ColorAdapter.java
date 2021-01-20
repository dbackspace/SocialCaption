package com.xlteam.socialcaption.ui.edit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.datasource.ColorDataSource;
import com.xlteam.socialcaption.external.utility.Utility;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private ArrayList<String> mColors;
    private ColorSelectCallback mCallback;
    private int mNumberSelect;

    public interface ColorSelectCallback {
        void selectColor(int color);
    }

    public ColorAdapter(ColorSelectCallback callBack) {
        mColors = ColorDataSource.getInstance().getAllDataMini();
        mCallback = callBack;
    }

    @NonNull
    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_mini, parent, false);
        return new ColorAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ViewHolder holder, int position) {
        String color = mColors.get(position);
        Utility.setColorForView(holder.itemView, color);
        holder.itemView.setOnClickListener(v -> {
            mCallback.selectColor(position);
            mNumberSelect = position;
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

    class ViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        ImageView imgSelected;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            imgSelected = itemView.findViewById(R.id.imgSelect);
        }
    }
}
