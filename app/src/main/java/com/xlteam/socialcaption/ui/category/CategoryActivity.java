package com.xlteam.socialcaption.ui.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.common.utility.Constant;
import com.xlteam.socialcaption.firebase.FirebaseController;
import com.xlteam.socialcaption.ui.common.controllers.BaseActivity;

public class CategoryActivity extends BaseActivity implements CategoryViewMvc.Listener {
    private Context mContext;
    private CategoryViewMvc mViewMvc;
    private FirebaseController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getControllerCompositionRoot().getViewMvcFactory().getCategoryViewMvc(null);
        setContentView(mViewMvc.getRootView());

        Intent intent = getIntent();

    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onSearchViewClicked() {
        //open search screen
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onTypeCategoryClicked(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.type_category_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.type_all:
                    mViewMvc.updateTypeCategory(Constant.TYPE_ALL);
                    break;
                case R.id.type_post:
                    mViewMvc.updateTypeCategory(Constant.TYPE_POST);
                    break;
                case R.id.type_bookmark:
                    mViewMvc.updateTypeCategory(Constant.TYPE_BOOKMARK);
                    break;
                default:
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    @Override
    public void onBackClicked() {
        finish();
    }

    @Override
    public void getDataOnFirebase(int categoryNumber, int typeCategory) {

    }
}