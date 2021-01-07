package com.xlteam.socialcaption.ui.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.xlteam.socialcaption.BuildConfig;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.repository.CaptionRepository;
import com.xlteam.socialcaption.external.repository.ICaptionRepository;
import com.xlteam.socialcaption.external.utility.Constant;
import com.xlteam.socialcaption.external.utility.Utility;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.controllers.BaseActivity;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;
import com.xlteam.socialcaption.ui.search.SearchCaptionActivity;

import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.LoaderTaskType.LOAD_ALL;

public class HomePageActivity extends BaseActivity implements HomePageViewMvc.Listener, ICaptionRepository {
    private HomePageViewMvc mViewMvc;
    private CaptionRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getControllerCompositionRoot().getViewMvcFactory().getCategoryViewMvc(null);
        setContentView(mViewMvc.getRootView());
        mRepository = new CaptionRepository(this, this);
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
        mRepository.getAllCaption();
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

    @Override
    public void onNavRateClicked() {
        Uri uri = Uri.parse(Constant.LINK_MARKET + BuildConfig.APPLICATION_ID);
        Intent intentMarket = new Intent(Intent.ACTION_VIEW, uri);
        intentMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(intentMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.LINK_GOOGLE_PLAY + BuildConfig.APPLICATION_ID)));
        }
    }

    @Override
    public void onNavShareClicked() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage = Constant.LINK_GOOGLE_PLAY + BuildConfig.APPLICATION_ID;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)));
        } catch (Exception e) {
            //e.toString();
        }
    }

    @Override
    public void loadResult(int loaderTaskType, List<Caption> result) {
        if (loaderTaskType == LOAD_ALL) {
            mViewMvc.binCaptions(result);
        }
    }
}