package com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application;

import android.app.Application;

import com.android.nirmesh.stackoverflowsample.networking.StackoverflowApi;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionsListUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    FetchQuestionsListUseCase getFetchQuestionsListUseCase(StackoverflowApi stackoverflowApi) {
        return new FetchQuestionsListUseCase(stackoverflowApi);
    }
}
