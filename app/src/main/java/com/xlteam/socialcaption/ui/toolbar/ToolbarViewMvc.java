package com.xlteam.socialcaption.ui.toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.ui.common.views.BaseViewMvc;

public class ToolbarViewMvc extends BaseViewMvc {
    public interface NavigateUpClickedListener {
        void onNavigateUpClicked();
    }

    public interface SearchButtonClickedListener {
        void onSearchButtonClicked();
    }

    private NavigateUpClickedListener mNavigateUpClickedListener;
    private SearchButtonClickedListener mSearchButtonClickedListener;

    private final TextView mTxtToolbarTitle;
    private final ImageButton btnSearch;
    private final ImageButton btnNavigateUp;

    public ToolbarViewMvc(LayoutInflater inflater, @Nullable ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_toolbar, parent, false));

        // init view
        mTxtToolbarTitle = findViewById(R.id.txt_toolbar_title);
        btnNavigateUp = findViewById(R.id.btn_back);
        btnSearch = findViewById(R.id.btn_search);

        btnNavigateUp.setOnClickListener((v) -> {
            mNavigateUpClickedListener.onNavigateUpClicked();
        });

        btnSearch.setOnClickListener((v) -> {
            mSearchButtonClickedListener.onSearchButtonClicked();
        });
    }

    public void enableNavigateUpButtonAndListen(NavigateUpClickedListener navigateUpClickedListener) {
        if (mNavigateUpClickedListener == null) {
            mNavigateUpClickedListener = navigateUpClickedListener;
        }
        btnNavigateUp.setVisibility(View.VISIBLE);
    }

    public void enableSearchButtonAndListen(SearchButtonClickedListener searchButtonClickedListener) {
        if (mSearchButtonClickedListener == null) {
            mSearchButtonClickedListener = searchButtonClickedListener;
        }
        btnSearch.setVisibility(View.VISIBLE);
    }

    public void disableNavigateUpButton() {
        btnNavigateUp.setVisibility(View.INVISIBLE);
    }

    public void disableSearchButton() {
        btnSearch.setVisibility(View.INVISIBLE);
    }

    public void setTitle(String title) {
        mTxtToolbarTitle.setText(title);
    }
}
