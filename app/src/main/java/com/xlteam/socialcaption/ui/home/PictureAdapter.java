package com.xlteam.socialcaption.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private List<String> mUrls;

    public PictureAdapter(List<String> urls) {
        mUrls = mUrls;
    }

    @NonNull
    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = mUrls.get(position);

        holder.mLoading.setVisibility(View.VISIBLE);
        //load url
//        holder.imgPicture.setImageBitmap(BitmapLruCache.getInstance().retrieveBitmapFromCache(path));
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPicture;
        private final LinearLayout mLoading;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.img_picture);
            mLoading = itemView.findViewById(R.id.loading_view);
        }

    }
}