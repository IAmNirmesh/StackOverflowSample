package com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application;

import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation.PresentationComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation.PresentationModule;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.service.ServiceComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.service.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {
    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);

    public ServiceComponent newServiceComponent(ServiceModule serviceModule);
}
