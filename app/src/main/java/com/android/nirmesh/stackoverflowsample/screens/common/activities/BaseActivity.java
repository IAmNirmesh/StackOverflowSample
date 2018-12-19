package com.android.nirmesh.stackoverflowsample.screens.common.activities;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.android.nirmesh.stackoverflowsample.MyApplication;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.CompositionRoot;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.PresentationCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private PresentationCompositionRoot mPresentationCompositionRoot;

    @UiThread
    protected PresentationCompositionRoot getCompositionRoot() {
        if (mPresentationCompositionRoot == null) {
            mPresentationCompositionRoot = new PresentationCompositionRoot(
                    getAppCompositionRoot(),this);
        }

        return mPresentationCompositionRoot;
    }

    protected CompositionRoot getAppCompositionRoot() {
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
