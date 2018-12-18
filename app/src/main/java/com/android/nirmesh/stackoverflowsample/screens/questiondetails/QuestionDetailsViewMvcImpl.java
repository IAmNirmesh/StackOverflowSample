package com.android.nirmesh.stackoverflowsample.screens.questiondetails;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.nirmesh.stackoverflowsample.R;
import com.android.nirmesh.stackoverflowsample.questions.QuestionWithBody;
import com.android.nirmesh.stackoverflowsample.screens.common.mvcviews.BaseViewMvc;

public class QuestionDetailsViewMvcImpl extends BaseViewMvc<QuestionDetailsViewMvc.Listener>
        implements QuestionDetailsViewMvc {
    private final TextView mTxtQuestionBody;

    public QuestionDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_question_details, container, false));
        mTxtQuestionBody = findViewById(R.id.txt_question_body);
    }

    @Override
    public void bindQuestion(QuestionWithBody question) {
        String questionBody = question.getBody();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody));
        }
    }
}
