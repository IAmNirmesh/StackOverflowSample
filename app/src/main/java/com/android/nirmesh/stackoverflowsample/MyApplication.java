package com.android.nirmesh.stackoverflowsample;

import android.app.Application;

import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application.ApplicationComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application.ApplicationModule;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application.DaggerApplicationComponent;

public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
