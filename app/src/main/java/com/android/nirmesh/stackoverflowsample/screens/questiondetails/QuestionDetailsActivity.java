package com.android.nirmesh.stackoverflowsample.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionDetailsUseCase;
import com.android.nirmesh.stackoverflowsample.questions.QuestionWithBody;
import com.android.nirmesh.stackoverflowsample.screens.common.ServerErrorDialogFragment;

public class QuestionDetailsActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = new QuestionDetailsViewMvcImpl(LayoutInflater.from(this), null);

        setContentView(mViewMvc.getRootView());

        mFetchQuestionDetailsUseCase = new FetchQuestionDetailsUseCase();

        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(ServerErrorDialogFragment.newInstance(), null).commitAllowingStateLoss();
    }
}
