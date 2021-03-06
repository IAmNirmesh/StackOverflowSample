package com.android.nirmesh.stackoverflowsample.screens.questionslist;

import android.os.Bundle;

import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionsListUseCase;
import com.android.nirmesh.stackoverflowsample.questions.Question;
import com.android.nirmesh.stackoverflowsample.screens.common.activities.BaseActivity;
import com.android.nirmesh.stackoverflowsample.screens.common.dialogs.DialogsManager;
import com.android.nirmesh.stackoverflowsample.screens.common.dialogs.ServerErrorDialogFragment;
import com.android.nirmesh.stackoverflowsample.screens.common.mvcviews.ViewMvcFactory;
import com.android.nirmesh.stackoverflowsample.screens.questiondetails.QuestionDetailsActivity;

import java.util.List;

import javax.inject.Inject;

public class QuestionsListActivity extends BaseActivity
        implements QuestionsListViewMvc.Listener, FetchQuestionsListUseCase.Listener {

    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;
    private QuestionsListViewMvc mViewMvc;

    @Inject FetchQuestionsListUseCase mFetchQuestionsListUseCase;
    @Inject DialogsManager mDialogsManager;
    @Inject ViewMvcFactory mViewMvcFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);

        mViewMvc = mViewMvcFactory.newInstance(QuestionsListViewMvc.class, null);

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchQuestionsListUseCase.registerListener(this);
        mFetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchQuestionsListUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionsSucceeded(List<Question> questions) {
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onFetchOfQuestionsFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(QuestionsListActivity.this, question.getId());
    }
}