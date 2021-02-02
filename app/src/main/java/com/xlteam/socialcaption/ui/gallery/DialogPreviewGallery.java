package com.xlteam.socialcaption.ui.gallery;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.external.utility.utils.PrefUtils;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.ui.commondialog.DialogSaveChangesBuilder;

import java.io.Serializable;
import java.util.ArrayList;

public class DialogPreviewGallery extends DialogFragment implements GalleryAdapter.GallerySelectCallback {
    private static final String ARG_LIST_PATH = "ARG_LIST_PATH";
    private static final String ARG_CURRENT_POSITION = "ARG_CURRENT_POSITION";
    private static final String ARG_DIALOG_DISMISS_LISTENER = "ARG_DIALOG_DISMISS_LISTENER";

    private RecyclerView rvPreviewGallery;
    private RelativeLayout relativeTopBar;
    private GalleryAdapter mGalleryAdapter;
    ArrayList<String> mGalleryPaths;
    boolean isImageClicked = false;

    private Dialog saveDialog;
    private Boolean isDeleted = false;

    interface DialogDismissListenerCallback extends Serializable {
        void onDialogPreviewDismissed(Boolean isImageDeleted);
    }

    private DialogDismissListenerCallback mListener;

    public static DialogPreviewGallery newInstance(ArrayList<String> galleryPaths, int position, DialogDismissListenerCallback dialogDismissListenerCallback) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_LIST_PATH, galleryPaths);
        args.putInt(ARG_CURRENT_POSITION, position);
        args.putSerializable(ARG_DIALOG_DISMISS_LISTENER, dialogDismissListenerCallback);

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
        mGalleryAdapter = new GalleryAdapter(mGalleryPaths, this, "DIALOG_PREVIEW_GALLERY");
        mListener = getDialogDismissListenerCallback();

        saveDialog = new DialogSaveChangesBuilder(getContext(),
                v -> {
                },
                v -> dismiss(),
                v -> deleteImage()).build();
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

        rvPreviewGallery = view.findViewById(R.id.rv_preview_gallery);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true);
        rvPreviewGallery.setLayoutManager(linearLayoutManager);
        rvPreviewGallery.setAdapter(mGalleryAdapter);
        rvPreviewGallery.scrollToPosition(getCurrentPosition());
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvPreviewGallery);

        ImageView btnBackToGalleryFragment = view.findViewById(R.id.btn_preview_back_to_gallery_fragment);
        btnBackToGalleryFragment.setOnClickListener(v -> {
            mListener.onDialogPreviewDismissed(isDeleted);
            dismiss();
        });

        TextView tvDelete = view.findViewById(R.id.tv_preview_delete_saved_image);
        tvDelete.setOnClickListener(v -> {
            mGalleryPaths = getGalleryPaths();
            saveDialog.show();
        });

        return view;
    }

    private ArrayList<String> getGalleryPaths() {
        return requireArguments().getStringArrayList(ARG_LIST_PATH);
    }

    private int getCurrentPosition() {
        return requireArguments().getInt(ARG_CURRENT_POSITION);
    }

    private DialogDismissListenerCallback getDialogDismissListenerCallback() {
        return (DialogDismissListenerCallback) requireArguments().getSerializable(ARG_DIALOG_DISMISS_LISTENER);
    }

    @Override
    public void onItemGallerySelected(int position, String path) {
        // do nothing
        relativeTopBar.setVisibility(isImageClicked ? View.VISIBLE : View.INVISIBLE);
        isImageClicked = !isImageClicked;
    }

    private void deleteImage() {
        Log.e("TEST", getCurrentPosition() + "");
        if (mGalleryPaths.size() == 1) {
            PrefUtils.putStringArrayList(getContext(), Constant.PREF_GALLERY, Constant.GALLERY_PATH, new ArrayList<>());
            mListener.onDialogPreviewDismissed(true);
            dismiss();
        }
        if (getCurrentPosition() < mGalleryPaths.size()) {
            mGalleryPaths.remove(getCurrentPosition());
            PrefUtils.putStringArrayList(getContext(), Constant.PREF_GALLERY, Constant.GALLERY_PATH, mGalleryPaths);
            mGalleryAdapter.updateList(mGalleryPaths);
            mGalleryAdapter.notifyDataSetChanged();
            isDeleted = true;
        }
    }
}