package com.xlteam.textonpicture.ui.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.textonpicture.R;

public class ToolTextAdapter extends RecyclerView.Adapter<ToolTextAdapter.ViewHolder> {
    private int mNumberTool = -1;
    private int[] mIcons;
    private int[] mNameTools;
    private Callback mCallback;
    private Context mContext;

    public interface Callback {
        void onMenuTextClicked(int number);
    }

    ToolTextAdapter(Context context, Callback callback) {
        mContext = context;
        mCallback = callback;
        mIcons = new int[]{R.drawable.ic_add_text, R.drawable.ic_text_color, R.drawable.ic_text_bg_color, R.drawable.ic_text_shadow, R.drawable.ic_text_font, R.drawable.ic_bend, R.drawable.ic_align_center};
        mNameTools = new int[]{R.string.add_text, R.string.color_text, R.string.color_background, R.string.shadow, R.string.font, R.string.bend, R.string.gravity};
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tool_text, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mNumberTool == position && position != 0) {
            holder.tvTool.setTextColor(mContext.getColor(R.color.color_3cc2f5_legend));
            holder.imgTool.setColorFilter(mContext.getColor(R.color.color_3cc2f5_legend));
        } else {
            holder.tvTool.setTextColor(mContext.getColor(R.color.white));
            holder.imgTool.setColorFilter(mContext.getColor(R.color.white));
        }
        holder.imgTool.setImageResource(mIcons[position]);
        holder.tvTool.setText(mNameTools[position]);
        holder.mItemView.setOnClickListener(view -> {
            if (position != 0) {
                mNumberTool = position;
                notifyDataSetChanged();
            }
            mCallback.onMenuTextClicked(position);
        });
    }

    public int getCurrentNumberTool() {
        return mNumberTool;
    }

    @Override
    public int getItemCount() {
        return mIcons.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTool;
        private TextView tvTool;
        private View mItemView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            imgTool = itemView.findViewById(R.id.image_tool);
            tvTool = itemView.findViewById(R.id.tv_tool);
        }

    }
}