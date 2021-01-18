package com.xlteam.socialcaption.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.repository.RepositoryFactory;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import static com.xlteam.socialcaption.external.utility.Constant.RepositoryType.COMMON_REPOSITORY;

public class SearchDialogFragment extends DialogFragment implements ILoader<CommonCaption>, CaptionAdapter.Callback {

    private TextView tvNumberCaption, tvEmptyCaption;
    private Disposable disposable;
    private CommonCaptionRepository mRepository;
    private Context mContext;
    private CaptionAdapter mAdapter;
    private RecyclerView rvCaption;

    static SearchDialogFragment newInstance() {
        return new SearchDialogFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_SocialCaption);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dialog_search, container, false);
        mRepository = (CommonCaptionRepository) RepositoryFactory.createRepository(mContext, this, COMMON_REPOSITORY);
        tvNumberCaption = root.findViewById(R.id.tv_number_caption);
        tvEmptyCaption = root.findViewById(R.id.tv_empty_caption);
        SearchView searchView = root.findViewById(R.id.search_view);
        rvCaption = root.findViewById(R.id.rv_caption);
        rvCaption = root.findViewById(R.id.rv_caption);
        rvCaption.setLayoutManager(new LinearLayoutManager(mContext));
        root.findViewById(R.id.imgBack).setOnClickListener(v -> dismiss());
        disposable = fromSearchView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(text -> text.toLowerCase().trim())
                .distinctUntilChanged()
                .switchMap(Observable::just)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> mRepository.searchCaptionByContainingContent(query));
        searchView.requestFocus();
        mRepository.getAllCaption(false);
        return root;
    }

    private Observable<String> fromSearchView(SearchView searchView) {
        final PublishSubject<String> subject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                subject.onNext(query);
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
    }

    @Override
    public void updateTotalNumberCaption(int totalCaption) {

    }
}
