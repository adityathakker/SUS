package com.adityathakker.sus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorkshopSelectActivity extends AppCompatActivity {
    private static final String TAG = WorkshopSelectActivity.class.getSimpleName();
    @BindView(R.id.textInputEditText_workshop_id)
    TextInputEditText workshopId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_select);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.proceed)
    public void proceed(View view){
        if(workshopId.getText().toString().trim().equals("")){
            Toast.makeText(this, "Workshop Number Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return;
        }

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

            Call<GeneralPojo> generalPojoCall = apIs.checkIfWorkshopExists(workshopId.getText().toString());
            generalPojoCall.enqueue(new Callback<GeneralPojo>() {
                @Override
                public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                    GeneralPojo generalPojo = response.body();
                    if(generalPojo.getStatus().equals("success")){
                        startActivity(new Intent(WorkshopSelectActivity.this, QuestionsSubmitActivity.class).putExtra("id", workshopId.getText().toString()));
                    }else{
                        Toast.makeText(WorkshopSelectActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GeneralPojo> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                    Toast.makeText(WorkshopSelectActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }else{
            Toast.makeText(this, "No Internet Connection Available", Toast.LENGTH_SHORT).show();
        }
    }

}
