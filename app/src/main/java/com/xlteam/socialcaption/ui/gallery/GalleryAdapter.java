package com.xlteam.socialcaption.ui.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.logger.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<String> mGalleryPaths;
    private ArrayList<Integer> checkedList = new ArrayList<>();
    private GallerySelectCallback mCallback;

    // = 1 -> all check box is not checked
    // = 0 -> all check box is checked
    // = -1 -> default
    private int isCheckBoxAllChecked = -1;

    private boolean isItemLongClicked = false;

    public interface GallerySelectCallback {
        void onItemGallerySelected(int position);

        void onCheckBoxChecked(boolean isCheckBoxChecked);

//        void setCheckBoxIsChecked(boolean isCheckBoxAllChecked);
    }

    public GalleryAdapter(List<String> galleryPaths, GalleryAdapter.GallerySelectCallback callBack) {
        mGalleryPaths = galleryPaths;
        mCallback = callBack;
    }

    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path = mGalleryPaths.get(position);
        Log.e("onBindViewHolder", "file://" + path);

        File imgFile = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        holder.imgGallery.setImageBitmap(bitmap);
        holder.imgGallery.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // checkbox
        if (isCheckBoxAllChecked == -1) {
            holder.checkBox.setVisibility(checkedList.size() > 0 ? View.VISIBLE : View.GONE);
            mCallback.onCheckBoxChecked(checkedList.size() > 0);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            mCallback.onCheckBoxChecked(true);
        }
        holder.checkBox.setChecked(checkedList.contains(position));
    }

    @Override
    public int getItemCount() {
        return mGalleryPaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgGallery;
        private final CheckBox checkBox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGallery = itemView.findViewById(R.id.img_item_gallery);
            checkBox = itemView.findViewById(R.id.checkbox_item_gallery);

            imgGallery.setOnClickListener(v -> {
                if (!isItemLongClicked) {
                    mCallback.onItemGallerySelected(getAdapterPosition());
                } else {
                    checkBox.setChecked(!checkBox.isChecked());
                }

            });

            imgGallery.setOnLongClickListener(v -> {
                isItemLongClicked = true;
                if (checkedList.contains(getAdapterPosition())) {
                    checkedList.remove(Integer.valueOf(getAdapterPosition()));
                    if (checkedList.size() == 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyItemChanged(getAdapterPosition());
                    }
                } else {
                    checkedList.add(getAdapterPosition());
                    if (checkedList.size() == 1) {
                        notifyDataSetChanged();
                    } else {
                        notifyItemChanged(getAdapterPosition());
                    }
                }
                //the code at the line up just set visible the selected item but i want to set visible all items check boxs
                return false;
            });

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isCheckBoxAllChecked == -1) {
//                    mCallback.setCheckBoxIsChecked(checkedList.size() == mGalleryPaths.size());
                    if (isChecked) {
                        if (!checkedList.contains(getAdapterPosition()))
                            checkedList.add(getAdapterPosition());
                    } else {
                        checkedList.remove(Integer.valueOf(getAdapterPosition()));
//                        if (checkedList.size() == 0) {
//                            notifyDataSetChanged();
//                        }
                    }
                }
            });
        }
    }

    public void updateList(List<String> galleryPaths) {
        mGalleryPaths = new ArrayList<>(galleryPaths);
    }

    public void onCheckBoxAllChecked(boolean isChecked) {
        isCheckBoxAllChecked = isChecked ? 0 : 1;
        if (isChecked) {
            for (int i = 0; i < mGalleryPaths.size(); i++) {
                if (!checkedList.contains(i)) {
                    checkedList.add(i);
                }
            }
        } else {
            for (int i = 0; i < mGalleryPaths.size(); i++) {
                if (checkedList.contains(i)) {
                    checkedList.remove(Integer.valueOf(i));
                }
            }
        }
        notifyDataSetChanged();
    }

}