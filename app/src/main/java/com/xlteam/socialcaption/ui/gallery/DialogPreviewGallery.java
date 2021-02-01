package com.xlteam.socialcaption.ui.gallery;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.PrefUtils;

import java.util.ArrayList;

public class DialogPreviewGallery extends DialogFragment implements GalleryAdapter.GallerySelectCallback {
    private static final String ARG_LIST_PATH = "ARG_LIST_PATH";
    private static final String ARG_CURRENT_POSITION = "ARG_CURRENT_POSITION";

    private RelativeLayout relativeTopBar;
    private ImageLoader mImageLoader;
    private GalleryAdapter mGalleryAdapter;
    ArrayList<String> mGalleryPaths;
    private int selectedPosition = -1;
    boolean isImageClicked = false;

    public static DialogPreviewGallery newInstance(ArrayList<String> galleryPaths, int position) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_LIST_PATH, galleryPaths);
        args.putInt(ARG_CURRENT_POSITION, position);
        DialogPreviewGallery fragment = new DialogPreviewGallery();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ArrayList<String> mGalleryPaths = getGalleryPaths();
        mImageLoader = getImageLoader();
        mGalleryAdapter = new GalleryAdapter(mGalleryPaths, mImageLoader, this, "DIALOG_PREVIEW_GALLERY");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_preview_gallery, container, false);

        relativeTopBar = view.findViewById(R.id.relative_preview_top_bar);

        RecyclerView rvPreviewGallery = view.findViewById(R.id.rv_preview_gallery);
        rvPreviewGallery.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true));
        rvPreviewGallery.setAdapter(mGalleryAdapter);
        rvPreviewGallery.scrollToPosition(getCurrentPosition());

        ImageView btnBackToGalleryFragment = view.findViewById(R.id.btn_preview_back_to_gallery_fragment);
        btnBackToGalleryFragment.setOnClickListener(v -> this.dismiss());

        TextView tvDelete = view.findViewById(R.id.tv_preview_delete_saved_image);
        tvDelete.setOnClickListener(v -> {
            mGalleryPaths = getGalleryPaths();
            if (selectedPosition != -1) {
                mGalleryPaths.remove(selectedPosition);
                PrefUtils.reduceListItemGallery(getContext(), selectedPosition);

                mGalleryAdapter.updateList(mGalleryPaths);
                mGalleryAdapter.notifyDataSetChanged();
                dismiss();
            }

        });

        return view;
    }

    private ArrayList<String> getGalleryPaths() {
        return requireArguments().getStringArrayList(ARG_LIST_PATH);
    }

    private int getCurrentPosition() {
        return requireArguments().getInt(ARG_CURRENT_POSITION);
    }


    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(ImageLoaderConfiguration.createDefault(requireActivity()));
        }
        return mImageLoader;
    }

    @Override
    public void onItemGallerySelected(int position, String path) {
        // do nothing
        selectedPosition = position;
        relativeTopBar.setVisibility(isImageClicked ? View.VISIBLE : View.INVISIBLE);
        isImageClicked = !isImageClicked;
    }
}
