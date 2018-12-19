package com.android.nirmesh.stackoverflowsample.common.dependencyinjection;

import com.android.nirmesh.stackoverflowsample.screens.questiondetails.QuestionDetailsActivity;
import com.android.nirmesh.stackoverflowsample.screens.questionslist.QuestionsListActivity;

public class Injector {

    private final PresentationCompositionRoot mPresentationCompositionRoot;

    public Injector(PresentationCompositionRoot presentationCompositionRoot) {
        mPresentationCompositionRoot = presentationCompositionRoot;
    }

    public void inject(Object client) {
        if (client instanceof QuestionsListActivity) {
            injectQuestionsListActivity((QuestionsListActivity) client);
        } else if (client instanceof QuestionDetailsActivity) {
            injectQuestionDetailsActivity((QuestionDetailsActivity) client);
        } else {
            throw new RuntimeException("Invalid Client: " + client);
        }
    }

    private void injectQuestionsListActivity(QuestionsListActivity client) {
        client.mViewMvcFactory = mPresentationCompositionRoot.getViewMvcFactory();
        client.mDialogsManager = mPresentationCompositionRoot.getDialogsManager();
        client.mFetchQuestionsListUseCase = mPresentationCompositionRoot.getFetchQuestionsListUseCase();
    }

    private void injectQuestionDetailsActivity(QuestionDetailsActivity client) {
        client.mViewMvcFactory = mPresentationCompositionRoot.getViewMvcFactory();
        client.mDialogsManager = mPresentationCompositionRoot.getDialogsManager();
        client.mFetchQuestionDetailsUseCase = mPresentationCompositionRoot.getFetchQuestionDetailsUseCase();
    }
}
