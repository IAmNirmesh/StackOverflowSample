package com.android.nirmesh.stackoverflowsample.screens.common.dialogs;

import android.support.annotation.UiThread;
import android.support.v4.app.DialogFragment;

import com.android.nirmesh.stackoverflowsample.MyApplication;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application.ApplicationComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation.PresentationComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation.PresentationModule;

public class BaseDialog extends DialogFragment {

    private boolean mIsInjectorUsed;

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("There is no need to use Injector more than once.");
        }

        mIsInjectorUsed = true;

        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule(getActivity()));
    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getActivity().getApplication()).getApplicationComponent();
    }
}
