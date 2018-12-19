package com.android.nirmesh.stackoverflowsample.screens.common.activities;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.android.nirmesh.stackoverflowsample.MyApplication;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.Injector;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.PresentationCompositionRoot;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application.ApplicationComponent;

public class BaseActivity extends AppCompatActivity {

    private boolean mIsInjectorUsed;

    @UiThread
    protected Injector getInjector() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("There is no need to use Injector more than once.");
        }
        mIsInjectorUsed = true;
        return new Injector(getCompositionRoot());
    }

    private PresentationCompositionRoot getCompositionRoot() {
        return new PresentationCompositionRoot(getApplicationComponent(), this);
    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }
}
