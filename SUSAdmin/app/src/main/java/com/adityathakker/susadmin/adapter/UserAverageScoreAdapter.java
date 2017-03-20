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

public class UserAverageScoreAdapter extends RecyclerView.Adapter<UserAverageScoreAdapter.UserAverageScoreViewHolder> {
    public static final String TAG = UserAverageScoreAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<String> username;
    private ArrayList<String> date;
    private ArrayList<String> score;

    public UserAverageScoreAdapter(Context context, ArrayList<String> username, ArrayList<String> date, ArrayList<String> score) {
        this.context = context;
        this.username = username;
        this.date = date;
        this.score = score;
    }

    public void setUsername(ArrayList<String> username) {
        this.username = username;
    }

    public void setScore(ArrayList<String> score) {
        this.score = score;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    @Override
    public UserAverageScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_score_list_row, parent, false);
        UserAverageScoreViewHolder viewHolder = new UserAverageScoreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserAverageScoreViewHolder holder, int position) {
        holder.username.setText(username.get(position));
        holder.date.setText(date.get(position));
        holder.score.setText(score.get(position));
        Log.d(TAG, "onBindViewHolder: Username: " + username.get(position) + " ==> " + score.get(position));
    }

    @Override
    public int getItemCount() {
        return username!=null?username.size():0;
    }

    class UserAverageScoreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.users_score_list_row_username)
        TextView username;
        @BindView(R.id.users_score_list_row_date)
        TextView date;
        @BindView(R.id.users_score_list_row_score)
        TextView score;
        public UserAverageScoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
