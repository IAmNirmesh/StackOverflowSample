package com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation;

import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionDetailsUseCase;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionsListUseCase;
import com.android.nirmesh.stackoverflowsample.screens.common.dialogs.DialogsManager;
import com.android.nirmesh.stackoverflowsample.screens.common.mvcviews.ViewMvcFactory;

import dagger.Component;

@Component(modules = PresentationModule.class)
public interface PresentationComponent {

    public DialogsManager getDialogsManager();

    public ViewMvcFactory getViewMvcFactory();

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase();

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase();
}
