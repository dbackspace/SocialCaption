package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

public class CaptionAdapter extends RecyclerView.Adapter<CaptionAdapter.ViewHolder> {
    private static final String TAG = "CaptionAdapter";

    private List<CommonCaption> mCaptions;
    private Context mContext;
    private Callback mCallback;

    public interface Callback {
        void openCaptionPreview(CommonCaption caption, int position);
    }

    public CaptionAdapter(Context context, List<CommonCaption> captions, Callback callback) {
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
        CommonCaption caption = mCaptions.get(position);
        holder.tvCaptionContent.setText(caption.getContent());
        holder.layoutBg.setBackgroundColor(mContext.getColor(R.color.color_FA));
        holder.imgBookmark.setOnClickListener(v -> {
            holder.imgBookmark.setActivated(!holder.imgBookmark.isActivated());
            //put to database
        });
        holder.view.setOnClickListener(view -> {
            //open preview dialog
            mCallback.openCaptionPreview(mCaptions.get(position), position);
            holder.layoutBg.setBackgroundColor(mContext.getColor(R.color.color_E0));
        });
    }

    @Override
    public int getItemCount() {
        return mCaptions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCaptionContent;
        private final ImageView imgBookmark;
        private RelativeLayout layoutBg;
        private View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvCaptionContent = itemView.findViewById(R.id.tv_content_of_caption);
            imgBookmark = itemView.findViewById(R.id.image_bookmark);
            layoutBg = itemView.findViewById(R.id.layout_background);
        }
    }

    public void notifyItem(int position) {
        if (position < mCaptions.size()) {
            notifyItemChanged(position);
        }
    }
}
