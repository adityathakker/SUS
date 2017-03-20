package com.adityathakker.susadmin.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.adityathakker.susadmin.R;
import com.adityathakker.susadmin.interfaces.APIs;
import com.adityathakker.susadmin.model.GeneralPojo;
import com.adityathakker.susadmin.utils.AppConst;
import com.adityathakker.susadmin.utils.CommonTasks;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditQuestionActivity extends AppCompatActivity {
    private static final String TAG = EditQuestionActivity.class.getSimpleName();
    @BindView(R.id.content_edit_question_edittext_question)
    EditText questionEditText;
    private String id = null;
    private String question = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        question = intent.getStringExtra("question");

        Log.d(TAG, "onCreate: Id: " + id + " Question: " + question);

        questionEditText.setText(question);

    }

    @OnClick(R.id.content_edit_question_button_save)
    public void save(View view){
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

            Call<GeneralPojo> generalPojoCall = apIs.editQuestion(id, questionEditText.getText().toString());
            generalPojoCall.enqueue(new Callback<GeneralPojo>() {
                @Override
                public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                    GeneralPojo generalPojo = response.body();
                    if(generalPojo.getStatus().equals("success")){
                        Toast.makeText(EditQuestionActivity.this, "Question Saved!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(EditQuestionActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GeneralPojo> call, Throwable t) {
                    Toast.makeText(EditQuestionActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}
