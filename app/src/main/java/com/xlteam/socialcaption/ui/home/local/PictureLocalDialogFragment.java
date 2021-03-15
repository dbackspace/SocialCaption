package com.xlteam.socialcaption.ui.home.local;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.utils.Utility;

import java.util.Objects;

public class PictureLocalDialogFragment extends DialogFragment {
    private Context mContext;
    private Activity mActivity;
    private RecyclerView rvPicture;
    private ImageView imgBack;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_SocialCaption);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_dialog_local, container, false);
        rvPicture = root.findViewById(R.id.rv_picture_local);

        // cache recyclerview
        rvPicture.setItemViewCacheSize(25);
        rvPicture.setDrawingCacheEnabled(true);
        rvPicture.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvPicture.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvPicture.setAdapter(new PictureLocalAdapter(getActivity(), mContext, Utility.getAllShownImagesPath(mActivity)));

        imgBack = root.findViewById(R.id.image_back);
        imgBack.setOnClickListener(v -> dismiss());
        return root;
    }
}
