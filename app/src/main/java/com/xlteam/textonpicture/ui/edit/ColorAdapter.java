package com.xlteam.textonpicture.ui.edit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.datasource.ColorDataSource;
import com.xlteam.textonpicture.external.utility.utils.Utility;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private ArrayList<String> mColors;
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        View mItemView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
        }
    }
}