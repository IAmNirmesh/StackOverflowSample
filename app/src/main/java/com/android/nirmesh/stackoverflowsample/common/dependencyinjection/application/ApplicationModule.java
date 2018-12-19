package com.android.nirmesh.stackoverflowsample.common.dependencyinjection.application;

import com.android.nirmesh.stackoverflowsample.Constants;
import com.android.nirmesh.stackoverflowsample.networking.StackoverflowApi;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionDetailsUseCase;
import com.android.nirmesh.stackoverflowsample.questions.FetchQuestionsListUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    @Singleton
    @Provides
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    StackoverflowApi getStackoverflowApi(Retrofit retrofit) {
        return retrofit.create(StackoverflowApi.class);
    }

    @Provides
    FetchQuestionsListUseCase getFetchQuestionsListUseCase(StackoverflowApi stackoverflowApi) {
        return new FetchQuestionsListUseCase(stackoverflowApi);
    }

    @Provides
    FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase(StackoverflowApi stackoverflowApi) {
        return new FetchQuestionDetailsUseCase(stackoverflowApi);
    }
}
