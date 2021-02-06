package com.xlteam.socialcaption.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.xlteam.socialcaption.external.utility.utils.PrefUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.xlteam.socialcaption.external.utility.utils.Constant.FILE_PROVIDER_PATH;

public class GalleryFragment extends Fragment
        implements GalleryAdapter.GallerySelectCallback,
        DialogPreviewGallery.DialogDismissListenerCallback {
    private static final String TAG = "GalleryFragment";
    private RecyclerView rvGallery;
    private Context mContext;
    //    private StaggeredGridLayoutManager _staGridLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private TextView mEmptyImage;
    private GalleryAdapter mGalleryAdapter;
    private List<String> mGalleryPaths;
    private RelativeLayout mRelativeCheckAll;
    private CheckBox mCheckBoxAll;
    private TextView mTvCancelMultipleMode;

    private LinearLayout mLinearShareAndDelete;
    private boolean showed = false;
    private Transition transition;
    private ViewGroup viewGroup;
    private LinearLayout mBtnShareGallery;
    private LinearLayout mBtnDeleteGallery;
    private TextView mTvNumberOfImageChecked;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mGalleryPaths = PrefUtils.getListItemGallery(mContext);
//        _staGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        mGalleryAdapter = new GalleryAdapter(mGalleryPaths, this);

        // set animation for bottom sheet (layout share and delete)
        transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(200);
        transition.addTarget(R.id.bottom_sheet);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        updateUI();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = AsyncLayoutInflateManager.getInstance(mContext).inflateView(inflater, container, R.layout.fragment_gallery);
        this.viewGroup = container;
        mEmptyImage = root.findViewById(R.id.tv_empty_image);
        mRelativeCheckAll = root.findViewById(R.id.linear_check_all);
        mLinearShareAndDelete = root.findViewById(R.id.bottom_sheet);

        mTvNumberOfImageChecked = root.findViewById(R.id.tv_number_image_checked);

        mBtnDeleteGallery = root.findViewById(R.id.btn_delete_gallery);
        mBtnDeleteGallery.setOnClickListener(v -> {
            deleteImages(mGalleryAdapter.getCheckedList());
        });

        mBtnShareGallery = root.findViewById(R.id.btn_share_gallery);
        mBtnShareGallery.setOnClickListener(v -> {
            shareImages(mGalleryAdapter.getCheckedList());
        });

        mTvCancelMultipleMode = root.findViewById(R.id.tv_cancel_multiple_mode);
        mTvCancelMultipleMode.setOnClickListener(v -> {
            mCheckBoxAll.setChecked(false);
            mGalleryAdapter.onCheckBoxAllChecked(false);
            mGalleryAdapter.cancelMultipleMode();
            mRelativeCheckAll.setVisibility(View.GONE);
        });
        mCheckBoxAll = root.findViewById(R.id.checkbox_all);
        mCheckBoxAll.setOnCheckedChangeListener(onCheckedChangeListener);

        // init recycler gallery by findViewById
        rvGallery = root.findViewById(R.id.rv_gallery_caption);
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        rvGallery.setLayoutManager(gridLayoutManager);
        rvGallery.setAdapter(mGalleryAdapter);
        rvGallery.setHasFixedSize(true);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView, isChecked) -> mGalleryAdapter.onCheckBoxAllChecked(isChecked);

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_gallery, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_create_picture) {
            Intent intent = new Intent(mContext, EditCaptionActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        return super.onOptionsItemSelected(item);
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
        mRelativeCheckAll.setVisibility(isCheckBoxChecked ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setAllItemChecked(boolean isCheckBoxAllChecked) {
        mCheckBoxAll.setOnCheckedChangeListener(null);
        mCheckBoxAll.setChecked(isCheckBoxAllChecked);
        mCheckBoxAll.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    public void showBottomSheetShareAndDelete(int numberImageChecked) {
        Timber.e("numberImageChecked: " + numberImageChecked);
        boolean isShow = numberImageChecked != 0;
        TransitionManager.beginDelayedTransition(viewGroup, transition);
        if (isShow) {
            mTvNumberOfImageChecked.setText(String.valueOf(numberImageChecked));
        } else {
            mTvNumberOfImageChecked.setText(R.string.select_items);
        }
        if (isShow && !showed) {
            mLinearShareAndDelete.setVisibility(View.VISIBLE);
            showed = true;
        } else if (!isShow && showed) {
            mLinearShareAndDelete.setVisibility(View.GONE);
            showed = false;
        }
    }

    private void updateUI() {
        mGalleryPaths = PrefUtils.getListItemGallery(mContext);
        Log.e("GalleryFragment", "updateUI, list path size = " + mGalleryPaths.size());
        if (mGalleryPaths.size() > 0) {
            mEmptyImage.setVisibility(View.GONE);
            rvGallery.setVisibility(View.VISIBLE);
//            Collections.sort(mGalleryPaths);
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

    private void shareImages(ArrayList<Integer> checkedList) {
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

    private void deleteImages(ArrayList<Integer> checkedList) {

    }
}