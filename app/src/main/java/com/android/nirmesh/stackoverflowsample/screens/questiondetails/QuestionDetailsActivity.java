package com.android.nirmesh.stackoverflowsample.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionDetailsUseCase;
import com.android.nirmesh.stackoverflowsample.questions.QuestionWithBody;
import com.android.nirmesh.stackoverflowsample.screens.common.activities.BaseActivity;
import com.android.nirmesh.stackoverflowsample.screens.common.dialogs.DialogsManager;
import com.android.nirmesh.stackoverflowsample.screens.common.dialogs.ServerErrorDialogFragment;

public class QuestionDetailsActivity extends BaseActivity
        implements QuestionDetailsViewMvc.Listener, FetchQuestionDetailsUseCase.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private String mQuestionId;
    private QuestionDetailsViewMvc mViewMvc;
    private FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;
    private DialogsManager mDialogsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = new QuestionDetailsViewMvcImpl(LayoutInflater.from(this), null);

        setContentView(mViewMvc.getRootView());

        mFetchQuestionDetailsUseCase = getCompositionRoot().getFetchQuestionDetailsUseCase();

        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

        mDialogsManager = getCompositionRoot().getDialogsManagerFactory().newDialogManager(getSupportFragmentManager());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchQuestionDetailsUseCase.registerListener(this);
        mFetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(mQuestionId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchQuestionDetailsUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question) {
        mViewMvc.bindQuestion(question);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {
        mDialogsManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }
}
