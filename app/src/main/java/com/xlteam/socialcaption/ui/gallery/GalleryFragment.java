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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.PrefUtils;
import com.xlteam.socialcaption.model.UserCaption;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import java.util.List;

public class GalleryFragment extends Fragment implements GalleryAdapter.GallerySelectCallback {
    private RecyclerView rvGallery;
    private List<UserCaption> mUserCaptions;
    private Context mContext;
    private ImageLoader mImageLoader;
    private StaggeredGridLayoutManager _staGridLayoutManager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        _staGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        // init recycler gallery by findViewById
        rvGallery = root.findViewById(R.id.rv_gallery_caption);
        rvGallery.setLayoutManager(_staGridLayoutManager);
        rvGallery.setAdapter(new GalleryAdapter(PrefUtils.getListItemGallery(mContext), getImageLoader(), this));
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
    public void onGallerySelected(int positionSelected) {

    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        }
        return mImageLoader;
    }
}