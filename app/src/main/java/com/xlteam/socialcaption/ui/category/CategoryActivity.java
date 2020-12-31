package com.xlteam.socialcaption.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xlteam.socialcaption.common.utility.Constant;
import com.xlteam.socialcaption.firebase.FirebaseController;
import com.xlteam.socialcaption.firebase.FirebaseListener;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.controllers.BaseActivity;

import java.util.ArrayList;

public class CategoryActivity extends BaseActivity implements CategoryViewMvc.Listener {
    private Context mContext;
    private CategoryViewMvc mViewMvc;
    private FirebaseController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getControllerCompositionRoot().getViewMvcFactory().getCategoryViewMvc(null);
        setContentView(mViewMvc.getRootView());
        mController = new FirebaseController();

        Intent intent = getIntent();
        int categoryNumber = intent.getIntExtra(Constant.EXTRA_CATEGORY_NUMBER, -1);
        int typeCategory = intent.getIntExtra(Constant.EXTRA_TYPE_CATEGORY, 0);
        mViewMvc.binNumberCategory(categoryNumber);
        mViewMvc.binTypeCategory(typeCategory);
        getDataOnFirebase(categoryNumber, typeCategory);
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

    @Override
    public void onBackClicked() {
        finish();
    }

    @Override
    public void getDataOnFirebase(int categoryNumber, int typeCategory) {
        if (typeCategory == Constant.TYPE_ALL) {
            mController.getCaptionByCategoryNumber(categoryNumber, new FirebaseListener<ArrayList<Caption>>() {
                @Override
                public void onResponse(ArrayList<Caption> captions) {
                    mViewMvc.binCaptions(captions);
                }

                @Override
                public void onError() {

                }
            });
        }
    }
}