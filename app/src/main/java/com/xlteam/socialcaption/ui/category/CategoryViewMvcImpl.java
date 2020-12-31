package com.xlteam.socialcaption.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.common.utility.Constant;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

public class CategoryViewMvcImpl extends BaseObservableViewMvc<CategoryViewMvc.Listener> implements
        CategoryViewMvc {
    private TextView tvTypeCategory;

    public CategoryViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.activity_category, parent, false);
        setRootView(view);
        findViewById(R.id.image_search).setOnClickListener(view1 -> {
            for (Listener listener : getListeners()) {
                listener.onSearchViewClicked();
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(view1 -> {
            for (Listener listener : getListeners()) {
                listener.onBackClicked();
            }
        });
        tvTypeCategory = findViewById(R.id.tv_type_view_list);
        tvTypeCategory.setOnClickListener(view1 -> {
            for (Listener listener : getListeners()) {
                listener.onTypeCategoryClicked(tvTypeCategory);
            }
        });

        RecyclerView rvCategoryName = findViewById(R.id.rv_category_list);
        rvCategoryName.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CategoryNameListAdapter adapter = new CategoryNameListAdapter(getContext());
        rvCategoryName.setAdapter(adapter);
    }

    @Override
    public void binCaptions() {

    }

    @Override
    public void updateTypeCategory(int type) {
        switch (type) {
            case Constant.TYPE_ALL:
                tvTypeCategory.setText("Tất cả");
                break;
            case Constant.TYPE_POST:
                tvTypeCategory.setText("Đã đăng");
                break;
            case Constant.TYPE_BOOKMARK:
                tvTypeCategory.setText("Đã lưu");
                break;
            default:
                break;
        }
        for (Listener listener : getListeners()) {
            listener.getDataOnFirebase(type, type);
        }
    }
}
