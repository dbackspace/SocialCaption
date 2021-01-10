package com.xlteam.socialcaption.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

public class GalleryFragment extends Fragment {
    private RecyclerView rvGallery;
    private List<CommonCaption> commonCaptionList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        // init recycler gallery by findViewById
        rvGallery = root.findViewById(R.id.rv_gallery_caption);


        return root;
    }
}