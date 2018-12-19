package com.android.nirmesh.stackoverflowsample.screens.common.activities;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.android.nirmesh.stackoverflowsample.MyApplication;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.CompositionRoot;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.Injector;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.PresentationCompositionRoot;

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
        return new PresentationCompositionRoot(getAppCompositionRoot(), this);
    }

    protected CompositionRoot getAppCompositionRoot() {
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
