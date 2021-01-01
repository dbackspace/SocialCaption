package com.xlteam.socialcaption.ui.upload.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;

public class ToolColorAlignFontAdapter extends RecyclerView.Adapter<ToolColorAlignFontAdapter.ViewHolder> {
    int[] listTools;
    private ToolColorAlignFontCallback callback;

    public interface ToolColorAlignFontCallback {
        void setDetailForTools(int position);
    }

    public ToolColorAlignFontAdapter(int[] tools, ToolColorAlignFontCallback callback) {
        listTools = tools;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background_for_caption, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImgView.setImageResource(listTools[position]);
        holder.mImgView.setOnClickListener(v -> {
            callback.setDetailForTools(position);
        });
        if (position == 0) {
            holder.mImgView.performClick();
        }
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
