package com.adityathakker.susadmin.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.adityathakker.susadmin.R;
import com.adityathakker.susadmin.interfaces.APIs;
import com.adityathakker.susadmin.model.GeneralPojo;
import com.adityathakker.susadmin.model.ListWorkshopsPojo;
import com.adityathakker.susadmin.model.WorkshopPojo;
import com.adityathakker.susadmin.ui.custom.SpinnerAdapterResearcher;
import com.adityathakker.susadmin.utils.AppConst;
import com.adityathakker.susadmin.utils.CommonTasks;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddResearcherActivity extends AppCompatActivity {

    private static final String TAG = AddResearcherActivity.class.getSimpleName();
    @BindView(R.id.content_add_researcher_textinputedittext_first_name)
    TextInputEditText fname;
    @BindView(R.id.content_add_researcher_textinputedittext_last_name)
    TextInputEditText lname;
    @BindView(R.id.content_add_researcher_textinputedittext_email)
    TextInputEditText email;
    @BindView(R.id.content_add_researcher_spinner_workshops)
    Spinner workshops;

    private int workshopSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_researcher);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        workshopSelected = -1;
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                    final ArrayList<WorkshopPojo> workshopPojoArrayList = listWorkshopsPojo.getContent();
                    SpinnerAdapterResearcher spinnerAdapter = new SpinnerAdapterResearcher(AddResearcherActivity.this,
                            android.R.layout.simple_spinner_item,
                            workshopPojoArrayList);
                    workshops.setAdapter(spinnerAdapter);
                    workshops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            WorkshopPojo workshopPojo = workshopPojoArrayList.get(i);
                            workshopSelected = workshopPojo.getId();
                            Log.d(TAG, "onItemSelected: Workshop Name: " + workshopPojo.getName());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    workshops.setSelection(1);

                }else{
                    Toast.makeText(AddResearcherActivity.this, listWorkshopsPojo.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ListWorkshopsPojo> call, Throwable t) {
                Toast.makeText(AddResearcherActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.content_add_researcher_button_add)
    public void addResearcher(View view){

        String fnameString = fname.getText().toString();
        if(fnameString.trim().equals("")){
            Toast.makeText(this, "First Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String lnameString = lname.getText().toString();
        if(lnameString.trim().equals("")){
            Toast.makeText(this, "Last Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String emailString = email.getText().toString();
        if(emailString.trim().equals("")){
            Toast.makeText(this, "Email Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(workshopSelected == -1){
            Toast.makeText(this, "Workshop Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "addResearcher: WorkshopId: " + workshopSelected);

        if(CommonTasks.isInternetAvailable(this)){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://projects.adityathakker.com/SUS/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apIs = retrofit.create(APIs.class);

            Call<GeneralPojo> generalPojoCall = apIs.insertIntoResearcher(fnameString, lnameString, emailString, workshopSelected + "");
            generalPojoCall.enqueue(new Callback<GeneralPojo>() {
                @Override
                public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                    GeneralPojo generalPojo = response.body();
                    if(generalPojo.getStatus().equals("success")){
                        Toast.makeText(AddResearcherActivity.this, "Researcher Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(AddResearcherActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GeneralPojo> call, Throwable t) {
                    Toast.makeText(AddResearcherActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure");
                    progressDialog.dismiss();
                }
            });
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

}
