package com.adityathakker.susadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityathakker.susadmin.R;
import com.adityathakker.susadmin.model.QuestionPojo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 7/10/16.
 */

public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.QuestionsListViewHolder> {
    public static final String TAG = QuestionsListAdapter.class.getSimpleName();
    private Context context;
    private OnItemClickListener clickListener;
    private ArrayList<QuestionPojo> questionPojoArrayList;

    public QuestionsListAdapter(Context context, ArrayList<QuestionPojo> questionPojoArrayList) {
        this.context = context;
        this.questionPojoArrayList = questionPojoArrayList;
    }

    @Override
    public QuestionsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_list_row, parent, false);
        QuestionsListViewHolder viewHolder = new QuestionsListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuestionsListViewHolder holder, int position) {
        QuestionPojo questionPojo = questionPojoArrayList.get(position);
        holder.question.setText(questionPojo.getQuestion());
        holder.questionSupport.setText("Question " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return questionPojoArrayList!=null?questionPojoArrayList.size():0;
    }

    class QuestionsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.questions_list_row_question)
        TextView question;
        @BindView(R.id.questions_list_row_question_support)
        TextView questionSupport;

        public QuestionsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition(), questionPojoArrayList.get(getAdapterPosition()));
        }
    }

    //Click Listener Interface
    public interface OnItemClickListener {
        public void onItemClick(View view, int position, QuestionPojo questionPojo);
    }

    //Setting up the click listener
    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
