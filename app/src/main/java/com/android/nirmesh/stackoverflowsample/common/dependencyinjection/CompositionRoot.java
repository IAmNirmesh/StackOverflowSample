package com.android.nirmesh.stackoverflowsample.common.dependencyinjection;

import android.support.annotation.UiThread;

import com.android.nirmesh.stackoverflowsample.Constants;
import com.android.nirmesh.stackoverflowsample.networking.StackoverflowApi;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionDetailsUseCase;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionsListUseCase;
import com.android.nirmesh.stackoverflowsample.screens.common.dialogs.DialogsManagerFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@UiThread
public class CompositionRoot {

    private Retrofit mRetrofit;
    private StackoverflowApi mStackoverflowApi;

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private StackoverflowApi getStackoverflowApi() {
        if (mStackoverflowApi == null) {
            mStackoverflowApi = getRetrofit().create(StackoverflowApi.class);
        }
        return mStackoverflowApi;
    }

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return new FetchQuestionsListUseCase(getStackoverflowApi());
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return new FetchQuestionDetailsUseCase(getStackoverflowApi());
    }

    public DialogsManagerFactory getDialogsManagerFactory() {
        return new DialogsManagerFactory();
    }
}
