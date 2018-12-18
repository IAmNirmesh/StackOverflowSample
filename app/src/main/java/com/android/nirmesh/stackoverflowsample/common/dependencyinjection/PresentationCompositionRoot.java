package com.android.nirmesh.stackoverflowsample.common.dependencyinjection;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionDetailsUseCase;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionsListUseCase;
import com.android.nirmesh.stackoverflowsample.screens.common.dialogs.DialogsManager;
import com.android.nirmesh.stackoverflowsample.screens.common.mvcviews.ViewMvcFactory;

public class PresentationCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentManager mFragmentManager;
    private LayoutInflater mLayoutInflater;

    public PresentationCompositionRoot(CompositionRoot compositionRoot,
                                       FragmentManager fragmentManager,
                                       LayoutInflater layoutInflater) {
        mCompositionRoot = compositionRoot;
        mFragmentManager = fragmentManager;
        mLayoutInflater = layoutInflater;
    }

    public DialogsManager getDialogsManager() {
        return new DialogsManager(mFragmentManager);
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return mCompositionRoot.getFetchQuestionDetailsUseCase();
    }

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return mCompositionRoot.getFetchQuestionsListUseCase();
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(mLayoutInflater);
    }
}
