package com.adityathakker.susadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityathakker.susadmin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 7/10/16.
 */

public class QuestionsAverageScoreAdapter extends RecyclerView.Adapter<QuestionsAverageScoreAdapter.QuestionsAverageScoreViewHolder> {
    public static final String TAG = QuestionsAverageScoreAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<String> averageScore;

    public QuestionsAverageScoreAdapter(Context context, ArrayList<String> averageScore) {
        this.context = context;
        this.averageScore = averageScore;
    }

    public void setAverageScore(ArrayList<String> averageScore) {
        this.averageScore = averageScore;
    }

    @Override
    public QuestionsAverageScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_score_list_row, parent, false);
        QuestionsAverageScoreViewHolder viewHolder = new QuestionsAverageScoreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuestionsAverageScoreViewHolder holder, int position) {
        holder.question.setText("Question " + (position + 1));
        holder.score.setText(averageScore.get(position));
        Log.d(TAG, "onBindViewHolder: Question: " + (position + 1) + " ==> " + averageScore.get(position));
    }

    @Override
    public int getItemCount() {
        return averageScore!=null?averageScore.size():0;
    }

    class QuestionsAverageScoreViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.questions_score_list_row_question)
        TextView question;
        @BindView(R.id.questions_score_list_row_score)
        TextView score;
        public QuestionsAverageScoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
