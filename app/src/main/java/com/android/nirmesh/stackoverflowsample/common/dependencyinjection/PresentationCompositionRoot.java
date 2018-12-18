package com.android.nirmesh.stackoverflowsample.common.dependencyinjection;

import android.support.v4.app.FragmentManager;

import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionDetailsUseCase;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionsListUseCase;
import com.android.nirmesh.stackoverflowsample.screens.common.dialogs.DialogsManager;

public class PresentationCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private FragmentManager mFragmentManager;

    public PresentationCompositionRoot(CompositionRoot compositionRoot, FragmentManager fragmentManager) {
        mCompositionRoot = compositionRoot;
        mFragmentManager = fragmentManager;
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
}
