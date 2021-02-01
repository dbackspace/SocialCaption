package com.xlteam.socialcaption.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xlteam.socialcaption.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<String> mGalleryPaths;
    private GallerySelectCallback mCallback;
    private ImageLoader imageLoader;

    public interface GallerySelectCallback {
        void onItemGallerySelected(int position, String path);
    }

    public GalleryAdapter(List<String> galleryPaths, ImageLoader imageLoader, GalleryAdapter.GallerySelectCallback callBack) {
        mGalleryPaths = galleryPaths;
        mCallback = callBack;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ViewHolder holder, int position) {
        String path = mGalleryPaths.get(position);
        imageLoader.displayImage("file://" + path, holder.imgGallery);
        holder.imgGallery.setOnClickListener(v -> {
            mCallback.onItemGallerySelected(position, path);
        });
    }

    @Override
    public int getItemCount() {
        return mGalleryPaths.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        ImageView imgGallery;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            imgGallery = itemView.findViewById(R.id.img_item_gallery);
        }
    }

    public void updateList(List<String> galleryPaths) {
        mGalleryPaths = new ArrayList<>(galleryPaths);
    }
}