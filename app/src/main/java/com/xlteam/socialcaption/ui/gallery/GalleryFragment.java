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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.PrefUtils;
import com.xlteam.socialcaption.external.utility.Utility;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment implements GalleryAdapter.GallerySelectCallback {
    private RecyclerView rvGallery;
    private Context mContext;
    private ImageLoader mImageLoader;
    //    private StaggeredGridLayoutManager _staGridLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private TextView mEmptyImage;
    private GalleryAdapter galleryAdapter;
    private List<String> mGalleries;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mGalleries = PrefUtils.getListItemGallery(mContext);
//        _staGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        galleryAdapter = new GalleryAdapter(mGalleries, getImageLoader(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mGalleries = PrefUtils.getListItemGallery(mContext);
        if (!Utility.isEmpty(mGalleries)) {
            mEmptyImage.setVisibility(View.GONE);
            Collections.sort(mGalleries);
            galleryAdapter.updateList(mGalleries);
            galleryAdapter.notifyDataSetChanged();
        } else {
            mEmptyImage.setVisibility(View.VISIBLE);
            Utility.vibrateAnimation(mContext, mEmptyImage);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = AsyncLayoutInflateManager.getInstance(mContext).inflateView(inflater, container, R.layout.fragment_gallery);

        mEmptyImage = root.findViewById(R.id.tv_empty_image);
        // init recycler gallery by findViewById
        rvGallery = root.findViewById(R.id.rv_gallery_caption);
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        rvGallery.setLayoutManager(gridLayoutManager);
        rvGallery.setAdapter(galleryAdapter);
        rvGallery.setHasFixedSize(true);

        return root;
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
    public void onItemGallerySelected(int position, String path) {

    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        }
        return mImageLoader;
    }
}