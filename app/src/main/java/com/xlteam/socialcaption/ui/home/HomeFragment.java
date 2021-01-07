package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.CaptionRepository;
import com.xlteam.socialcaption.external.repository.ICaptionRepository;
import com.xlteam.socialcaption.model.Caption;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_ALL;

public class HomeFragment extends Fragment implements ICaptionRepository, CaptionAdapter.Callback {
    private CaptionRepository mRepository;
    private Context mContext;
    private RecyclerView rvCaption;
    private TextView tvNumberCaption;
    private TabLayout tabLayoutCategory;
    private TextView tvBookmarkCategory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRepository = new CaptionRepository(mContext, this);
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
    public void loadResult(int loaderTaskType, List<Caption> result) {
        if (loaderTaskType == LOAD_ALL) {
            binCaptions(result);
        }
    }

    public void getCaptionList(int categoryNumber, boolean isBookmark) {
        //lấy database rồi gọi mViewMvc.binCaptions(captions);
        mRepository.getAllCaption();
    }

    public void binCaptions(List<Caption> captions) {
        tvNumberCaption.setText(mContext.getString(R.string.number_captions, captions.size()));
        CaptionAdapter adapter = new CaptionAdapter(getContext(), captions, this);
        rvCaption.setAdapter(adapter);
    }

    @Override
    public void openCaptionPreview(Caption caption) {
        PreviewCaptionDialogBuilder previewCaptionDialogBuilder = new PreviewCaptionDialogBuilder(getContext(), caption);
        previewCaptionDialogBuilder.build().show();
    }
}