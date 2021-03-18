package com.xlteam.socialcaption.ui.home;

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
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import java.util.List;

import timber.log.Timber;

public class PictureHomeAdapter extends RecyclerView.Adapter<PictureHomeAdapter.ViewHolder> {
    private final int mType;
    private Context mContext;
    private final List<String> mUrls;
    private final RequestOptions requestOptions;

    PictureHomeAdapter(Context context, int type, List<String> urls) {
        mType = type;
        mUrls = urls;
        mContext = context;
        // option luu cache
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Timber.e("loading position: %d", position);
        String url = mUrls.get(position);
        if (mType == Constant.TYPE_PICTURE_FIREBASE) {
            Glide.with(mContext)
                    .load(url)
                    .error(R.drawable.ic_image_error)
//                        .apply(requestOptions.override(600, 600))
                    .into(viewHolder.imgPicture);
        } else if (mType == Constant.TYPE_PICTURE_CREATED) {
            Glide.with(mContext)
                    .load("file://" + url)
//                        .apply(requestOptions.override(600, 600))
                    .fitCenter()
                    .into(viewHolder.imgPicture);
        }
        viewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, EditCaptionActivity.class);
            intent.putExtra(Constant.EXTRA_URL_PICTURE, url);
            intent.putExtra(Constant.EXTRA_TYPE_PICTURE, mType);
            ((Activity) mContext).startActivityForResult(intent, Constant.REQUEST_CODE_PHOTO_FROM_HOME);
        });
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPicture;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.img_picture);
        }

    }
}