package com.android.nirmesh.stackoverflowsample.common.dependencyinjection;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application.ApplicationComponent;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionDetailsUseCase;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionsListUseCase;
import com.android.nirmesh.stackoverflowsample.screens.common.ImageLoader;
import com.android.nirmesh.stackoverflowsample.screens.common.dialogs.DialogsManager;
import com.android.nirmesh.stackoverflowsample.screens.common.mvcviews.ViewMvcFactory;

public class PresentationCompositionRoot {

    private final ApplicationComponent mApplicationComponent;
    private final AppCompatActivity mActivity;

    public PresentationCompositionRoot(ApplicationComponent applicationComponent,
                                       AppCompatActivity activity) {
        mApplicationComponent = applicationComponent;
        mActivity = activity;
    }

    private FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    private Activity getActivity() {
        return mActivity;
    }

    private ImageLoader getImageLoader() {
        return new ImageLoader(mActivity);
    }

    public DialogsManager getDialogsManager() {
        return new DialogsManager(getFragmentManager());
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return mApplicationComponent.getFetchQuestionDetailsUseCase();
    }

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return mApplicationComponent.getFetchQuestionsListUseCase();
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater(), getImageLoader());
    }
}
