package com.xlteam.socialcaption.ui.upload;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.Category;
import com.xlteam.socialcaption.model.User;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

import java.util.List;

public class DialogUploadCaptionsViewMvcImpl extends BaseObservableViewMvc<DialogUploadCaptionsViewMvc.Listener>
        implements DialogUploadCaptionsViewMvc {

    private final ImageButton mBtnBack;
    private final TextView mBtnPostCaption;
    private final ImageView mImgUser;
    private final TextView mTvUserName;
    private final Spinner mSpinnerCategory;
    private final EditText mEdtCaption;
    private final RecyclerView mRvImgBackgroud;

    public DialogUploadCaptionsViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.dialog_upload, parent, false));

        // init view
        mBtnBack = findViewById(R.id.btn_back);
        mBtnPostCaption = findViewById(R.id.btn_search);
        mImgUser = findViewById(R.id.img_avatar_user);
        mTvUserName = findViewById(R.id.tv_user_name);
        mSpinnerCategory = findViewById(R.id.spinner_category_post);
        mEdtCaption = findViewById(R.id.edtInput);
        mRvImgBackgroud = findViewById(R.id.rv_background_post_caption);

        mBtnBack.setOnClickListener((v) -> {
            for (Listener listener : getListeners()) {
                listener.onBackButtonClicked();
            }
        });
    }

    @Override
    public void bindUser(User user) {
        mTvUserName.setText(user.getUserName());
    }

    @Override
    public void bindCategory(List<Category> categories) {

    }

    @Override
    public void bindBackgroundForCaption(List<String> stringPaths) {

    }
}
