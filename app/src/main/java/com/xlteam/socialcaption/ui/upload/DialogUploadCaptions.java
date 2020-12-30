package com.xlteam.socialcaption.ui.upload;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.ui.common.controllers.BaseDialog;

import java.util.Objects;

public class DialogUploadCaptions extends BaseDialog implements DialogUploadCaptionsViewMvc.Listener {
    public static DialogUploadCaptions newInstance() {
        return new DialogUploadCaptions();
    }

    private DialogUploadCaptionsViewMvc mViewMvc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getControllerCompositionRoot().getViewMvcFactory().getDialogUploadCaptionsViewMvc(null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext(), R.style.Theme_SocialCaption);
        dialog.setContentView(mViewMvc.getRootView());
        return dialog;
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
    public void onPostButtonClicked() {

    }

    @Override
    public void onBackButtonClicked() {
        dismiss();
    }
}
