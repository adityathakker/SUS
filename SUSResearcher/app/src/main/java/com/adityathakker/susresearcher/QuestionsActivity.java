package com.adityathakker.susresearcher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsActivity extends AppCompatActivity {
    private static final String TAG = QuestionsActivity.class.getSimpleName();
    @BindView(R.id.content_questions_recyclerview)
    RecyclerEmptyView recyclerEmptyView;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

            Call<ListQuestionsPojo> listQuestionsPojoCall = apIs.getListOfQuestions();
            listQuestionsPojoCall.enqueue(new Callback<ListQuestionsPojo>() {
                @Override
                public void onResponse(Call<ListQuestionsPojo> call, Response<ListQuestionsPojo> response) {
                    ListQuestionsPojo listQuestionsPojo = response.body();
                    if(listQuestionsPojo.getStatus().equals("success")){
                        QuestionsListAdapter questionsListAdapter = new QuestionsListAdapter(QuestionsActivity.this, listQuestionsPojo.getContent());
                        questionsListAdapter.setOnItemClickListener(new QuestionsListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position, QuestionPojo questionPojo) {
                                Intent intent = new Intent(QuestionsActivity.this, EditQuestionActivity.class);
                                intent.putExtra("id", questionPojo.getId());
                                intent.putExtra("question", questionPojo.getQuestion());
                                startActivity(intent);
                            }
                        });
                        recyclerEmptyView.setAdapter(questionsListAdapter);
                    }else{
                        Toast.makeText(QuestionsActivity.this, listQuestionsPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ListQuestionsPojo> call, Throwable t) {
                    Toast.makeText(QuestionsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
            recyclerEmptyView.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);

        }else{
            recyclerEmptyView.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }
    }
}
