package com.xlteam.socialcaption.ui.upload;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;

public class BackgroundForCaptionAdapter extends RecyclerView.Adapter<BackgroundForCaptionAdapter.ViewHolder> {

    int[] mItemBackgrounds;
    BackgroundClickCallback mBackgroundClickCallback;

    public BackgroundForCaptionAdapter(int[] itemBackgrounds, BackgroundClickCallback backgroundClickCallback) {
        mItemBackgrounds = itemBackgrounds;
        mBackgroundClickCallback = backgroundClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background_for_caption, parent, false);
        return new BackgroundForCaptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImgView.setImageResource(mItemBackgrounds[position]);

        holder.mImgView.setOnClickListener((v) -> {
            mBackgroundClickCallback.onBackgroundGradientClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return mItemBackgrounds.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgView = itemView.findViewById(R.id.img_background_for_caption_post);
        }
    }
}
