package com.xlteam.textonpicture.ui.home.firebase;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xlteam.textonpicture.R;

import java.util.Objects;

public class PictureFirebaseDialogFragment extends DialogFragment {
    private Context mContext;
    private TabLayout tabLayoutCategory;
    private ViewPager viewPager;
    private SlidePagerAdapter mAdapter;
    private ImageView imgBack;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_TextOnPicture);
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
        final View root = inflater.inflate(R.layout.fragment_dialog_firebase, container, false);
        tabLayoutCategory = root.findViewById(R.id.tab_layout_category);
        viewPager = root.findViewById(R.id.viewPager);
        mAdapter = new SlidePagerAdapter(getChildFragmentManager(), mContext);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayoutCategory.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayoutCategory.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayoutCategory.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(10, 0, 10, 0);
            tab.requestLayout();
        }
        imgBack = root.findViewById(R.id.image_back);
        imgBack.setOnClickListener(v -> dismiss());
        return root;
    }

    public static class SlidePagerAdapter extends FragmentStatePagerAdapter {
        private Context mContext;

        SlidePagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new PictureFirebaseListFragment(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContext.getString(R.string.mood);
                case 1:
                    return mContext.getString(R.string.love);
                case 2:
                    return mContext.getString(R.string.nature);
                case 3:
                    return mContext.getString(R.string.black_white);
            }
            return mContext.getString(R.string.mood);
        }


    }
}
