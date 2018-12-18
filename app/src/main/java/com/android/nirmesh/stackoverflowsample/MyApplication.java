package com.android.nirmesh.stackoverflowsample;

import android.app.Application;

import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.CompositionRoot;

public class MyApplication extends Application {

    private CompositionRoot mCompositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }
}
