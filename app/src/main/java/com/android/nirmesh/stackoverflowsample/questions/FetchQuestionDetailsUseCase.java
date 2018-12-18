package com.android.nirmesh.stackoverflowsample.questions;

import android.support.annotation.Nullable;

import com.android.nirmesh.stackoverflowsample.Constants;
import com.android.nirmesh.stackoverflowsample.common.BaseObservable;
import com.android.nirmesh.stackoverflowsample.networking.SingleQuestionResponseSchema;
import com.android.nirmesh.stackoverflowsample.networking.StackoverflowApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    public interface Listener {
        void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question);
        void onFetchOfQuestionDetailsFailed();
    }

    private final StackoverflowApi mStackoverflowApi;

    @Nullable
    Call<SingleQuestionResponseSchema> mCall;

    public FetchQuestionDetailsUseCase() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackoverflowApi = retrofit.create(StackoverflowApi.class);
    }

    public void fetchQuestionDetailsAndNotify(String questionId) {

        cancelCurrentFetchIfActive();

        mCall = mStackoverflowApi.questionDetails(questionId);
        mCall.enqueue(new Callback<SingleQuestionResponseSchema>() {
            @Override
            public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getQuestion());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(QuestionWithBody question) {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsSucceeded(question);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsFailed();
        }
    }
}
