package com.android.nirmesh.stackoverflowsample.questions;

import android.support.annotation.Nullable;

import com.android.nirmesh.stackoverflowsample.common.BaseObservable;
import com.android.nirmesh.stackoverflowsample.networking.QuestionSchema;
import com.android.nirmesh.stackoverflowsample.networking.SingleQuestionResponseSchema;
import com.android.nirmesh.stackoverflowsample.networking.StackoverflowApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    public interface Listener {
        void onFetchOfQuestionDetailsSucceeded(QuestionDetails question);
        void onFetchOfQuestionDetailsFailed();
    }

    private final StackoverflowApi mStackoverflowApi;

    @Nullable
    Call<SingleQuestionResponseSchema> mCall;

    public FetchQuestionDetailsUseCase(StackoverflowApi stackoverflowApi) {
        mStackoverflowApi = stackoverflowApi;
    }

    public void fetchQuestionDetailsAndNotify(String questionId) {

        cancelCurrentFetchIfActive();

        mCall = mStackoverflowApi.questionDetails(questionId);
        mCall.enqueue(new Callback<SingleQuestionResponseSchema>() {
            @Override
            public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(questionDetailsFromQuestionSchema(response.body().getQuestion()));
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

    private QuestionDetails questionDetailsFromQuestionSchema(QuestionSchema question) {
        return new QuestionDetails(question.getId(), question.getTitle(), question.getBody(),
                question.getOwner().getUserDisplayName(),
                question.getOwner().getUserAvatarUrl());
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(QuestionDetails question) {
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
