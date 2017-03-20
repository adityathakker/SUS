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
import com.adityathakker.susadmin.adapter.WorkShopsListAdapter;
import com.adityathakker.susadmin.interfaces.APIs;
import com.adityathakker.susadmin.model.ListWorkshopsPojo;
import com.adityathakker.susadmin.model.WorkshopPojo;
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

public class WorkshopsActivity extends AppCompatActivity {
    private static final String TAG = WorkshopsActivity.class.getSimpleName();
    @BindView(R.id.content_workshops_recycler_view)
    RecyclerEmptyView recyclerEmptyView;
    @BindView(R.id.no_internet)
    TextView noInternet;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkshopsActivity.this, AddWorkshopActivity.class));
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

            Call<ListWorkshopsPojo> listWorkshopsPojoCall = apIs.getListOfWorkshops();
            listWorkshopsPojoCall.enqueue(new Callback<ListWorkshopsPojo>() {
                @Override
                public void onResponse(Call<ListWorkshopsPojo> call, Response<ListWorkshopsPojo> response) {
                    ListWorkshopsPojo listWorkshopsPojo = response.body();
                    if(listWorkshopsPojo.getStatus().equals("success")){
                        WorkShopsListAdapter workShopsListAdapter = new WorkShopsListAdapter(WorkshopsActivity.this, listWorkshopsPojo.getContent());
                        workShopsListAdapter.setOnItemClickListener(new WorkShopsListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position, WorkshopPojo workshopPojo) {
                                Log.d(TAG, "onItemClick: " + workshopPojo.getName() + " Clicked!");
                            }
                        });
                        recyclerEmptyView.setAdapter(workShopsListAdapter);
                    }else{
                        Toast.makeText(WorkshopsActivity.this, listWorkshopsPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ListWorkshopsPojo> call, Throwable t) {
                    Toast.makeText(WorkshopsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

            recyclerEmptyView.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }else{
            recyclerEmptyView.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);

        }
    }
}
