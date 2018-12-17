package com.android.nirmesh.stackoverflowsample.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.android.nirmesh.stackoverflowsample.Constants;
import com.android.nirmesh.stackoverflowsample.R;
import com.android.nirmesh.stackoverflowsample.networking.SingleQuestionResponseSchema;
import com.android.nirmesh.stackoverflowsample.networking.StackoverflowApi;
import com.android.nirmesh.stackoverflowsample.screens.common.ServerErrorDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionDetailsActivity extends AppCompatActivity implements Callback<SingleQuestionResponseSchema> {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private TextView mTxtQuestionsBody;
    private StackoverflowApi mStackoverflowApi;
    private Call<SingleQuestionResponseSchema> mCall;
    private String mQuestionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question_details);

        mTxtQuestionsBody = findViewById(R.id.txt_question_body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackoverflowApi = retrofit.create(StackoverflowApi.class);

        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCall = mStackoverflowApi.questionDetails(mQuestionId);
        mCall.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
        SingleQuestionResponseSchema questionResponseSchema;

        if (response.isSuccessful() && (questionResponseSchema = response.body()) != null) {
            String questionBody = questionResponseSchema.getQuestion().getBody();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTxtQuestionsBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
            } else {
                mTxtQuestionsBody.setText(Html.fromHtml(questionBody));
            }
        } else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable t) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(ServerErrorDialogFragment.newInstance(), null).commitAllowingStateLoss();
    }
}
