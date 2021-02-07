package com.xlteam.socialcaption.ui.gallery;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.thread.BitmapLruCache;
import com.xlteam.socialcaption.external.utility.thread.ThreadExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

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

        // hide/show checkbox all
        void showCheckBoxAll(boolean isCheckBoxChecked);

        // callback All items was checked to GalleryFragment
        void setAllItemChecked(boolean isCheckBoxAllChecked);

        void showBottomSheetShareAndDelete(int numberImageChecked);
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

//        ThreadExecutor.runOnMainThread(() -> Picasso.get()
//                .load(new File(path))
//                .into(holder.imgGallery, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        holder.rlItemGallery.setBackgroundColor(Color.BLACK);
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                    }
//                }));

        holder.imgGallery.setImageBitmap(BitmapLruCache.getInstance().retrieveBitmapFromCache(path));
        holder.imgGallery.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.rlItemGallery.setBackgroundColor(Color.BLACK);

        // checkbox
        if (isItemLongClicked) {
            if (isCheckBoxAllChecked == -1) {
                holder.checkBox.setVisibility(checkedList.size() > 0 ? View.VISIBLE : View.GONE);
                mCallback.showCheckBoxAll(checkedList.size() > 0);
            } else {
                holder.checkBox.setVisibility(View.VISIBLE);
                mCallback.showCheckBoxAll(true);
            }
            holder.checkBox.setChecked(checkedList.contains(position));
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
        mCallback.showBottomSheetShareAndDelete(checkedList.size());
    }

    @Override
    public int getItemCount() {
        return mGalleryPaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rlItemGallery;
        private final ImageView imgGallery;
        private final CheckBox checkBox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlItemGallery = itemView.findViewById(R.id.rl_item_gallery);
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
//                if (isCheckBoxAllChecked == -1) {
                if (isChecked) {
                    if (!checkedList.contains(getAdapterPosition())) {
                        checkedList.add(getAdapterPosition());
                    }

                } else {
                    checkedList.remove(Integer.valueOf(getAdapterPosition()));
//                if (checkedList.size() == 0) {
//                    notifyDataSetChanged();
//                }

                }
//                }
                Timber.d("GalleryPath size: " + mGalleryPaths.size() + " - checkedList size: " + checkedList.size());
                mCallback.setAllItemChecked(checkedList.size() == mGalleryPaths.size());
                mCallback.showBottomSheetShareAndDelete(checkedList.size());
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

    public void cancelMultipleMode() {
        isItemLongClicked = false;
    }

    public ArrayList<Integer> getCheckedList() {
        return checkedList;
    }


}