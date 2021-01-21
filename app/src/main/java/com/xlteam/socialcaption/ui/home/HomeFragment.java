package com.xlteam.socialcaption.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.repository.RepositoryFactory;
import com.xlteam.socialcaption.external.utility.Constant;
import com.xlteam.socialcaption.model.CommonCaption;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.RepositoryType.COMMON_REPOSITORY;

public class HomeFragment extends Fragment implements ILoader<CommonCaption>, CaptionAdapter.Callback {
    private static final String TAG = "HomeFragment";

    private CommonCaptionRepository mRepository;
    private Context mContext;
    private Activity mActivity;
    private RecyclerView rvCaption;
    private TextView tvNumberCaption, tvBookmarkCategory, tvEmptyCaption;
    private TabLayout tabLayoutCategory;
    private CaptionAdapter mAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRepository = (CommonCaptionRepository) RepositoryFactory.createRepository(mContext, this, COMMON_REPOSITORY);
        tvNumberCaption = root.findViewById(R.id.tv_number_caption);
        tabLayoutCategory = root.findViewById(R.id.tab_layout_category);
        tvBookmarkCategory = root.findViewById(R.id.tv_bookmark_select);
        tvEmptyCaption = root.findViewById(R.id.tv_empty_caption);
        tabLayoutCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getCaptionList(tab.getPosition(), tvBookmarkCategory.isActivated());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tvBookmarkCategory.setOnClickListener(view1 -> {
            boolean isBookmark;
            if (tvBookmarkCategory.isActivated()) {
                tvBookmarkCategory.setTextColor(mContext.getColor(R.color.color_25));
                isBookmark = false;
            } else {
                tvBookmarkCategory.setTextColor(mContext.getColor(R.color.white));
                isBookmark = true;
            }
            tvBookmarkCategory.setActivated(isBookmark);
            getCaptionList(tabLayoutCategory.getSelectedTabPosition(), isBookmark);
        });

        rvCaption = root.findViewById(R.id.rv_caption);
        rvCaption.setLayoutManager(new LinearLayoutManager(getContext()));
        mRepository.getAllCaption(false);
        return root;
    }

    @Override
    public void loadResult(int loaderTaskType, List<CommonCaption> captions) {
        tvNumberCaption.setText(mContext.getString(R.string.number_captions, captions.size()));
        if (captions.isEmpty()) {
            tvEmptyCaption.setVisibility(View.VISIBLE);
            rvCaption.setVisibility(View.GONE);
        } else {
            tvEmptyCaption.setVisibility(View.GONE);
            rvCaption.setVisibility(View.VISIBLE);
            mAdapter = new CaptionAdapter(mContext, captions, this);
            rvCaption.setAdapter(mAdapter);
        }
    }

    public void getCaptionList(int categoryNumber, boolean isBookmark) {
        //lấy database rồi gọi mViewMvc.binCaptions(captions);
        switch (categoryNumber) {
            case 0: //ALL
                mRepository.getAllCaption(isBookmark);
                break;
            case 1:
                //get firebase caption đang hot
                break;
            case 2:
                mRepository.getCaptionBySavedAndCategoryType(Constant.TYPE_THA_THINH, isBookmark);
                break;
            case 3:
                mRepository.getCaptionBySavedAndCategoryType(Constant.TYPE_CUOC_SONG, isBookmark);
                break;
            case 4:
                mRepository.getCaptionBySavedAndCategoryType(Constant.TYPE_BAN_BE, isBookmark);
                break;
            case 5:
                mRepository.getCaptionBySavedAndCategoryType(Constant.TYPE_KHAC, isBookmark);
                break;
        }
    }

    @Override
    public void openCaptionPreview(CommonCaption caption, int position) {
        BottomSheetDialog dialog = new SelectedCaptionDialogBuilder(mContext, caption).build();
        dialog.setOnCancelListener(dialogInterface -> {
            if (mAdapter != null) {
                mAdapter.notifyItem(position);
            }
        });
        dialog.show();
    }

    @Override
    public void onBookmarkClick(long id, boolean saved, int positionRemove) {
        mRepository.updateCaptionBySaved(id, saved);
        if (tvBookmarkCategory.isActivated()) {
            mAdapter.removeCaption(positionRemove);
        }
    }

    @Override
    public void updateTotalNumberCaption(int totalCaption) {
        if (totalCaption == 0) {
            tvEmptyCaption.setVisibility(View.VISIBLE);
            rvCaption.setVisibility(View.GONE);
        }
        tvNumberCaption.setText(mContext.getString(R.string.number_captions, totalCaption));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SearchDialogFragment searchDialogFragment = new SearchDialogFragment(()
                    -> getCaptionList(tabLayoutCategory.getSelectedTabPosition(), tvBookmarkCategory.isActivated()));
            searchDialogFragment.show(fragmentTransaction, "dialog");
        } else if (item.getItemId() == R.id.action_create_picture) {
            Intent intent = new Intent(mContext, EditCaptionActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
