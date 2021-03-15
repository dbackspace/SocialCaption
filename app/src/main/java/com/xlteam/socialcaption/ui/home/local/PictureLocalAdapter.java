package com.xlteam.socialcaption.ui.home.local;

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

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class PictureLocalAdapter extends RecyclerView.Adapter {
    private final List<String> mUrls;
    private final RequestOptions requestOptions;
    private Callback mCallback;

    public PictureLocalAdapter(ArrayList<String> url, Callback callback) {
        mUrls = url;
        mCallback = callback;
        // option luu cache
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    public interface Callback {
        void selectPhoto(int number);

        void pickPhoto();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera_dialog, parent, false);
            return new PictureLocalAdapter.CameraViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
            return new PictureLocalAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Picasso.get().load(Utility.getUrlByCategoryIndex(mNumberCategory, position)).into(holder.imgPicture);
        Timber.e("loading position: %d", position);
        if (holder.getItemViewType() == 0) {
            PictureLocalAdapter.CameraViewHolder cameraViewHolder = (PictureLocalAdapter.CameraViewHolder) holder;
            cameraViewHolder.itemView.setOnClickListener(view -> mCallback.pickPhoto());
        } else {
            PictureLocalAdapter.ViewHolder viewHolder = (PictureLocalAdapter.ViewHolder) holder;
            String url = mUrls.get(position);
            Glide.with(holder.itemView.getContext())
                    .load("file://" + url)
//                        .apply(requestOptions.override(600, 600))
                    .error(R.drawable.ic_camera)
                    .fitCenter()
                    .into(viewHolder.imgPicture);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        return 1;
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPicture;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.img_picture);
        }

    }

    static class CameraViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        CameraViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}