package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;

public class HomeFragment extends Fragment {
    private Context mContext;
    private TabLayout tabLayoutCategory;
    private ViewPager viewPager;
    private SlidePagerAdapter mAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = AsyncLayoutInflateManager.getInstance(mContext).inflateView(inflater, container, R.layout.fragment_home);
        tabLayoutCategory = root.findViewById(R.id.tab_layout_category);
        viewPager = root.findViewById(R.id.viewPager);
        mAdapter = new SlidePagerAdapter(getChildFragmentManager(), mContext);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayoutCategory.setupWithViewPager(viewPager);
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
            return new PictureListFragment(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 5;
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
