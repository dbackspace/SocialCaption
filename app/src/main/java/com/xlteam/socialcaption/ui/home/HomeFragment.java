package com.xlteam.socialcaption.ui.home;

import android.annotation.SuppressLint;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.repository.RepositoryFactory;
import com.xlteam.socialcaption.external.utility.Constant;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

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
    private MenuItem searchItem;

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
        if (tvBookmarkCategory.isActivated() && !searchItem.isActionViewExpanded()) {
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
        inflater.inflate(R.menu.menu_toolbar, menu);

        searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                tvBookmarkCategory.setVisibility(View.GONE);
                tabLayoutCategory.setVisibility(View.GONE);
                tvEmptyCaption.setVisibility(View.VISIBLE);
                tvNumberCaption.setText(mContext.getString(R.string.number_captions, 0));
                mAdapter.setCurrentListCaptions(new ArrayList<>());
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                tvBookmarkCategory.setVisibility(View.VISIBLE);
                tabLayoutCategory.setVisibility(View.VISIBLE);
                getCaptionList(tabLayoutCategory.getSelectedTabPosition(), tvBookmarkCategory.isActivated());
                return true;
            }
        });
        SearchManager searchManager = (SearchManager) mActivity.getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(mActivity.getComponentName()));
            fromSearchView(searchView)
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .filter(text -> !text.trim().isEmpty())
                    .map(text -> text.toLowerCase().trim())
                    .distinctUntilChanged()
                    .switchMap(text -> Observable.just(text))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(query -> mRepository.searchCaptionByContainingContent(query));
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private Observable<String> fromSearchView (SearchView searchView) {
        final PublishSubject<String> subject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                subject.onComplete();
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                subject.onNext(newText);
                return false;
            }
        });
        return subject;
    }
}