package com.xlteam.socialcaption.ui.upload.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;

public class DetailForToolsAdapter extends RecyclerView.Adapter<DetailForToolsAdapter.ViewHolder> {

    int[] mItemBackgrounds;
    DetailForToolsCallback mDetailForToolsCallback;

    public interface DetailForToolsCallback {
        void onBackgroundGradientClicked(int position);
    }

    public DetailForToolsAdapter(int[] itemBackgrounds, DetailForToolsCallback detailForToolsCallback) {
        mItemBackgrounds = itemBackgrounds;
        mDetailForToolsCallback = detailForToolsCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background_for_caption, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImgView.setImageResource(mItemBackgrounds[position]);

        holder.mImgView.setOnClickListener((v) -> {
            mDetailForToolsCallback.onBackgroundGradientClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return mItemBackgrounds.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgView = itemView.findViewById(R.id.img_background_for_caption_post);
        }
    }
}
