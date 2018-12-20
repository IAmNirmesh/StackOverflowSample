package com.android.nirmesh.stackoverflowsample.common.dependencyinjection.presentation;

import com.android.nirmesh.stackoverflowsample.screens.questiondetails.QuestionDetailsActivity;
import com.android.nirmesh.stackoverflowsample.screens.questionslist.QuestionsListActivity;

import dagger.Subcomponent;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {

    void inject(QuestionsListActivity questionsListActivity);

    void inject(QuestionDetailsActivity questionDetailsActivity);
}
