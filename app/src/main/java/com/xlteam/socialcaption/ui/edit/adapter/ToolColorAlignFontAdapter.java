package com.xlteam.socialcaption.ui.edit.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;

public class ToolColorAlignFontAdapter extends RecyclerView.Adapter<ToolColorAlignFontAdapter.ViewHolder> {
    int[] listTools;
    private ToolColorAlignFontCallback callback;
    private Context mContext;
    private int toolDefault;

    public interface ToolColorAlignFontCallback {
        void setDetailForTools(int position);
    }

    public ToolColorAlignFontAdapter(int[] tools, ToolColorAlignFontCallback callback) {
        listTools = tools;
        this.callback = callback;
        toolDefault = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background_for_caption, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImgView.setImageResource(listTools[position]);
        Log.e("TAG", toolDefault + " " + position);

        if (toolDefault == position) {
            setTint(holder.mImgView, R.color.black);
            holder.mImgView.setBackground(mContext.getDrawable(R.drawable.bg_font_selected));
        } else {
            setTint(holder.mImgView, R.color.white);
            holder.mImgView.setBackground(mContext.getDrawable(R.drawable.bg_font_unselected));
        }

        holder.mImgView.setOnClickListener(v -> {
            toolDefault = position;
            callback.setDetailForTools(position);

            notifyDataSetChanged();
        });
    }

    private void setTint(ImageView imgView, @ColorRes int colorRes) {
        ImageViewCompat.setImageTintList(imgView, ColorStateList.valueOf(ContextCompat.getColor(mContext, colorRes)));
    }

    @Override
    public int getItemCount() {
        return listTools.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgView = itemView.findViewById(R.id.img_background_for_caption_post);
        }
    }
}
