package com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation;

import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application.ApplicationComponent;
import com.android.nirmesh.stackoverflowsample.screens.questiondetails.QuestionDetailsActivity;
import com.android.nirmesh.stackoverflowsample.screens.questionslist.QuestionsListActivity;

import dagger.Component;

@PresentationScope
@Component(dependencies = ApplicationComponent.class, modules = PresentationModule.class)
public interface PresentationComponent {

    void inject(QuestionsListActivity questionsListActivity);

    void inject(QuestionDetailsActivity questionDetailsActivity);
}
