package com.xlteam.socialcaption.ui.upload.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.Font;

import java.util.List;

public class DetailForFontAdapter extends RecyclerView.Adapter<DetailForFontAdapter.ViewHolder> {
    private final List<Font> mFonts;
    DetailForFontCallback mDetailForFontCallback;

    public interface DetailForFontCallback {
        void onFontClicked(int position);
    }

    public DetailForFontAdapter(List<Font> fonts, DetailForFontCallback DetailForFontCallback) {
        mFonts = fonts;
        mDetailForFontCallback = DetailForFontCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_font_for_caption, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTvFont.setText(mFonts.get(position).getFontName());

        holder.mTvFont.setOnClickListener((v) -> {
            mDetailForFontCallback.onFontClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return mFonts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvFont;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvFont = itemView.findViewById(R.id.tv_font_for_post_caption);
        }
    }
}
