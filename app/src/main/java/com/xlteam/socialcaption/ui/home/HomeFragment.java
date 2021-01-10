package com.xlteam.socialcaption.ui.home;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.repository.RepositoryFactory;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_ALL;
import static com.xlteam.socialcaption.external.utility.Constant.RepositoryType.COMMON_REPOSITORY;

public class HomeFragment extends Fragment implements ILoader<CommonCaption>, CaptionAdapter.Callback {
    private static final String TAG = "HomeFragment";

    private CommonCaptionRepository mRepository;
    private Context mContext;
    private Activity mActivity;
    private RecyclerView rvCaption;
    private TextView tvNumberCaption;
    private TabLayout tabLayoutCategory;
    private TextView tvBookmarkCategory;

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
        getCaptionList(LOAD_ALL, false);
        return root;
    }

    @Override
    public void loadResult(int loaderTaskType, List<CommonCaption> result) {
        if (loaderTaskType == LOAD_ALL) {
            binCaptions(result);
        }
    }

    public void getCaptionList(int categoryNumber, boolean isBookmark) {
        //lấy database rồi gọi mViewMvc.binCaptions(captions);
        if (categoryNumber == LOAD_ALL) {
            mRepository.getAllCaption(isBookmark);
        } else {
            mRepository.getCaptionBySavedAndCategoryType(categoryNumber, isBookmark);
        }
    }

    public void binCaptions(List<CommonCaption> captions) {
        tvNumberCaption.setText(mContext.getString(R.string.number_captions, captions.size()));
        CaptionAdapter adapter = new CaptionAdapter(getContext(), captions, this);
        rvCaption.setAdapter(adapter);
    }

    @Override
    public void openCaptionPreview(CommonCaption caption) {
        PreviewCaptionDialogBuilder previewCaptionDialogBuilder = new PreviewCaptionDialogBuilder(getContext(), caption);
        previewCaptionDialogBuilder.build().show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                tvBookmarkCategory.setVisibility(View.GONE);
                tabLayoutCategory.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                tvBookmarkCategory.setVisibility(View.VISIBLE);
                tabLayoutCategory.setVisibility(View.VISIBLE);
                return true;
            }
        });
        SearchManager searchManager = (SearchManager) mActivity.getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(mActivity.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mRepository.searchCaptionByContainingContent(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mRepository.searchCaptionByContainingContent(newText);
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }
}