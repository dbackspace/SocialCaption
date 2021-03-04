package com.xlteam.socialcaption.ui.gallery;

import android.app.Dialog;
import android.content.Context;
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
import com.xlteam.socialcaption.external.utility.utils.FileUtils;
import com.xlteam.socialcaption.ui.commondialog.DialogSaveChangesBuilder;

import java.io.Serializable;
import java.util.ArrayList;

import timber.log.Timber;

public class DialogPreviewGallery extends DialogFragment implements ItemPreviewGalleryAdapter.GallerySelectCallback {
    private static final String ARG_LIST_PATH = "ARG_LIST_PATH";
    private static final String ARG_CURRENT_POSITION = "ARG_CURRENT_POSITION";
    private static final String ARG_DIALOG_DISMISS_LISTENER = "ARG_DIALOG_DISMISS_LISTENER";

    private RecyclerView mRecyclerPreviewGallery;
    private LinearLayoutManager mLinearLayoutManager;
    private RelativeLayout relativeTopBar;
    private ItemPreviewGalleryAdapter mItemPreviewGalleryAdapter;
    ArrayList<String> mGalleryPaths;
    boolean isImageClicked = false;

    private Dialog saveDialog;
    private Boolean isDeleted = false;
    private int mCurrentPosition;
    private Context mContext;

    interface DialogDismissListenerCallback extends Serializable {
        void onDialogPreviewDismissed(boolean isImageDeleted);
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
        mItemPreviewGalleryAdapter = new ItemPreviewGalleryAdapter(mGalleryPaths, this);
        mListener = getDialogDismissListenerCallback();
        mCurrentPosition = getPickedPosition();
        Timber.e("currentPosition: %s", mCurrentPosition);
        mContext = getContext();

        saveDialog = DialogSaveChangesBuilder.create(getContext())
                .setTitleMessage(getString(R.string.confirm_delete_or_not))
                .setCancelable(false)
                .setSecondButton(v -> {
                }, getString(R.string.close))
                .setThirdButton(v -> deleteImage(), getString(R.string.delete))
                .build();
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

        mRecyclerPreviewGallery = view.findViewById(R.id.rv_preview_gallery);
        mLinearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerPreviewGallery.setLayoutManager(mLinearLayoutManager);
        mRecyclerPreviewGallery.setAdapter(mItemPreviewGalleryAdapter);
        mRecyclerPreviewGallery.scrollToPosition(mCurrentPosition);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerPreviewGallery);

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

    private int getPickedPosition() {
        return requireArguments().getInt(ARG_CURRENT_POSITION);
    }

    private DialogDismissListenerCallback getDialogDismissListenerCallback() {
        return (DialogDismissListenerCallback) requireArguments().getSerializable(ARG_DIALOG_DISMISS_LISTENER);
    }

    @Override
    public void onItemGallerySelected(int position, String path) {
        // do nothing
        mCurrentPosition = position;
        relativeTopBar.setVisibility(isImageClicked ? View.VISIBLE : View.INVISIBLE);
        isImageClicked = !isImageClicked;
    }

    private void deleteImage() {
        mCurrentPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        Timber.e("mCurrentPosition" + " " + mCurrentPosition);

        String folderImagePath = FileUtils.findExistingFolderSaveImage().getAbsolutePath();

        if (mGalleryPaths.size() == 1) {
            FileUtils.deleteSingleImage(folderImagePath + "/" + mGalleryPaths.get(0), mContext);
            mListener.onDialogPreviewDismissed(true);
            dismiss();
        }
        if (mCurrentPosition < mGalleryPaths.size()) {
            String deleteImagePath = mGalleryPaths.get(mCurrentPosition);
            FileUtils.deleteSingleImage(folderImagePath + "/" + deleteImagePath, mContext);
            mGalleryPaths.remove(mCurrentPosition);
//            mItemPreviewGalleryAdapter.updateList(mGalleryPaths);
//            mItemPreviewGalleryAdapter.notifyDataSetChanged();
            mItemPreviewGalleryAdapter.notifyItemRemoved(mCurrentPosition);
            isDeleted = true;
        }
    }
}
