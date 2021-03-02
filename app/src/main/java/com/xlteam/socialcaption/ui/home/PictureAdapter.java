package com.xlteam.socialcaption.ui.home;

import android.content.Context;
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

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private final int mNumberCategory;
    private Context mContext;

    public PictureAdapter(int numberCategory, Context context) {
        mNumberCategory = numberCategory;
        mContext = context;
    }

    @NonNull
    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Picasso.get().load(Utility.getUrlByCategoryIndex(mNumberCategory, position)).into(holder.imgPicture);
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext)
                .load(Utility.getUrlByCategoryIndex(mNumberCategory, position))
                .apply(requestOptions)
                .into(holder.imgPicture);
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