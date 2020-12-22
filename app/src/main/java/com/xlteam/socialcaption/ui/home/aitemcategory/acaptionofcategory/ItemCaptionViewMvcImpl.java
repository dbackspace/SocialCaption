package com.xlteam.socialcaption.ui.home.aitemcategory.acaptionofcategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;

public class ItemCaptionViewMvcImpl extends BaseObservableViewMvc<ItemCaptionViewMvc.Listener> implements ItemCaptionViewMvc {
    private final TextView tvCaptionContent;
    private final TextView tvPathImg;
    private Caption mCaption;

    public ItemCaptionViewMvcImpl(LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        tvCaptionContent = findViewById(R.id.tv_content_of_caption);
        tvPathImg = findViewById(R.id.tv_path_img);

        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener : getListeners()) {
                    listener.onCaptionClicked(mCaption);
                }
            }
        });

    }

    @Override
    public void bindCaption(Caption caption) {
        mCaption = caption;
    }
}
