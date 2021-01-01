package com.xlteam.socialcaption.ui.upload.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
    private DetailForFontCallback mDetailForFontCallback;
    private Context mContext;
    private int fontDefault;

    public interface DetailForFontCallback {
        void onFontClicked(int position);
    }

    public DetailForFontAdapter(Context context, List<Font> fonts, DetailForFontCallback DetailForFontCallback) {
        mContext = context;
        mFonts = fonts;
        mDetailForFontCallback = DetailForFontCallback;
        fontDefault = 5; // tạm thời đặt font mặc định
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_font_circle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Font font = mFonts.get(position);
        Typeface type = Typeface.createFromAsset(mContext.getAssets(), "font/" + font.getFont());
        holder.mTvFont.setTypeface(type);
        if (fontDefault == position) {
            holder.mTvFont.setBackground(mContext.getDrawable(R.drawable.bg_font_selected));
            holder.mTvFont.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.mTvFont.setBackground(mContext.getDrawable(R.drawable.bg_font_unselected));
            holder.mTvFont.setTextColor(Color.parseColor("#FFFFFF"));
        }

        holder.mTvFont.setOnClickListener((v) -> {
            fontDefault = position;
            notifyDataSetChanged();
            mDetailForFontCallback.onFontClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return mFonts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvFont;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvFont = itemView.findViewById(R.id.tvFont);
        }
    }
}
