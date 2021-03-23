package com.xlteam.textonpicture.ui.edit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.datasource.FontDataSource;
import com.xlteam.textonpicture.model.Font;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Font> mFonts;
    private int numberFont;
    private FontSelectCallback mCallBack;

    public interface FontSelectCallback {
        void selectFont(int numberFont);
    }

    FontAdapter(Context context, FontSelectCallback callBack) {
        mContext = context;
        mFonts = FontDataSource.getInstance().getAllFonts();
        mCallBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_font, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Font font = mFonts.get(position);
        holder.tvFont.setText(font.getFontName());
        Typeface type = Typeface.createFromAsset(mContext.getAssets(), "font/" + font.getFont());
        holder.tvFont.setTypeface(type);
        if (position == numberFont) {
            holder.tvFont.setTextColor(mContext.getColor(R.color.color_3cc2f5_legend));
        } else {
            holder.tvFont.setTextColor(mContext.getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(view -> {
            numberFont = position;
            mCallBack.selectFont(numberFont);
            notifyDataSetChanged();
        });
    }

    void setNumberFont(int numberFont) {
        this.numberFont = numberFont;
    }

    @Override
    public int getItemCount() {
        return mFonts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFont;
        View itemView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvFont = itemView.findViewById(R.id.tvFont);
        }
    }
}
