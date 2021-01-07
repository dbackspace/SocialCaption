package com.xlteam.socialcaption.ui.common.controllers;

import androidx.fragment.app.DialogFragment;

import com.xlteam.socialcaption.common.dependencyinjection.ControllerCompositionRoot;
import com.xlteam.socialcaption.ui.home.HomePageActivity;

public class BaseDialog extends DialogFragment {
    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getControllerCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((HomePageActivity) requireActivity()).getActivityCompositionRoot()
            );
        }
        return mControllerCompositionRoot;
    }
}
