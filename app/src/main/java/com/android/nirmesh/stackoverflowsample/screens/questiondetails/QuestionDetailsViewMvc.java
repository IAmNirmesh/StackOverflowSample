package com.android.nirmesh.stackoverflowsample.screens.questiondetails;

import com.android.nirmesh.stackoverflowsample.questions.QuestionDetails;
import com.android.nirmesh.stackoverflowsample.screens.common.mvcviews.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener> {

    interface Listener {
        // currently no user actions
    }

    void bindQuestion(QuestionDetails question);
}
