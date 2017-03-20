package com.adityathakker.sus;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsSubmitActivity extends AppCompatActivity {

    private static final String TAG = QuestionsSubmitActivity.class.getSimpleName();
    private String id = null;

    @BindView(R.id.textInputEditText_name)
    TextInputEditText name;

    @BindView(R.id.question_q1)
    TextView question1;
    @BindView(R.id.question_q2)
    TextView question2;
    @BindView(R.id.question_q3)
    TextView question3;
    @BindView(R.id.question_q4)
    TextView question4;
    @BindView(R.id.question_q5)
    TextView question5;
    @BindView(R.id.question_q6)
    TextView question6;
    @BindView(R.id.question_q7)
    TextView question7;
    @BindView(R.id.question_q8)
    TextView question8;
    @BindView(R.id.question_q9)
    TextView question9;
    @BindView(R.id.question_q10)
    TextView question10;

    @BindView(R.id.radio_group_q1)
    RadioGroup radioGroup1;
    @BindView(R.id.radio_group_q2)
    RadioGroup radioGroup2;
    @BindView(R.id.radio_group_q3)
    RadioGroup radioGroup3;
    @BindView(R.id.radio_group_q4)
    RadioGroup radioGroup4;
    @BindView(R.id.radio_group_q5)
    RadioGroup radioGroup5;
    @BindView(R.id.radio_group_q6)
    RadioGroup radioGroup6;
    @BindView(R.id.radio_group_q7)
    RadioGroup radioGroup7;
    @BindView(R.id.radio_group_q8)
    RadioGroup radioGroup8;
    @BindView(R.id.radio_group_q9)
    RadioGroup radioGroup9;
    @BindView(R.id.radio_group_q10)
    RadioGroup radioGroup10;

    @BindView(R.id.comment_q1)
    EditText commentQ1;
    @BindView(R.id.comment_q2)
    EditText commentQ2;
    @BindView(R.id.comment_q3)
    EditText commentQ3;
    @BindView(R.id.comment_q4)
    EditText commentQ4;
    @BindView(R.id.comment_q5)
    EditText commentQ5;
    @BindView(R.id.comment_q6)
    EditText commentQ6;
    @BindView(R.id.comment_q7)
    EditText commentQ7;
    @BindView(R.id.comment_q8)
    EditText commentQ8;
    @BindView(R.id.comment_q9)
    EditText commentQ9;
    @BindView(R.id.comment_q10)
    EditText commentQ10;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_submit);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra("id");
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
                    .baseUrl("http://projects.adityathakker.com/SUS/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apIs = retrofit.create(APIs.class);

            Call<GetQuestionsPojo> getQuestionsPojoCall = apIs.getQuestions(id);
            getQuestionsPojoCall.enqueue(new Callback<GetQuestionsPojo>() {
                @Override
                public void onResponse(Call<GetQuestionsPojo> call, Response<GetQuestionsPojo> response) {
                    GetQuestionsPojo getQuestionsPojo = response.body();
                    if(getQuestionsPojo.getStatus().equals("success")){
                        ArrayList<QuestionPojo> questionPojos = getQuestionsPojo.getQuestionPojoArrayList();
                        for(QuestionPojo q: questionPojos){
                            Log.d(TAG, "onResponse: Qurstions" + q.toString());
                        }
                        question1.setText(questionPojos.get(0).getQuestion());
                        question2.setText(questionPojos.get(1).getQuestion());
                        question3.setText(questionPojos.get(2).getQuestion());
                        question4.setText(questionPojos.get(3).getQuestion());
                        question5.setText(questionPojos.get(4).getQuestion());
                        question6.setText(questionPojos.get(5).getQuestion());
                        question7.setText(questionPojos.get(6).getQuestion());
                        question8.setText(questionPojos.get(7).getQuestion());
                        question9.setText(questionPojos.get(8).getQuestion());
                        question10.setText(questionPojos.get(9).getQuestion());
                    }else{
                        Toast.makeText(QuestionsSubmitActivity.this, getQuestionsPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetQuestionsPojo> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                    Toast.makeText(QuestionsSubmitActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });


        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.submit)
    public void submit(View view){
        int ans1 = 0;
        switch(radioGroup1.getCheckedRadioButtonId()){
            case R.id.radio_sd_q1:
                ans1 = 0;
                break;
            case R.id.radio_d_q1:
                ans1 = 1;
                break;
            case R.id.radio_n_q1:
                ans1 = 2;
                break;
            case R.id.radio_a_q1:
                ans1 = 3;
                break;
            case R.id.radio_sa_q1:
                ans1 = 4;
                break;
        }

        String comment1 = commentQ1.getText().toString();



        int ans2 = 0;
        switch(radioGroup2.getCheckedRadioButtonId()){
            case R.id.radio_sd_q2:
                ans2 = 0;
                break;
            case R.id.radio_d_q2:
                ans2 = 1;
                break;
            case R.id.radio_n_q2:
                ans2 = 2;
                break;
            case R.id.radio_a_q2:
                ans2 = 3;
                break;
            case R.id.radio_sa_q2:
                ans2 = 4;
                break;
        }

        String comment2 = commentQ2.getText().toString();

        int ans3 = 0;
        switch(radioGroup3.getCheckedRadioButtonId()){
            case R.id.radio_sd_q3:
                ans3 = 0;
                break;
            case R.id.radio_d_q3:
                ans3 = 1;
                break;
            case R.id.radio_n_q3:
                ans3 = 2;
                break;
            case R.id.radio_a_q3:
                ans3 = 3;
                break;
            case R.id.radio_sa_q3:
                ans3 = 4;
                break;
        }
        String comment3 = commentQ3.getText().toString();

        int ans4 = 0;
        switch(radioGroup4.getCheckedRadioButtonId()){
            case R.id.radio_sd_q4:
                ans4 = 0;
                break;
            case R.id.radio_d_q4:
                ans4 = 1;
                break;
            case R.id.radio_n_q4:
                ans4 = 2;
                break;
            case R.id.radio_a_q4:
                ans4 = 3;
                break;
            case R.id.radio_sa_q4:
                ans4 = 4;
                break;
        }
        String comment4 = commentQ4.getText().toString();

        int ans5 = 0;
        switch(radioGroup5.getCheckedRadioButtonId()){
            case R.id.radio_sd_q5:
                ans5 = 0;
                break;
            case R.id.radio_d_q5:
                ans5 = 1;
                break;
            case R.id.radio_n_q5:
                ans5 = 2;
                break;
            case R.id.radio_a_q5:
                ans5 = 3;
                break;
            case R.id.radio_sa_q5:
                ans5 = 4;
                break;
        }
        String comment5 = commentQ5.getText().toString();

        int ans6 = 0;
        switch(radioGroup6.getCheckedRadioButtonId()){
            case R.id.radio_sd_q6:
                ans6 = 0;
                break;
            case R.id.radio_d_q6:
                ans6 = 1;
                break;
            case R.id.radio_n_q6:
                ans6 = 2;
                break;
            case R.id.radio_a_q6:
                ans6 = 3;
                break;
            case R.id.radio_sa_q6:
                ans6 = 4;
                break;
        }
        String comment6 = commentQ6.getText().toString();

        int ans7 = 0;
        switch(radioGroup7.getCheckedRadioButtonId()){
            case R.id.radio_sd_q7:
                ans7 = 0;
                break;
            case R.id.radio_d_q7:
                ans7 = 1;
                break;
            case R.id.radio_n_q7:
                ans7 = 2;
                break;
            case R.id.radio_a_q7:
                ans7 = 3;
                break;
            case R.id.radio_sa_q7:
                ans7 = 4;
                break;
        }
        String comment7 = commentQ7.getText().toString();

        int ans8 = 0;
        switch(radioGroup8.getCheckedRadioButtonId()){
            case R.id.radio_sd_q8:
                ans8 = 0;
                break;
            case R.id.radio_d_q8:
                ans8 = 1;
                break;
            case R.id.radio_n_q8:
                ans8 = 2;
                break;
            case R.id.radio_a_q8:
                ans8 = 3;
                break;
            case R.id.radio_sa_q8:
                ans8 = 4;
                break;
        }
        String comment8 = commentQ8.getText().toString();

        int ans9 = 0;
        switch(radioGroup9.getCheckedRadioButtonId()){
            case R.id.radio_sd_q9:
                ans9 = 0;
                break;
            case R.id.radio_d_q9:
                ans9 = 1;
                break;
            case R.id.radio_n_q9:
                ans9 = 2;
                break;
            case R.id.radio_a_q9:
                ans9 = 3;
                break;
            case R.id.radio_sa_q9:
                ans9 = 4;
                break;
        }
        String comment9 = commentQ9.getText().toString();

        int ans10 = 0;
        switch(radioGroup10.getCheckedRadioButtonId()){
            case R.id.radio_sd_q10:
                ans10 = 0;
                break;
            case R.id.radio_d_q10:
                ans10 = 1;
                break;
            case R.id.radio_n_q10:
                ans10 = 2;
                break;
            case R.id.radio_a_q10:
                ans10 = 3;
                break;
            case R.id.radio_sa_q10:
                ans10 = 4;
                break;
        }

        String comment10 = commentQ10.getText().toString();

        if(name.getText().toString().trim().equals("")){
            Toast.makeText(this, "Name must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(ans1 == -1 || ans2 == -1 || ans3 == -1 || ans4 == -1 || ans5 == -1 ||
                ans6 == -1 || ans7 == -1 || ans8 == -1 || ans9 == -1 || ans10 == -1 ){
            Toast.makeText(this, "Choose Options For All Questions", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://projects.adityathakker.com/SUS/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIs apIs = retrofit.create(APIs.class);

        Call<GeneralPojo> generalPojoCall = apIs.submitForm(
                name.getText().toString(),
                id,
                ans1 + "", comment1,
                ans2 + "", comment2,
                ans3 + "", comment3,
                ans4 + "", comment4,
                ans5 + "", comment5,
                ans6 + "", comment6,
                ans7 + "", comment7,
                ans8 + "", comment8,
                ans9 + "", comment9,
                ans10 + "", comment10);
        generalPojoCall.enqueue(new Callback<GeneralPojo>() {
            @Override
            public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                GeneralPojo generalPojo = response.body();
                if(generalPojo.getStatus().equals("success")){
                    Toast.makeText(QuestionsSubmitActivity.this, "Submission Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QuestionsSubmitActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GeneralPojo> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(QuestionsSubmitActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}
