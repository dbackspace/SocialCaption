package com.xlteam.socialcaption.ui.common.controllers;

import androidx.fragment.app.Fragment;

import com.xlteam.socialcaption.common.dependencyinjection.ControllerCompositionRoot;
import com.xlteam.socialcaption.ui.main.MainActivity;

public class BaseFragment extends Fragment {
    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getControllerCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((MainActivity) requireActivity()).getActivityCompositionRoot());

        }
        return mControllerCompositionRoot;
    }

}
