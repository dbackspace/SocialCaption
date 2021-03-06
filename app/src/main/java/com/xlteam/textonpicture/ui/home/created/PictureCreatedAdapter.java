package com.xlteam.textonpicture.ui.home.created;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.utility.logger.Log;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class PictureCreatedAdapter extends RecyclerView.Adapter<PictureCreatedAdapter.ViewHolder> {
    private List<String> mGalleryPaths;
    private final List<Integer> checkedList = new ArrayList<>();
    private final GallerySelectCallback mCallback;

    // = 1 -> all check box is not checked
    // = 0 -> all check box is checked
    // = -1 -> default
    private int isCheckBoxAllChecked = -1;
    private boolean isItemLongClicked = false;

    private final RequestOptions requestOptions;

    public interface GallerySelectCallback {
        void onItemGallerySelected(int position);

        // hide/show checkbox all
        void showCheckBoxAll(boolean isCheckBoxChecked);

        // callback All items was checked to PictureCreatedDialogFragment
        // checkBoxAll setIsActived
        void setAllItemChecked(boolean isCheckBoxAllChecked);

        void showBottomSheetShareAndDelete(int numberImageChecked);
    }

    public PictureCreatedAdapter(List<String> galleryPaths, GallerySelectCallback callBack) {
        mGalleryPaths = galleryPaths;
        mCallback = callBack;

        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    @NonNull
    @Override
    public PictureCreatedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture_created, parent, false);
        return new PictureCreatedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path = mGalleryPaths.get(position);
//        String path = "file://" + FileUtils.findExistingFolderSaveImage().getAbsolutePath() + "/" + mGalleryPaths.get(position);
        Log.e("onBindViewHolder", path);

        Glide.with(holder.itemView.getContext())
                .load("file://" + path)
                .apply(requestOptions.override(600, 600))
                .fitCenter()
                .into(holder.imgGallery);

        // checkbox
        if (isItemLongClicked) {
            Timber.e("RunAnywhere %s", isCheckBoxAllChecked);
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
                if (isItemLongClicked) {
                    imgGallery.setSelected(true);
                } else {
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

    public List<Integer> getCheckedList() {
        return checkedList;
    }


}