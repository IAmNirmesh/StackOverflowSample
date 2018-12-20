package com.android.nirmesh.stackoverflowsample.screens.common.activities;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.android.nirmesh.stackoverflowsample.MyApplication;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation.DaggerPresentationComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation.PresentationComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation.PresentationModule;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application.ApplicationComponent;

public class BaseActivity extends AppCompatActivity {

    private boolean mIsInjectorUsed;

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("There is no need to use Injector more than once.");
        }
        mIsInjectorUsed = true;

        return DaggerPresentationComponent.builder()
                .presentationModule(new PresentationModule(this, getApplicationComponent()))
                .build();
    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }
}
