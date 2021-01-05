package com.xlteam.socialcaption.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

import java.util.List;

public class HomePageViewMvcImpl extends BaseObservableViewMvc<HomePageViewMvc.Listener> implements
        HomePageViewMvc, CaptionAdapter.Callback {
    private final TextView tvBookmarkCategory;
    private RecyclerView rvCaption;
    private TextView tvNumberCaption;
    private TabLayout tabLayoutCategory;

    public HomePageViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.activity_category, parent, false);
        setRootView(view);

        findViewById(R.id.image_more).setOnClickListener(view1 -> {
            for (Listener listener : getListeners()) {
                listener.openDrawer();
            }
        });
        findViewById(R.id.image_search).setOnClickListener(view1 -> {
            for (Listener listener : getListeners()) {
                listener.searchClicked();
            }
        });
        findViewById(R.id.image_create).setOnClickListener(view1 -> {
            for (Listener listener : getListeners()) {
                listener.createCaptionClicked();
            }
        });
        tvNumberCaption = findViewById(R.id.tv_number_caption);
        tabLayoutCategory = findViewById(R.id.tab_layout_category);
        tvBookmarkCategory = findViewById(R.id.tv_bookmark_select);
        tabLayoutCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (Listener listener : getListeners()) {
                    listener.getCaptionList(tab.getPosition(), tvBookmarkCategory.isActivated());
                }
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
                tvBookmarkCategory.setTextColor(getContext().getColor(R.color.color_25));
                isBookmark = false;
            } else {
                tvBookmarkCategory.setTextColor(getContext().getColor(R.color.white));
                isBookmark = true;
            }
            tvBookmarkCategory.setActivated(isBookmark);
            for (Listener listener : getListeners()) {
                listener.getCaptionList(1, isBookmark);
            }
        });

        rvCaption = findViewById(R.id.rv_caption);
        rvCaption.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void binCaptions(List<Caption> captions) {
        tvNumberCaption.setText(getContext().getString(R.string.number_captions, captions.size()));
        CaptionAdapter adapter = new CaptionAdapter(getContext(), captions, this);
        rvCaption.setAdapter(adapter);
    }

    @Override
    public void openCaptionPreview(Caption caption) {
        PreviewCaptionDialogBuilder previewCaptionDialogBuilder = new PreviewCaptionDialogBuilder(getContext(), caption);
        previewCaptionDialogBuilder.build().show();
    }
}
