package com.xlteam.socialcaption.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.common.utility.Constant;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

public class CategoryViewMvcImpl extends BaseObservableViewMvc<CategoryViewMvc.Listener> implements
        CategoryViewMvc {
    private final TextView tvTypeCategory;
    private int mTypeCategory, mCategoryNumber;
    private CategoryNameListAdapter adapter;
    private RecyclerView rvCategoryName;

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
            PopupMenu popupMenu = new PopupMenu(getContext(), tvTypeCategory);
            popupMenu.getMenuInflater().inflate(R.menu.type_category_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                int type;
                switch (item.getItemId()) {
                    case R.id.type_all:
                        type = Constant.TYPE_ALL;
                        tvTypeCategory.setText("Tất cả");
                        break;
                    case R.id.type_post:
                        type = Constant.TYPE_POST;
                        tvTypeCategory.setText("Đã đăng");
                        break;
                    case R.id.type_bookmark:
                        type = Constant.TYPE_BOOKMARK;
                        tvTypeCategory.setText("Đã lưu");
                        break;
                    default:
                        type = Constant.TYPE_ALL;
                        break;
                }
                if (mTypeCategory != type) {
                    mTypeCategory = type;
                    for (Listener listener : getListeners()) {
                        listener.getDataOnFirebase(mCategoryNumber, mTypeCategory);
                    }
                }
                return true;
            });
            popupMenu.show();
        });

        rvCategoryName = findViewById(R.id.rv_category_list);
        rvCategoryName.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new CategoryNameListAdapter(getContext(), categoryNumber -> {
            mCategoryNumber = categoryNumber;
            for (Listener listener : getListeners()) {
                listener.getDataOnFirebase(mCategoryNumber, mTypeCategory);
            }
        });
        rvCategoryName.setAdapter(adapter);
    }

    @Override
    public void binCaptions() {

    }

    @Override
    public void binNumberCategory(int number) {
        adapter.setCategoryNumber(number + 1);// do thêm "tất cả" vào số 0 trong adapter
        rvCategoryName.scrollToPosition(number + 1);
    }

    @Override
    public void binTypeCategory(int type) {
        mTypeCategory = type;
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
    }

}
