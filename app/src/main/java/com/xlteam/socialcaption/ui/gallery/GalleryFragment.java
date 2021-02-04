package com.xlteam.socialcaption.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.logger.Log;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;
import com.xlteam.socialcaption.external.utility.utils.PrefUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private LinearLayout mLinearCheckAll;
    private CheckBox mCheckBoxAll;

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

        mEmptyImage = root.findViewById(R.id.tv_empty_image);
        mLinearCheckAll = root.findViewById(R.id.linear_check_all);
        mCheckBoxAll = root.findViewById(R.id.checkbox_all);
        mCheckBoxAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mGalleryAdapter.onCheckBoxAllChecked(isChecked);
        });

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
    public void onCheckBoxChecked(boolean isCheckBoxChecked) {
        mLinearCheckAll.setVisibility(isCheckBoxChecked ? View.VISIBLE : View.GONE);
        mCheckBoxAll.setVisibility(isCheckBoxChecked ? View.VISIBLE : View.GONE);
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

}