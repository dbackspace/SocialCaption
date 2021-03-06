package com.xlteam.textonpicture.ui.home.created;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.Transition;

import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.utility.utils.Constant;
import com.xlteam.textonpicture.external.utility.utils.FileUtils;
import com.xlteam.textonpicture.external.utility.utils.Utility;
import com.xlteam.textonpicture.ui.commondialog.DialogSaveChangesBuilder;
import com.xlteam.textonpicture.ui.edit.EditPictureActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

import static com.xlteam.textonpicture.external.utility.utils.Constant.FILE_PROVIDER_PATH;

public class PictureCreatedDialogFragment extends DialogFragment
        implements
        PictureCreatedAdapter.GallerySelectCallback,
        DialogInterface {
    private RecyclerView rvCreated;
    private Context mContext;
    private TextView mEmptyImage, tvTotalChecked;
    private PictureCreatedAdapter mPictureCreatedAdapter;
    private List<String> mGalleryPaths;

    private LinearLayout layoutBottom, layoutCheckAll;
    private RelativeLayout layoutTitle;
    private boolean showed = false;
    private Transition transition;

    private LinearLayout mLoadingProgress;
    private Dialog deleteDialog;
    private List<Integer> mCheckedList;
    private ImageView imgCheckAll, imgBack;
    private boolean temp;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(mContext, getTheme()) {
            @Override
            public void onBackPressed() {
                if (layoutCheckAll.getVisibility() == View.VISIBLE) {
                    clearSelectMode();
                } else super.onBackPressed();
            }
        };
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_TextOnPicture);
        mGalleryPaths = FileUtils.getListPathsIfFolderExist();

        deleteDialog = DialogSaveChangesBuilder.create(getContext())
                .setTitleMessage(getString(R.string.confirm_delete_or_not))
                .setCancelable(false)
                .setSecondButton(v -> {
                }, getString(R.string.cancel))
                .setThirdButton(v -> new DeleteOperatorTask(mCheckedList).execute(), getString(R.string.delete))
                .build();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_dialog_created, container, false);
        mEmptyImage = root.findViewById(R.id.tv_empty_image);
        mLoadingProgress = root.findViewById(R.id.loading_view);
        imgCheckAll = root.findViewById(R.id.image_check_all);
        imgBack = root.findViewById(R.id.image_back);
        tvTotalChecked = root.findViewById(R.id.tv_number_image_checked);
        layoutCheckAll = root.findViewById(R.id.layout_check_all);
        layoutTitle = root.findViewById(R.id.layout_title);
        layoutBottom = root.findViewById(R.id.bottom_sheet);
        layoutBottom.setVisibility(View.GONE);

        // set animation for bottom sheet (layout share and delete)
        transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(200);
        transition.addTarget(R.id.bottom_sheet);

//        layoutTop = root.findViewById(R.id.layout_top);
//        tvTotalChecked = root.findViewById(R.id.tv_number_image_checked);
//        imgCheckAll = root.findViewById(R.id.image_check_all);

        temp = imgCheckAll.isActivated();
        imgCheckAll.setOnClickListener(view -> {
            imgCheckAll.setActivated(!temp);
            mPictureCreatedAdapter.onCheckBoxAllChecked(!temp);
            temp = !temp;
        });

        LinearLayout mBtnDeleteGallery = root.findViewById(R.id.btn_delete_gallery);
        mBtnDeleteGallery.setOnClickListener(v -> {
            mCheckedList = mPictureCreatedAdapter.getCheckedList();
            deleteDialog.show();
        });

        LinearLayout mBtnShareGallery = root.findViewById(R.id.btn_share_gallery);
        mBtnShareGallery.setOnClickListener(v -> shareImages(mPictureCreatedAdapter.getCheckedList()));

        // init recycler gallery by findViewById
        rvCreated = root.findViewById(R.id.rv_created_picture);
        rvCreated.setLayoutManager(new GridLayoutManager(mContext, 3));
        // cache recyclerview
        rvCreated.setItemViewCacheSize(25);
        rvCreated.setDrawingCacheEnabled(true);
        rvCreated.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        if (mGalleryPaths != null && mGalleryPaths.size() > 0) {
//            Timber.e("updateUI, list path size = " + mGalleryPaths.size());
            mEmptyImage.setVisibility(View.GONE);
            rvCreated.setVisibility(View.VISIBLE);
            Collections.sort(mGalleryPaths, (s1, s2) -> s1.substring(s1.lastIndexOf("/") + 1)
                    .compareTo(s2.substring(s2.lastIndexOf("/") + 1)));
            mPictureCreatedAdapter = new PictureCreatedAdapter(mGalleryPaths, this);
            rvCreated.setAdapter(mPictureCreatedAdapter);
        } else {
            mPictureCreatedAdapter = new PictureCreatedAdapter(new ArrayList<>(), this);
            rvCreated.setVisibility(View.GONE);
            mEmptyImage.setVisibility(View.VISIBLE);
            Utility.vibrateAnimation(mContext, mEmptyImage);
        }
        imgBack.setOnClickListener(v -> onDismiss(this));
//        rvGallery.setHasFixedSize(true);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemGallerySelected(int position) {
        Intent intent = new Intent(mContext, EditPictureActivity.class);
        intent.putExtra(Constant.EXTRA_URL_PICTURE, mGalleryPaths.get(position));
        intent.putExtra(Constant.EXTRA_TYPE_PICTURE, Constant.TYPE_PICTURE_CREATED);
        ((Activity) mContext).startActivityForResult(intent, Constant.REQUEST_CODE_PHOTO_FROM_HOME);
    }

    @Override
    public void showCheckBoxAll(boolean isCheckBoxChecked) {
        layoutCheckAll.setVisibility(isCheckBoxChecked ? View.VISIBLE : View.GONE);
        layoutTitle.setVisibility(isCheckBoxChecked ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setAllItemChecked(boolean isCheckBoxAllChecked) {
        imgCheckAll.setActivated(isCheckBoxAllChecked);
    }

    @Override
    public void showBottomSheetShareAndDelete(int numberImageChecked) {
        Timber.e("numberImageChecked: %s", numberImageChecked);
        boolean isShow = numberImageChecked != 0;

        if (isShow) {
            tvTotalChecked.setText(getString(R.string.select_number_image, numberImageChecked));
            layoutTitle.setVisibility(View.GONE);
            layoutCheckAll.setVisibility(View.VISIBLE);
        } else {
            tvTotalChecked.setText(R.string.select_items);
        }

        if (isShow && !showed) {
//            TransitionManager.beginDelayedTransition(viewGroup, transition);
            layoutBottom.setVisibility(View.VISIBLE);
            showed = true;
        } else if (!isShow && showed) {
            showed = false;
            layoutBottom.setVisibility(View.GONE);
        }
    }

    private void updateUI() {
        mGalleryPaths = FileUtils.getListPathsIfFolderExist();
        if (mGalleryPaths != null && mGalleryPaths.size() > 0) {
            Timber.e("updateUI, list path size = %s", mGalleryPaths.size());
            mEmptyImage.setVisibility(View.GONE);
            rvCreated.setVisibility(View.VISIBLE);
            Collections.sort(mGalleryPaths, (s1, s2) -> s1.substring(s1.lastIndexOf("/") + 1)
                    .compareTo(s2.substring(s2.lastIndexOf("/") + 1)));
            mPictureCreatedAdapter.updateList(mGalleryPaths);
            mPictureCreatedAdapter.notifyDataSetChanged();
        } else {
            rvCreated.setVisibility(View.GONE);
            mEmptyImage.setVisibility(View.VISIBLE);
            Utility.vibrateAnimation(mContext, mEmptyImage);
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
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }

    @Override
    public void cancel() {

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
            for (int i = 0; i < mGalleryPaths.size(); i++) {
                if (mCheckedList.contains(i)) {
                    mPaths.add(mGalleryPaths.get(i));
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
            layoutTitle.setVisibility(View.GONE);
            layoutCheckAll.setVisibility(View.VISIBLE);
            clearSelectMode();
            updateUI();
            showBottomSheetShareAndDelete(0);
        }
    }

    private void clearSelectMode() { // cancel selecting image mode
        layoutCheckAll.setVisibility(View.GONE);
        layoutTitle.setVisibility(View.VISIBLE);
        mPictureCreatedAdapter.onCheckBoxAllChecked(false);
        mPictureCreatedAdapter.cancelMultipleMode();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}