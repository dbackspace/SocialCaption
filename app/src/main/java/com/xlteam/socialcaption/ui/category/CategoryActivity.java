package com.xlteam.socialcaption.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

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
        Log.d("binh.ngk", "aaaaaaaaaaaaaa");
    }

    @Override
    public void onTypeCategoryClicked() {
        Log.d("binh.ngk", "aaaaaaaaaaaaaa");
    }

    @Override
    public void onBackClicked() {
        Log.d("binh.ngk", "aaaaaaaaaaaaaa");
        finish();
    }
}