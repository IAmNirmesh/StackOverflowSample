package com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application;

import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation.PresentationComponent;
import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation.PresentationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {
    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
