package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.repository.RepositoryFactory;
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.utils.Constant.RepositoryType.COMMON_REPOSITORY;

public class CaptionListFragment extends Fragment implements ILoader<CommonCaption>, CaptionAdapter.Callback {
    private Context mContext;
    private TextView tvNumberCaption, tvEmptyCaption;
    private RecyclerView rvCaption;
    private int mCategoryNumber;
    private CaptionAdapter mAdapter;
    private CommonCaptionRepository mRepository;
    private LinearLayout mLoading;

    public CaptionListFragment(int categoryNumber) {
        mCategoryNumber = categoryNumber;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = AsyncLayoutInflateManager.getInstance(mContext).inflateView(inflater, container, R.layout.fragment_caption_list);
        mLoading = root.findViewById(R.id.loading_view);
        mRepository = (CommonCaptionRepository) RepositoryFactory.createRepository(mContext, this, COMMON_REPOSITORY);
        tvEmptyCaption = root.findViewById(R.id.tv_empty_caption);
        tvNumberCaption = root.findViewById(R.id.tv_number_caption);
        rvCaption = root.findViewById(R.id.rv_caption);
        rvCaption.setLayoutManager(new LinearLayoutManager(getContext()));
        getCaptionList(mCategoryNumber);
        return root;
    }

    private void getCaptionList(int categoryNumber) {
        //lấy database rồi gọi mViewMvc.binCaptions(captions);
        setStatusViewInLoadingProgress(true);
        switch (categoryNumber) {
            case 0: //ALL
                mRepository.getAllCaption();
                break;
            case 1:
                //get firebase caption đang hot
                break;
            case 2:
                mRepository.getCaptionByCategoryType(Constant.TYPE_THA_THINH);
                break;
            case 3:
                mRepository.getCaptionByCategoryType(Constant.TYPE_CUOC_SONG);
                break;
            case 4:
                mRepository.getCaptionByCategoryType(Constant.TYPE_BAN_BE);
                break;
            case 5:
                mRepository.getCaptionByCategoryType(Constant.TYPE_KHAC);
                break;
        }
    }

    @Override
    public void onBookmarkClick(long id, boolean saved) {
        mRepository.updateCaptionBySaved(id, saved);
    }

    @Override
    public void loadResult(int loaderTaskType, List<CommonCaption> captions) {
        setStatusViewInLoadingProgress(false);
        tvNumberCaption.setVisibility(View.VISIBLE);
        tvNumberCaption.setText(mContext.getString(R.string.number_captions, captions.size()));
        if (captions.isEmpty()) {
            tvEmptyCaption.setVisibility(View.VISIBLE);
            Utility.vibrateAnimation(mContext, tvEmptyCaption);
            rvCaption.setVisibility(View.GONE);
        } else {
            tvEmptyCaption.setVisibility(View.GONE);
            rvCaption.setVisibility(View.VISIBLE);
            mAdapter = new CaptionAdapter(mContext, captions, this, null, false);
            rvCaption.setAdapter(mAdapter);
        }
    }

    private void setStatusViewInLoadingProgress(boolean inProgress) {
        tvNumberCaption.setVisibility(inProgress ? View.INVISIBLE : View.VISIBLE);
        mLoading.setVisibility(inProgress ? View.VISIBLE : View.GONE);
    }
}
