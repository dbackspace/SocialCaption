package com.xlteam.socialcaption.ui.home;

import android.content.Intent;
import android.os.Bundle;

import com.xlteam.socialcaption.common.repository.CaptionRepository;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.controllers.BaseActivity;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;
import com.xlteam.socialcaption.ui.search.SearchCaptionActivity;

import java.util.List;

public class HomePageActivity extends BaseActivity implements HomePageViewMvc.Listener {
    private HomePageViewMvc mViewMvc;
    private CaptionRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getControllerCompositionRoot().getViewMvcFactory().getCategoryViewMvc(null);
        setContentView(mViewMvc.getRootView());
        mRepository = new CaptionRepository(this);
        getCaptionList(0, false);
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
    public void getCaptionList(int categoryNumber, boolean isBookmark) {
        //lấy database rồi gọi mViewMvc.binCaptions(captions);
        List<Caption> listCaption = mRepository.getAllCaption();
        mViewMvc.binCaptions(listCaption);
    }

    @Override
    public void openDrawer() {

    }

    @Override
    public void searchClicked() {
        Intent intent = new Intent(this, SearchCaptionActivity.class);
        startActivity(intent);
    }

    @Override
    public void createCaptionClicked() {
        Intent intent = new Intent(this, EditCaptionActivity.class);
        startActivity(intent);
    }
}