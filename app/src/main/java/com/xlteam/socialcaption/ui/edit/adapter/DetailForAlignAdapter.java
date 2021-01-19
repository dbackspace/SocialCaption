package com.xlteam.socialcaption.ui.edit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;

public class DetailForAlignAdapter extends RecyclerView.Adapter<DetailForAlignAdapter.ViewHolder> {
    private AlignSelectCallback mCallback;
    private int mNumberSelect;
    public static final int[] mAligns = {R.drawable.ic_align_justify, R.drawable.ic_align_center, R.drawable.ic_align_left, R.drawable.ic_align_right};

    public interface AlignSelectCallback {
        void selectAlign(int Align);
    }

    public DetailForAlignAdapter(AlignSelectCallback callBack) {
        mCallback = callBack;
        mNumberSelect = 0; // mặc định màu ban đầu là
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background_for_caption, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImgView.setImageResource(mAligns[position]);
        holder.mImgView.setOnClickListener(v -> {
            mCallback.selectAlign(position);
            mNumberSelect = position;
        });
        if (position == 0) {
            holder.mImgView.performClick();
        }
    }

    @Override
    public int getItemCount() {
        return mAligns.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgView = itemView.findViewById(R.id.img_background_for_caption_post);
        }
    }
}
