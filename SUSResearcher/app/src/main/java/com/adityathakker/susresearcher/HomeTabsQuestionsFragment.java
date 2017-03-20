package com.adityathakker.susresearcher;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 7/10/16.
 */

public class HomeTabsQuestionsFragment extends Fragment {
    RecyclerEmptyView recyclerEmptyView;
    public static QuestionsAverageScoreAdapter ques;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tabs_questions, container, false);
        Context context = getContext();

        recyclerEmptyView = (RecyclerEmptyView) view.findViewById(R.id.home_tabs_questions_fragment_recyclerview);
        recyclerEmptyView.setLayoutManager(new LinearLayoutManager(context));

        Bundle args = getArguments();
        ArrayList<String> score = args.getStringArrayList("average");

        ques = new QuestionsAverageScoreAdapter(context, score);
        recyclerEmptyView.addItemDecoration(new SimpleDividerItemDecoration(context));
        recyclerEmptyView.setAdapter(ques);
        return view;
    }
}
