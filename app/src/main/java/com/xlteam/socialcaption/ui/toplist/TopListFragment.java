package com.xlteam.socialcaption.ui.toplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xlteam.socialcaption.R;

public class TopListFragment extends Fragment {
    public static TopListFragment newInstance() {
        return new TopListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_list, container, false);
        //code ui
        return view;
    }
}
