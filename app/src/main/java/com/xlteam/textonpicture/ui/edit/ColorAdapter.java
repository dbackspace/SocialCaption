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
    private boolean isShowNoColor = false; // item remove màu xuất hiện khi set màu cho background và shadow

    public interface ColorSelectCallback {
        void selectColor(int color);

        void setNoColor();

        void pickColor();
    }

    public ColorAdapter(ColorSelectCallback callBack) {
        mColors = ColorDataSource.getInstance().getAllData();
        mCallback = callBack;
    }

    @NonNull
    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pick_color, parent, false);
            return new ViewHolder(v);
        } else if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_color, parent, false);
            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        if (position == 1 && isShowNoColor) return 1;
        return 2;
    }

    public void setNoColor(boolean noColor) {
        isShowNoColor = noColor;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            holder.itemView.setOnClickListener(v -> mCallback.pickColor());
        } else if (holder.getItemViewType() == 1) {
            holder.itemView.setOnClickListener(v -> mCallback.setNoColor());
        } else {
            int index;
            if (isShowNoColor) index = position - 2;
            else index = position - 1;
            String color = mColors.get(index);
            Utility.setColorForView(holder.itemView, "#FF" + color);
            holder.itemView.setOnClickListener(v -> mCallback.selectColor(index));
        }
    }

    @Override
    public int getItemCount() {
        if (isShowNoColor) return mColors.size() + 2;
        else return mColors.size() + 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
