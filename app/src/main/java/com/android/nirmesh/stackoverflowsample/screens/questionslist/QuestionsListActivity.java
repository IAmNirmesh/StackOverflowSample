package com.android.nirmesh.stackoverflowsample.screens.questionslist;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.android.nirmesh.stackoverflowsample.Constants;
import com.android.nirmesh.stackoverflowsample.networking.QuestionsListResponseSchema;
import com.android.nirmesh.stackoverflowsample.networking.StackoverflowApi;
import com.android.nirmesh.stackoverflowsample.questions.Question;
import com.android.nirmesh.stackoverflowsample.screens.common.ServerErrorDialogFragment;
import com.android.nirmesh.stackoverflowsample.screens.questiondetails.QuestionDetailsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsListActivity extends AppCompatActivity
        implements Callback<QuestionsListResponseSchema>,QuestionsListViewMvc.Listener {

    private StackoverflowApi mStackoverflowApi;
    private Call<QuestionsListResponseSchema> mCall;
    private QuestionsListViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = new QuestionsListViewMvcImpl(LayoutInflater.from(this), null);

        setContentView(mViewMvc.getRootView());

        // init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackoverflowApi = retrofit.create(StackoverflowApi.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mCall = mStackoverflowApi.lastActiveQuestions(20);
        mCall.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
        QuestionsListResponseSchema responseSchema;

        if (response.isSuccessful() && (responseSchema = response.body()) != null) {
            mViewMvc.bindQuestions(responseSchema.getQuestions());
        } else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss();
    }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(QuestionsListActivity.this, question.getId());
    }
}