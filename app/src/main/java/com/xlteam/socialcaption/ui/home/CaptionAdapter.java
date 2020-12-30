package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.Caption;

import java.util.List;

public class CaptionAdapter extends RecyclerView.Adapter<CaptionAdapter.ViewHolder> {
    private List<Caption> mCaptions;
    private Context mContext;

    public CaptionAdapter(Context context, List<Caption> captions) {
        mContext = context;
        mCaptions = captions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_caption, parent, false);
        return new CaptionAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Caption caption = mCaptions.get(position);
        holder.tvCaptionContent.setText(caption.getContent());
        holder.tvAuthor.setText(caption.getUserName());
        holder.imgHeart.setOnClickListener(v -> {
            holder.imgHeart.setActivated(!holder.imgHeart.isActivated());
            //put to firebase
        });

        holder.imgBookmark.setOnClickListener(v -> {
            holder.imgBookmark.setActivated(!holder.imgBookmark.isActivated());
            //put to firebase
        });
    }

    @Override
    public int getItemCount() {
        return mCaptions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCaptionContent;
        private final TextView tvAuthor;
        private final ImageView imgHeart, imgBookmark;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCaptionContent = itemView.findViewById(R.id.tv_content_of_caption);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            imgHeart = itemView.findViewById(R.id.image_heart);
            imgBookmark = itemView.findViewById(R.id.image_bookmark);
        }
    }
}
