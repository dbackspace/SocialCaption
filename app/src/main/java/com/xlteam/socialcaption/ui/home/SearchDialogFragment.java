package com.xlteam.socialcaption.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.repository.RepositoryFactory;
import com.xlteam.socialcaption.external.utility.utils.SearchQueryUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import static com.xlteam.socialcaption.external.utility.utils.Constant.RepositoryType.COMMON_REPOSITORY;

public class SearchDialogFragment extends DialogFragment implements ILoader<CommonCaption>, CaptionAdapter.Callback {

    private TextView tvNumberCaption, tvEmptyCaption;
    private Disposable disposable;
    private CommonCaptionRepository mRepository;
    private Context mContext;
    private CaptionAdapter mAdapter;
    private RecyclerView rvCaption;
    private Callback mCallback;
    private SearchView mSearchView;
    private LinearLayout mLoading;
    private String mQueryText = "";
    private static final int REQUEST_DELAY_TIMEOUT = 300; // Thời gian accept dữ liệu nhập vào
    private static final int WAIT_DELAY_TIMEOUT = 100; // Thời gian delay search và hiển thị kết quả

    public interface Callback {
        void onBookmarkClicked();
    }

    public SearchDialogFragment() {

    }

    public SearchDialogFragment(Callback listener) {
        mCallback = listener;
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
        final View root = AsyncLayoutInflateManager.getInstance(mContext).inflateView(inflater, container, R.layout.fragment_dialog_search);
        mLoading = root.findViewById(R.id.loading_view);
        mRepository = (CommonCaptionRepository) RepositoryFactory.createRepository(mContext, this, COMMON_REPOSITORY);
        tvNumberCaption = root.findViewById(R.id.tv_number_caption);
        tvEmptyCaption = root.findViewById(R.id.tv_empty_caption);
        Utility.vibrateAnimation(mContext, tvEmptyCaption);
        mSearchView = root.findViewById(R.id.search_view);
        rvCaption = root.findViewById(R.id.rv_caption);
        rvCaption.setLayoutManager(new LinearLayoutManager(mContext));
        root.findViewById(R.id.imgBack).setOnClickListener(v -> dismiss());
        disposable = initRxSearchView(mSearchView);
        mSearchView.requestFocus();
        tvNumberCaption.setText(mContext.getString(R.string.number_captions, 0));
        return root;
    }

    /**
     * Quá trình tìm kiếm sẽ ở đây (disposable = observable + operator + observer)
     * - debounce(): chờ cho đến khi hết khoảng thời gian được cung cấp, nếu có ký tự nào gõ
     * thêm trong thời điểm đó thì sẽ bỏ qua những chữ đằng trước và lại reset khoảng thời gian chờ. Chỉ
     * khi hết REQUEST_DELAY_TIMEOUT mà không có ký tự nào được gõ thêm thì nó sẽ dùng đoạn text cuối để search.
     * - delay(): đợi một khoảng thời gian nhỏ để hiện cái progress loading trước khi hiển thị kết quả.
     * - distinctUntilChanged(): tránh tạo ra các request trùng nhau. Ví dụ khi người dùng thêm ký tự xong xóa ký tự
     * đó xong lại thêm, thì nó sẽ lấy chuỗi cuối cùng độc nhất thay vì request hết tất cả.
     * - switchMap(): hiện kết quả query gần nhất được gõ vào, bỏ qua tất cả các kết quả query khác, bổ trợ cho debounce().
     * - observerOn(): Thực hiện việc nhận dữ liệu qua mainThread. Ở đây do subcribe đã xử lý thread và post lên mainThread
     * trong mRepository.searchCaptionByContainingContent() nên không cần cho nó chạy ngầm ở background thread nữa.
     *
     * @param searchView
     * @return
     */
    private Disposable initRxSearchView(SearchView searchView) {
        return fromSearchView(searchView)
                .debounce(REQUEST_DELAY_TIMEOUT, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap(query -> Observable.just(query))
                .subscribe(query -> {
                    mQueryText = query;
                    mRepository.searchCaptionByContainingContent(query);
                });
    }


    /**
     * Publish Subject là 1 loại đặc biệt, có cả tính chất của Observable và Observer,
     * tức là vừa phát vừa nhận. Ở đây dùng để tạo ra 1 RxSearchViewObservable nhằm phát
     * các text nhận được sau khi onQueryTextChange và onQueryTextSubmit.
     *
     * @param searchView
     * @return
     */
    private Observable<String> fromSearchView(SearchView searchView) {
        final PublishSubject<String> subject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String validQuery = SearchQueryUtils.checkSkipSpecialChar(newText.toLowerCase().trim());
                setStatusViewInLoadingProgress(true);
                subject.onNext(validQuery);
                return false;
            }
        });
        return subject;
    }

    @Override
    public void loadResult(int loaderTaskType, List<CommonCaption> captions) {
        setStatusViewInLoadingProgress(false);
        tvNumberCaption.setText(mContext.getString(R.string.number_captions, captions.size()));
        if (captions.isEmpty()) {
            tvEmptyCaption.setVisibility(View.VISIBLE);
            Utility.vibrateAnimation(mContext, tvEmptyCaption);
            rvCaption.setVisibility(View.GONE);
        } else {
            tvEmptyCaption.setVisibility(View.GONE);
            rvCaption.setVisibility(View.VISIBLE);
            mAdapter = new CaptionAdapter(mContext, captions, this, mQueryText, true);
            rvCaption.setAdapter(mAdapter);
        }
    }

    @Override
    public void onBookmarkClick(long id, boolean saved) {
        mRepository.updateCaptionBySaved(id, saved);
        mCallback.onBookmarkClicked();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void setStatusViewInLoadingProgress(boolean inProgress) {
        tvNumberCaption.setVisibility(inProgress ? View.INVISIBLE : View.VISIBLE);
        mLoading.setVisibility(inProgress ? View.VISIBLE : View.GONE);
    }
}
