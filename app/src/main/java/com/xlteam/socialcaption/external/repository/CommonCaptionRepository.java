package com.xlteam.socialcaption.external.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.xlteam.socialcaption.external.datasource.CaptionDataSource;
import com.xlteam.socialcaption.external.utility.thread.ThreadExecutor;
import com.xlteam.socialcaption.external.utility.utils.SearchQueryUtils;
import com.xlteam.socialcaption.model.CommonCaption;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.sqlite.db.SimpleSQLiteQuery;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.xlteam.socialcaption.external.utility.utils.Constant.LoaderTaskType.LOAD_ALL;
import static com.xlteam.socialcaption.external.utility.utils.Constant.LoaderTaskType.LOAD_BY_CATEGORY_TYPE;
import static com.xlteam.socialcaption.external.utility.utils.Constant.LoaderTaskType.LOAD_BY_SAVED;
import static com.xlteam.socialcaption.external.utility.utils.Constant.LoaderTaskType.SEARCH_BY_CONTENT;

public class CommonCaptionRepository extends AbsRepository {
    private static final int WAIT_LOAD_DELAY = 300;
    private Context mContext;

    public CommonCaptionRepository(Context context, ILoader callback) {
        super(context, callback);
        mContext = context;
    }

    public void insertToDatabase() {
        insertMultipleCaption(CaptionDataSource.getInstance().getDataLocal(mContext));
    }

    public void insertMultipleCaption(List<CommonCaption> listCaption) {
        ThreadExecutor.runOnDatabaseThread(() -> {
            for (CommonCaption caption : listCaption) {
                mDatabase.commonCaptionDAO().insertCaption(caption);
            }
        });
    }

    public void insertSingleCaption(CommonCaption caption) {
        ThreadExecutor.runOnDatabaseThread(() -> mDatabase.commonCaptionDAO().insertCaption(caption));
    }

    public void updateCaptionBySaved(long id, boolean saved) {
        ThreadExecutor.runOnDatabaseThread(() -> mDatabase.commonCaptionDAO().updateCaptionBySaved(id, saved));
    }


    @SuppressLint("CheckResult")
    public void getAllCaption() {
        Single.defer(() -> Single.just(mDatabase.commonCaptionDAO().getAllCaption()))
                .delay(WAIT_LOAD_DELAY, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    sortListCaption(result);
                    execute(LOAD_ALL, result);
                }, error -> execute(LOAD_ALL, Collections.emptyList()));
    }

    @SuppressLint("CheckResult")
    public void getCaptionBySaved(boolean saved) {
        Single.defer(() -> Single.just(mDatabase.commonCaptionDAO().getAllCaptionBySaved(saved)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    sortListCaption(result);
                    execute(LOAD_BY_SAVED, result);
                }, error -> execute(LOAD_BY_SAVED, Collections.emptyList()));
    }

    @SuppressLint("CheckResult")
    public void getCaptionByCategoryType(int categoryType) {
        Single.defer(() -> Single.just(mDatabase.commonCaptionDAO().getCaptionByCategoryType(categoryType)))
                .delay(WAIT_LOAD_DELAY, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    sortListCaption(result);
                    execute(LOAD_BY_CATEGORY_TYPE, result);
                }, error -> execute(LOAD_BY_CATEGORY_TYPE, Collections.emptyList()));
    }

    @SuppressLint("CheckResult")
    public void searchCaptionByContainingContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            Single.defer(() -> {
                StringBuilder query = SearchQueryUtils.getSelectionArgs(content);
                return Single.just(mDatabase.commonCaptionDAO().searchByContainingContent(new SimpleSQLiteQuery(query.toString())));})
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        sortListCaption(result);
                        execute(SEARCH_BY_CONTENT, result);
                    }, error -> execute(SEARCH_BY_CONTENT, Collections.emptyList()));
        } else {
            execute(SEARCH_BY_CONTENT, Collections.emptyList());
        }
    }

    private void sortListCaption(final List<CommonCaption> result) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result.sort((o1, o2) -> Collator.getInstance(new Locale("vi", "VN"))
                    .compare(o1.getContent().toLowerCase(), o2.getContent().toLowerCase()));
        }
    }
}
