package com.xlteam.socialcaption.ui.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.logger.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<String> mGalleryPaths;
    private GallerySelectCallback mCallback;
    private String mViewType;

    public interface GallerySelectCallback {
        void onItemGallerySelected(int position, String path);
    }

    public GalleryAdapter(List<String> galleryPaths, GalleryAdapter.GallerySelectCallback callBack, String viewType) {
        mGalleryPaths = galleryPaths;
        mCallback = callBack;
        mViewType = viewType;
    }

    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mViewType.equals("DIALOG_PREVIEW_GALLERY")) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview_image, parent, false);
            return new GalleryAdapter.ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
            return new GalleryAdapter.ViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path = mGalleryPaths.get(position);
        Log.e("onBindViewHolder", "file://" + path);
        File imgFile = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        holder.imgGallery.setImageBitmap(bitmap);
        holder.imgGallery.setScaleType(ImageView.ScaleType.CENTER_CROP);

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