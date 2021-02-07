package com.xlteam.socialcaption.ui.edit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.datasource.ColorDataSource;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.model.Color;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private ArrayList<Color> mColors;
    private ColorSelectCallback mCallback;
    private int mNumberSelect;

    public interface ColorSelectCallback {
        void selectColor(int color);
    }

    public ColorAdapter(ColorSelectCallback callBack) {
        mColors = ColorDataSource.getInstance().getAllData();
        mCallback = callBack;
    }

    @NonNull
    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ViewHolder holder, int position) {
        Color color = mColors.get(position);
        Utility.setColorForView(holder.itemView, color.getColor());
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        View mItemView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
        }
    }
}
