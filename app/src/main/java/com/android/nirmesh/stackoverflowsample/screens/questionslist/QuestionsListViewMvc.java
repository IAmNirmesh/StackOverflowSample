package com.android.nirmesh.stackoverflowsample.screens.questionslist;

import com.android.nirmesh.stackoverflowsample.questions.Question;
import com.android.nirmesh.stackoverflowsample.screens.common.mvcviews.ObservableViewMvc;

import java.util.List;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {

    interface Listener {
        void onQuestionClicked(Question question);
    }

    void bindQuestions(List<Question> questions);
}
