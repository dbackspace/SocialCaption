package com.xlteam.socialcaption.ui.saved;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.external.repository.ILoader;
import com.xlteam.socialcaption.external.repository.RepositoryFactory;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;
import com.xlteam.socialcaption.model.CommonCaption;
import com.xlteam.socialcaption.ui.home.CaptionAdapter;
import com.xlteam.socialcaption.ui.home.SelectedCaptionDialogBuilder;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.RepositoryType.COMMON_REPOSITORY;

public class SavedFragment extends Fragment implements ILoader<CommonCaption>, CaptionAdapter.Callback {
    private Context mContext;
    private TextView tvNumberCaption, tvEmptyCaption;
    private RecyclerView rvCaption;
    private CaptionAdapter mAdapter;
    private CommonCaptionRepository mRepository;
    private LinearLayout mLoading;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = AsyncLayoutInflateManager.getInstance(mContext).inflateView(inflater, container, R.layout.fragment_saved);
        mLoading = root.findViewById(R.id.loading_view);
        mRepository = (CommonCaptionRepository) RepositoryFactory.createRepository(mContext, this, COMMON_REPOSITORY);
        tvEmptyCaption = root.findViewById(R.id.tv_empty_caption);
        tvNumberCaption = root.findViewById(R.id.tv_number_caption);
        rvCaption = root.findViewById(R.id.rv_caption);
        rvCaption.setLayoutManager(new LinearLayoutManager(getContext()));
        getCaptionBySaved();
        return root;
    }

    private void getCaptionBySaved() {
        setStatusViewInLoadingProgress(true);
        mRepository.getCaptionBySaved(true);
    }

    @Override
    public void loadResult(int loaderTaskType, List<CommonCaption> captions) {
        setStatusViewInLoadingProgress(false);
        tvNumberCaption.setVisibility(View.VISIBLE);
        tvNumberCaption.setText(mContext.getString(R.string.number_captions, captions.size()));
        if (captions.isEmpty()) {
            tvEmptyCaption.setVisibility(View.VISIBLE);
            rvCaption.setVisibility(View.GONE);
        } else {
            tvEmptyCaption.setVisibility(View.GONE);
            rvCaption.setVisibility(View.VISIBLE);
            mAdapter = new CaptionAdapter(mContext, captions, this, null, false);
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
    public void onBookmarkClick(long id, boolean saved) {
        mRepository.updateCaptionBySaved(id, saved);
    }

    private void setStatusViewInLoadingProgress(boolean inProgress) {
        tvNumberCaption.setVisibility(inProgress ? View.INVISIBLE : View.VISIBLE);
        mLoading.setVisibility(inProgress ? View.VISIBLE : View.GONE);
    }
}