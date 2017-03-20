package com.adityathakker.susadmin.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adityathakker.susadmin.R;
import com.adityathakker.susadmin.adapter.ResearchersListAdapter;
import com.adityathakker.susadmin.interfaces.APIs;
import com.adityathakker.susadmin.model.ListResearchersPojo;
import com.adityathakker.susadmin.model.ResearcherPojo;
import com.adityathakker.susadmin.ui.custom.RecyclerEmptyView;
import com.adityathakker.susadmin.ui.custom.SimpleDividerItemDecoration;
import com.adityathakker.susadmin.utils.AppConst;
import com.adityathakker.susadmin.utils.CommonTasks;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResearchersActivity extends AppCompatActivity {

    private static final String TAG = ResearchersActivity.class.getSimpleName();
    @BindView(R.id.content_researchers_recyclerview)
    RecyclerEmptyView recyclerEmptyView;
    @BindView(R.id.no_internet)
    TextView noInternet;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_researchers);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResearchersActivity.this, AddResearcherActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerEmptyView.setLayoutManager(new LinearLayoutManager(this));
        recyclerEmptyView.addItemDecoration(new SimpleDividerItemDecoration(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(CommonTasks.isInternetAvailable(this)){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.URLs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apIs = retrofit.create(APIs.class);

            Call<ListResearchersPojo> listResearchersPojoCall = apIs.getListOfResearchers();
            listResearchersPojoCall.enqueue(new Callback<ListResearchersPojo>() {
                @Override
                public void onResponse(Call<ListResearchersPojo> call, Response<ListResearchersPojo> response) {
                    ListResearchersPojo listResearchersPojo = response.body();
                    if(listResearchersPojo.getStatus().equals("success")){
                        ResearchersListAdapter researchersListAdapter = new ResearchersListAdapter(ResearchersActivity.this, listResearchersPojo.getContent());
                        researchersListAdapter.setOnItemClickListener(new ResearchersListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position, ResearcherPojo current) {
                                Log.d(TAG, "onItemClick: " + current.getFirstname()  + " " + current.getLastname()+ " Clicked!");
                            }
                        });
                        recyclerEmptyView.setAdapter(researchersListAdapter);
                    }else{
                        Toast.makeText(ResearchersActivity.this, listResearchersPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ListResearchersPojo> call, Throwable t) {
                    Toast.makeText(ResearchersActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

            noInternet.setVisibility(View.GONE);
            recyclerEmptyView.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        }else{
            noInternet.setVisibility(View.VISIBLE);
            recyclerEmptyView.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }
    }
}
