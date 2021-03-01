package com.xlteam.socialcaption.ui.gallery;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;
import com.xlteam.socialcaption.external.utility.utils.FileUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.ui.MainActivity;
import com.xlteam.socialcaption.ui.commondialog.DialogSaveChangesBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

import static com.xlteam.socialcaption.external.utility.utils.Constant.FILE_PROVIDER_PATH;

public class GalleryFragment extends Fragment
        implements GalleryAdapter.GallerySelectCallback,
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
    private LinearLayout mBtnShareGallery;
    private LinearLayout mBtnDeleteGallery;

    private LinearLayout mLoadingProgress;
    private Dialog deleteDialog;
    private List<Integer> mCheckedList;

    //    private RelativeLayout layoutTop;
    //    private ImageView imgCheckAll;
    //    private TextView tvTotalChecked;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGalleryPaths = FileUtils.getListPathsIfFolderExist();
        mGalleryAdapter = new GalleryAdapter(mGalleryPaths, this);

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

    @Override
    public void onResume() {
        super.onResume();
        Log.e(this, "onResume");
        updateUI();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = AsyncLayoutInflateManager.getInstance(mContext).inflateView(inflater, container, R.layout.fragment_gallery);
        this.viewGroup = container;
        mEmptyImage = root.findViewById(R.id.tv_empty_image);
        mLoadingProgress = root.findViewById(R.id.loading_view);

        layoutBottom = root.findViewById(R.id.bottom_sheet);
        layoutBottom.setVisibility(View.INVISIBLE);

//        layoutTop = root.findViewById(R.id.layout_top);
//        tvTotalChecked = root.findViewById(R.id.tv_number_image_checked);
//        imgCheckAll = root.findViewById(R.id.image_check_all);
//        imgCheckAll.setOnClickListener(view -> {
//            imgCheckAll.setActivated(!imgCheckAll.isActivated());
//            mGalleryAdapter.onCheckBoxAllChecked(imgCheckAll.isActivated());
//        });
//

        mBtnDeleteGallery = root.findViewById(R.id.btn_delete_gallery);
        mBtnDeleteGallery.setOnClickListener(v -> {
            mCheckedList = mGalleryAdapter.getCheckedList();
            deleteDialog.show();
        });

        mBtnShareGallery = root.findViewById(R.id.btn_share_gallery);
        mBtnShareGallery.setOnClickListener(v -> {
            shareImages(mGalleryAdapter.getCheckedList());
        });

        // init recycler gallery by findViewById
        rvGallery = root.findViewById(R.id.rv_gallery_caption);
        rvGallery.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvGallery.setAdapter(mGalleryAdapter);
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
        ((MainActivity) getActivity()).showToolbarCustom(isCheckBoxChecked);
    }

    @Override
    public void setAllItemChecked(boolean isCheckBoxAllChecked) {
        ((MainActivity) getActivity()).imgCheckAll.setActivated(isCheckBoxAllChecked);
    }

    @Override
    public void showBottomSheetShareAndDelete(int numberImageChecked) {
        Timber.e("numberImageChecked: " + numberImageChecked);
        boolean isShow = numberImageChecked != 0;
        TransitionManager.beginDelayedTransition(viewGroup, transition);

        if (isShow) {
            ((MainActivity) getActivity()).tvTotalChecked.setText(mContext.getString(R.string.select_number_image, numberImageChecked));
        } else {
            ((MainActivity) getActivity()).tvTotalChecked.setText(R.string.select_items);
        }
        if (isShow && !showed) {
            layoutBottom.setVisibility(View.VISIBLE);
            showed = true;
        } else if (!isShow && showed) {
            layoutBottom.setVisibility(View.INVISIBLE);
            showed = false;
        }
    }

    private void updateUI() {
        mGalleryPaths = FileUtils.getListPathsIfFolderExist();
        if (mGalleryPaths != null && mGalleryPaths.size() > 0) {
            Timber.e("updateUI, list path size = " + mGalleryPaths.size());
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
    public void onDialogPreviewDismissed(Boolean isImageDeleted) {
        if (isImageDeleted) {
            updateUI();
        }
    }

    private void shareImages(List<Integer> checkedList) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
//        intent.putExtra(Intent.EXTRA_SUBJECT, );
        intent.setType("image/jpeg");
        ArrayList<Uri> uriList = new ArrayList<Uri>();
        for (int i = 0; i < mGalleryPaths.size(); i++) {
            if (checkedList.contains(i)) {
                uriList.add(
                        FileProvider.getUriForFile(getContext(),
                                FILE_PROVIDER_PATH,
                                new File(mGalleryPaths.get(i))));

            }
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        startActivity(Intent.createChooser(intent, "Chia sẻ"));
    }

    // TODO: Using RxJava for check onError if delete image failed.
    private final class DeleteOperatorTask extends AsyncTask<Void, Void, String> {
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
        protected String doInBackground(Void... voids) {
            FileUtils.deleteMultiImage(mPaths, mContext);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mLoadingProgress.setVisibility(View.GONE);
            updateUI();
        }
    }

    public void onBackPress() { // cancel selecting image mode
//        imgCheckAll.setActivated(false);
//        layoutTop.setVisibility(View.GONE);
        mGalleryAdapter.onCheckBoxAllChecked(false);
        mGalleryAdapter.cancelMultipleMode();
    }

    public void isImageCheckAllChecked(boolean activated) {
        mGalleryAdapter.onCheckBoxAllChecked(activated);
    }
}