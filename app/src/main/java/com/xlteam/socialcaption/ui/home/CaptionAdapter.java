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
    private Callback mCallback;

    public interface Callback {
        void openCaptionPreview(Caption caption);
    }

    public CaptionAdapter(Context context, List<Caption> captions, Callback callback) {
        mContext = context;
        mCaptions = captions;
        mCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_caption_in_category, parent, false);
        return new CaptionAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Caption caption = mCaptions.get(position);
        holder.tvCaptionContent.setText(caption.getContent());
        holder.imgHeart.setOnClickListener(v -> {
            holder.imgHeart.setActivated(!holder.imgHeart.isActivated());
            //put to firebase
        });
        holder.view.setOnClickListener(view -> {
            //open preview dialog
            mCallback.openCaptionPreview(mCaptions.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mCaptions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCaptionContent;
        private final ImageView imgHeart;
        private View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvCaptionContent = itemView.findViewById(R.id.tv_content_of_caption);
            imgHeart = itemView.findViewById(R.id.image_heart);
        }
    }
}
