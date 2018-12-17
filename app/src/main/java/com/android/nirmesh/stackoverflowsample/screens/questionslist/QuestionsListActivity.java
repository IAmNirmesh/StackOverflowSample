package com.android.nirmesh.stackoverflowsample.screens.questionslist;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.nirmesh.stackoverflowsample.R;
import com.android.nirmesh.stackoverflowsample.networking.QuestionsListResponseSchema;
import com.android.nirmesh.stackoverflowsample.networking.StackoverflowApi;
import com.android.nirmesh.stackoverflowsample.questions.Question;
import com.android.nirmesh.stackoverflowsample.screens.common.ServerErrorDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsListActivity extends AppCompatActivity implements Callback<QuestionsListResponseSchema> {

    private RecyclerView mRecyclerView;
    private QuestionsAdapter mQuestionsAdapter;
    private StackoverflowApi mStackoverflowApi;
    private Call<QuestionsListResponseSchema> mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_questions_list);

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        mCall = mStackoverflowApi.lastActiveQuestions(20);
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
    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
        QuestionsListResponseSchema responseSchema;

        if (response.isSuccessful() && (responseSchema = response.body()) != null) {
            mQuestionsAdapter.bindData(responseSchema.getQuestions());
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

    /** RecyclerView adapter */
    public interface OnQuestionClickListener {
        void onQuestionClicked(Question question);
    }

    public static class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

        private final OnQuestionClickListener mOnQuestionClickListener;

        private List<Question> mQuestionsList = new ArrayList<>(0);

        public class QuestionViewHolder extends RecyclerView.ViewHolder {
            public TextView mTitle;


            public QuestionViewHolder(@NonNull View view) {
                super(view);
                mTitle = view.findViewById(R.id.txt_title);
            }
        }

        public QuestionsAdapter(OnQuestionClickListener onQuestionClickListener) {
            mOnQuestionClickListener = onQuestionClickListener;
        }

        public void bindData(List<Question> questions) {
            mQuestionsList = new ArrayList<>(questions);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_question_list_item, parent, false);

            return new QuestionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionViewHolder holder, final int position) {
            holder.mTitle.setText(mQuestionsList.get(position).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnQuestionClickListener.onQuestionClicked(mQuestionsList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mQuestionsList.size();
        }
    }
}