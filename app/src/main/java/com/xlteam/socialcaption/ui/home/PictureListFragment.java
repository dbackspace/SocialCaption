package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;

public class PictureListFragment extends Fragment {
    private Context mContext;
    private RecyclerView rvPicture;
    private int mCategoryNumber;

    public PictureListFragment() {

    }

    public PictureListFragment(int numberCategory) {
        mCategoryNumber = numberCategory;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_caption_list, container, false);
        rvPicture = root.findViewById(R.id.rv_picture);

        // cache recyclerview
        rvPicture.setItemViewCacheSize(25);
        rvPicture.setDrawingCacheEnabled(true);
        rvPicture.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        rvPicture.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvPicture.setAdapter(new PictureAdapter(mCategoryNumber));

        return root;
    }
}
