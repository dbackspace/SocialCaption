package com.xlteam.textonpicture.ui.home.firebase;

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
import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.utility.utils.Constant;
import com.xlteam.textonpicture.external.utility.utils.Utility;
import com.xlteam.textonpicture.ui.edit.EditPictureActivity;

import timber.log.Timber;

public class PictureFirebaseAdapter extends RecyclerView.Adapter<PictureFirebaseAdapter.ViewHolder> {
    private Context mContext;
    private final int mNumberCategory;
    private final RequestOptions requestOptions;

    PictureFirebaseAdapter(Context context, int numberCategory) {
        mContext = context;
        mNumberCategory = numberCategory;
        // option luu cache
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture_firebase, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Timber.e("loading position: %d", position);
        String url = Utility.getUrlByCategoryIndex(mNumberCategory, position);
        Glide.with(holder.itemView.getContext())
                .load(url)
                .apply(requestOptions.override(600, 600))
                .into(holder.imgPicture);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditPictureActivity.class);
            intent.putExtra(Constant.EXTRA_URL_PICTURE, url);
            intent.putExtra(Constant.EXTRA_TYPE_PICTURE, Constant.TYPE_PICTURE_FIREBASE);
            ((Activity) mContext).startActivityForResult(intent, Constant.REQUEST_CODE_PHOTO_FROM_HOME);
        });
    }

    @Override
    public int getItemCount() {
        return 21;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPicture;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.img_picture);
        }

    }
}