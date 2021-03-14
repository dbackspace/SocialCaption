package com.xlteam.socialcaption.ui.home;

import android.graphics.BitmapFactory;
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

import java.util.List;

import timber.log.Timber;

public class PictureHomeAdapter extends RecyclerView.Adapter {
    private final int mType;
    private final List<String> mUrls;
    private final RequestOptions requestOptions;
    private PictureHomeCallback mCallback;

    public PictureHomeAdapter(int type, List<String> urls, PictureHomeCallback callback) {
        mType = type;
        mUrls = urls;
        mCallback = callback;
        // option luu cache
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    public interface PictureHomeCallback {
        void selectPhoto(int numberFont);

        void pickPhoto();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera, parent, false);
            return new CameraViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture_home, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Picasso.get().load(Utility.getUrlByCategoryIndex(mNumberCategory, position)).into(holder.imgPicture);
        Timber.e("loading position: %d", position);
        if (holder.getItemViewType() == 0) {
            CameraViewHolder cameraViewHolder = (CameraViewHolder) holder;
            cameraViewHolder.itemView.setOnClickListener(view -> mCallback.pickPhoto());
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            String url = mUrls.get(position);
            if (mType == 1) {
                Glide.with(holder.itemView.getContext())
                        .load("file://" + url)
//                        .apply(requestOptions.override(600, 600))
                        .error(R.drawable.ic_camera)
                        .fitCenter()
                        .into(viewHolder.imgPicture);
            } else if (mType == 2) {
                Glide.with(holder.itemView.getContext())
                        .load(url)
                        .error(R.drawable.ic_camera)
//                        .apply(requestOptions.override(600, 600))
                        .into(viewHolder.imgPicture);
            } else if (mType == 3) {
                Glide.with(holder.itemView.getContext())
                        .load("file://" + url)
//                        .apply(requestOptions.override(600, 600))
                        .fitCenter()
                        .into(viewHolder.imgPicture);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mType == 1 && position == 0) return 0;
        return 1;
    }

    @Override
    public int getItemCount() {
        if (mType == 1 && mUrls.size() == 0) return 1;
        return Math.min(mUrls.size(), 10);
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