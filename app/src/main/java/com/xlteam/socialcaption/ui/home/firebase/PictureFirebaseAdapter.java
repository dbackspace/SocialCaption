package com.xlteam.socialcaption.ui.home.firebase;

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
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import timber.log.Timber;

public class PictureFirebaseAdapter extends RecyclerView.Adapter<PictureFirebaseAdapter.ViewHolder> {
    private Context mContext;
    private final int mNumberCategory;
    private final RequestOptions requestOptions;

    public PictureFirebaseAdapter(Context context, int numberCategory) {
        mContext = context;
        mNumberCategory = numberCategory;
        // option luu cache
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Picasso.get().load(Utility.getUrlByCategoryIndex(mNumberCategory, position)).into(holder.imgPicture);
        Timber.e("loading position: %d", position);
        String url = Utility.getUrlByCategoryIndex(mNumberCategory, position);
        Glide.with(holder.itemView.getContext())
                .load(url)
                .apply(requestOptions.override(600, 600))
                .into(holder.imgPicture);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditCaptionActivity.class);
            intent.putExtra("EXTRA_URL_PICTURE", url);
            intent.putExtra("EXTRA_TYPE_PICTURE", 2);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 21;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPicture;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.img_picture);
        }

    }
}