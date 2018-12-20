package com.android.nirmesh.stackoverflowsample.common;

import android.app.Service;
import android.support.annotation.UiThread;

import com.android.nirmesh.stackoverflowsample.MyApplication;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application.ApplicationComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.service.ServiceComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.service.ServiceModule;

public abstract class BaseService extends Service {

    private boolean mIsServiceComponentUsed;

    @UiThread
    protected ServiceComponent getServiceComponent() {
        if (mIsServiceComponentUsed) {
            throw new RuntimeException("There is no reason to perform Injection more than once");
        }

        mIsServiceComponentUsed = true;

        return getApplicationComponent().newServiceComponent(new ServiceModule(this));
    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }
}
