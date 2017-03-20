package com.adityathakker.susadmin.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adityathakker.susadmin.R;
import com.adityathakker.susadmin.adapter.UserAverageScoreAdapter;
import com.adityathakker.susadmin.ui.custom.RecyclerEmptyView;
import com.adityathakker.susadmin.ui.custom.SimpleDividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 7/10/16.
 */

public class HomeTabsUserFragment extends android.support.v4.app.Fragment {
    RecyclerEmptyView recyclerEmptyView;
    public static UserAverageScoreAdapter userAverageScoreAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tabs_user, container, false);
        Context context = getContext();
        recyclerEmptyView = (RecyclerEmptyView) view.findViewById(R.id.home_tabs_user_fragment_recyclerview);
        recyclerEmptyView.setLayoutManager(new LinearLayoutManager(context));

        Bundle args = getArguments();
        ArrayList<String> username = args.getStringArrayList("username");
        ArrayList<String> date = args.getStringArrayList("date");
        ArrayList<String> score = args.getStringArrayList("score");

        userAverageScoreAdapter = new UserAverageScoreAdapter(context, username, date, score);
        recyclerEmptyView.addItemDecoration(new SimpleDividerItemDecoration(context));
        recyclerEmptyView.setAdapter(userAverageScoreAdapter);


        return view;
    }


}
