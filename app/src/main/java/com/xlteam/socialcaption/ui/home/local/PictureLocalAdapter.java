package com.xlteam.socialcaption.ui.home.local;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.xlteam.socialcaption.ui.edit.EditCaptionActivity.PICK_IMAGE;

public class PictureLocalAdapter extends RecyclerView.Adapter {
    private final List<String> mUrls;
    private final RequestOptions requestOptions;
    private Context mContext;
    private Activity mActivity;
    private static final int PICK_IMAGE_FROM_HOME = 5;

    public PictureLocalAdapter(Activity activity, Context context, ArrayList<String> url) {
        mUrls = url;
        mContext = context;
        mActivity = activity;
        // option luu cache
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
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
            cameraViewHolder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                mActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_FROM_HOME);
            });
        } else {
            PictureLocalAdapter.ViewHolder viewHolder = (PictureLocalAdapter.ViewHolder) holder;
            String url = mUrls.get(position);
            Glide.with(holder.itemView.getContext())
                    .load("file://" + url)
//                        .apply(requestOptions.override(600, 600))
                    .fitCenter()
                    .into(viewHolder.imgPicture);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, EditCaptionActivity.class);
                intent.putExtra("EXTRA_URL_PICTURE", url);
                intent.putExtra("EXTRA_TYPE_PICTURE", 1);
                mContext.startActivity(intent);
            });
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