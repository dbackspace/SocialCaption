package com.xlteam.socialcaption.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ItemPreviewGalleryAdapter extends RecyclerView.Adapter<ItemPreviewGalleryAdapter.ViewHolder> {
    private List<String> mGalleryPaths;
    private GallerySelectCallback mCallback;

    public interface GallerySelectCallback {
        void onItemGallerySelected(int position, String path);
    }

    public ItemPreviewGalleryAdapter(List<String> galleryPaths, GallerySelectCallback callBack) {
        mGalleryPaths = galleryPaths;
        mCallback = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path = FileUtils.findExistingFolderSaveImage().getAbsolutePath() + "/" + mGalleryPaths.get(position);
        Timber.e("file://" + path);
        Timber.e("currentPosition: " + position);

        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with((DialogPreviewGallery) mCallback)
                .load("file://" + path)
                .apply(requestOptions)
                .into(holder.imgGallery);

//        File imgFile = new File(path);
//        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//        holder.imgGallery.setImageBitmap(bitmap);
//        holder.imgGallery.setScaleType(ImageView.ScaleType.CENTER_CROP);

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
