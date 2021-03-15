package com.xlteam.socialcaption.ui.home.created;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.utils.FileUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.ui.commondialog.DialogSaveChangesBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

import static com.xlteam.socialcaption.external.utility.utils.Constant.FILE_PROVIDER_PATH;

public class PictureCreatedDialogFragment extends DialogFragment implements
        GalleryAdapter.GallerySelectCallback,
        DialogPreviewGallery.DialogDismissListenerCallback {
    private RecyclerView rvGallery;
    private Context mContext;
    private TextView mEmptyImage;
    private GalleryAdapter mGalleryAdapter;
    private List<String> mGalleryPaths;

    private LinearLayout layoutBottom;
    private boolean showed = false;
    private Transition transition;
    private ViewGroup viewGroup;

    private LinearLayout mLoadingProgress;
    private Dialog deleteDialog;
    private List<Integer> mCheckedList;

    public interface ToolbarCallback {
        void showToolbarCustom(boolean isShowed);

        void isCheckBoxAllChecked(boolean isCheckBoxAllChecked);

        void setTextForTotalCheckedTextView(boolean isShow, int numberImageChecked);
    }

    private static ToolbarCallback mCallback;

    public static PictureCreatedDialogFragment newInstance(ToolbarCallback callback) {
        mCallback = callback;
        return new PictureCreatedDialogFragment();
    }

    //    private RelativeLayout layoutTop;
    //    private ImageView imgCheckAll;
    //    private TextView tvTotalChecked;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_SocialCaption);
        mGalleryPaths = FileUtils.getListPathsIfFolderExist();
//        Timber.e("SizeListImageInSocialCaptionFolder: %s", mGalleryPaths.size());

        // set animation for bottom sheet (layout share and delete)
        transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(200);
        transition.addTarget(R.id.bottom_sheet);

        deleteDialog = DialogSaveChangesBuilder.create(getContext())
                .setTitleMessage(getString(R.string.confirm_delete_or_not))
                .setCancelable(false)
                .setSecondButton(v -> {
                }, getString(R.string.close))
                .setThirdButton(v -> new DeleteOperatorTask(mCheckedList).execute(), getString(R.string.delete))
                .build();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_dialog_created, container, false);
        this.viewGroup = container;
        mEmptyImage = root.findViewById(R.id.tv_empty_image);
        mLoadingProgress = root.findViewById(R.id.loading_view);

        layoutBottom = root.findViewById(R.id.bottom_sheet);
        layoutBottom.setVisibility(View.GONE);

//        layoutTop = root.findViewById(R.id.layout_top);
//        tvTotalChecked = root.findViewById(R.id.tv_number_image_checked);
//        imgCheckAll = root.findViewById(R.id.image_check_all);
//        imgCheckAll.setOnClickListener(view -> {
//            imgCheckAll.setActivated(!imgCheckAll.isActivated());
//            mGalleryAdapter.onCheckBoxAllChecked(imgCheckAll.isActivated());
//        });
//

        LinearLayout mBtnDeleteGallery = root.findViewById(R.id.btn_delete_gallery);
        mBtnDeleteGallery.setOnClickListener(v -> {
            mCheckedList = mGalleryAdapter.getCheckedList();
            deleteDialog.show();
        });

        LinearLayout mBtnShareGallery = root.findViewById(R.id.btn_share_gallery);
        mBtnShareGallery.setOnClickListener(v -> shareImages(mGalleryAdapter.getCheckedList()));

        // init recycler gallery by findViewById
        rvGallery = root.findViewById(R.id.rv_gallery_caption);
        rvGallery.setLayoutManager(new GridLayoutManager(mContext, 3));
        // cache recyclerview
        rvGallery.setItemViewCacheSize(25);
        rvGallery.setDrawingCacheEnabled(true);
        rvGallery.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        if (mGalleryPaths != null && mGalleryPaths.size() > 0) {
//            Timber.e("updateUI, list path size = " + mGalleryPaths.size());
            mEmptyImage.setVisibility(View.GONE);
            rvGallery.setVisibility(View.VISIBLE);
            Collections.sort(mGalleryPaths, (s1, s2) -> s1.substring(s1.lastIndexOf("/") + 1)
                    .compareTo(s2.substring(s2.lastIndexOf("/") + 1)));
            mGalleryAdapter = new GalleryAdapter(mGalleryPaths, this);
            rvGallery.setAdapter(mGalleryAdapter);
        } else {
            mGalleryAdapter = new GalleryAdapter(new ArrayList<>(), this);
            rvGallery.setVisibility(View.GONE);
            mEmptyImage.setVisibility(View.VISIBLE);
            Utility.vibrateAnimation(mContext, mEmptyImage);
        }

//        rvGallery.setHasFixedSize(true);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemGallerySelected(int position) {
        DialogPreviewGallery dialogPreview = DialogPreviewGallery.newInstance(
                new ArrayList<>(mGalleryPaths),
                position,
                this);
        dialogPreview.show(getChildFragmentManager(), "DialogPreviewGallery");
    }

    @Override
    public void showCheckBoxAll(boolean isCheckBoxChecked) {
        mCallback.showToolbarCustom(!isCheckBoxChecked);
    }

    @Override
    public void setAllItemChecked(boolean isCheckBoxAllChecked) {
        mCallback.isCheckBoxAllChecked(isCheckBoxAllChecked);
    }

    @Override
    public void showBottomSheetShareAndDelete(int numberImageChecked) {
        Timber.e("numberImageChecked: %s", numberImageChecked);
        boolean isShow = numberImageChecked != 0;
        TransitionManager.beginDelayedTransition(viewGroup, transition);

        mCallback.setTextForTotalCheckedTextView(isShow, numberImageChecked);

        if (isShow && !showed) {
            layoutBottom.setVisibility(View.VISIBLE);
            showed = true;
        } else if (!isShow && showed) {
            layoutBottom.setVisibility(View.GONE);
            showed = false;
        }
    }

    public void updateUI() {
        mGalleryPaths = FileUtils.getListPathsIfFolderExist();
        if (mGalleryPaths != null && mGalleryPaths.size() > 0) {
            Timber.e("updateUI, list path size = %s", mGalleryPaths.size());
            mEmptyImage.setVisibility(View.GONE);
            rvGallery.setVisibility(View.VISIBLE);
            Collections.sort(mGalleryPaths, (s1, s2) -> s1.substring(s1.lastIndexOf("/") + 1)
                    .compareTo(s2.substring(s2.lastIndexOf("/") + 1)));
            mGalleryAdapter.updateList(mGalleryPaths);
            mGalleryAdapter.notifyDataSetChanged();
        } else {
            rvGallery.setVisibility(View.GONE);
            mEmptyImage.setVisibility(View.VISIBLE);
            Utility.vibrateAnimation(mContext, mEmptyImage);
        }
    }

    @Override
    public void onDialogPreviewDismissed(boolean isImageDeleted) {
        if (isImageDeleted) {
            updateUI();
        }
    }

    private void shareImages(List<Integer> checkedList) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
//        intent.putExtra(Intent.EXTRA_SUBJECT, );
        intent.setType("image/jpeg");
        ArrayList<Uri> uriList = new ArrayList<>();
        String folderImagePath = FileUtils.findExistingFolderSaveImage().getAbsolutePath();
        for (int i = 0; i < mGalleryPaths.size(); i++) {
            if (checkedList.contains(i)) {
                uriList.add(
                        FileProvider.getUriForFile(mContext,
                                FILE_PROVIDER_PATH,
                                new File(folderImagePath + "/" + mGalleryPaths.get(i))));
            }
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        startActivity(Intent.createChooser(intent, "Chia sẻ"));
    }

    // TODO: Using RxJava for check onError if delete image failed.
    private final class DeleteOperatorTask extends AsyncTask<Void, Void, Void> {
        private final List<Integer> mCheckedList;
        private final List<String> mPaths;

        private DeleteOperatorTask(List<Integer> checkedList) {
            mCheckedList = checkedList;
            mPaths = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            mLoadingProgress.setVisibility(View.VISIBLE);
            String folderImagePath = FileUtils.findExistingFolderSaveImage().getAbsolutePath();
            for (int i = 0; i < mGalleryPaths.size(); i++) {
                if (mCheckedList.contains(i)) {
                    mPaths.add(folderImagePath + "/" + mGalleryPaths.get(i));
                }
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FileUtils.deleteMultiImage(mPaths, mContext);
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            mLoadingProgress.setVisibility(View.GONE);
            mCallback.showToolbarCustom(true);
            clearSelectMode();
            updateUI();
            showBottomSheetShareAndDelete(0);
        }
    }

    public void clearSelectMode() { // cancel selecting image mode
//        imgCheckAll.setActivated(false);
//        layoutTop.setVisibility(View.GONE);
        mGalleryAdapter.onCheckBoxAllChecked(false);
        mGalleryAdapter.cancelMultipleMode();
    }

    public void isImageCheckAllChecked(boolean activated) {
        mGalleryAdapter.onCheckBoxAllChecked(activated);
    }
}