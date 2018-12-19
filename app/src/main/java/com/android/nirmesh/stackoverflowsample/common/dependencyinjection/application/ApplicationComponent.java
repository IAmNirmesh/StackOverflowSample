package com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application;

import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionDetailsUseCase;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionsListUseCase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase();

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase();
}
