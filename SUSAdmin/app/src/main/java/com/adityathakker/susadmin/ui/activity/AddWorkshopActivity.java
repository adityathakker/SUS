package com.adityathakker.susadmin.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
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

public class AddWorkshopActivity extends AppCompatActivity {

    @BindView(R.id.content_add_workshop_textinputledittext_workshop_name)
    TextInputEditText workshopName;
    @BindView(R.id.content_add_workshop_textinputledittext_system_name)
    TextInputEditText systemName;
    @BindView(R.id.content_add_workshop_checkbox_reason_compulsory)
    CheckBox reasonCompulasory;
    @BindView(R.id.content_add_workshop_checkbox_show_score)
    CheckBox showScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.content_add_workshop_button_add)
    public void addWorkshop(View view){
        if(CommonTasks.isInternetAvailable(this)){
            String workshopNameString = workshopName.getText().toString();
            if(workshopNameString.trim().equals("")){
                return;
            }

            String systemNameString = systemName.getText().toString();
            if(systemNameString.trim().equals("")){
                return;
            }

            Boolean reasonCompulasoryChecked = reasonCompulasory.isChecked();
            Boolean showScoreChecked = showScore.isChecked();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.URLs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apIs = retrofit.create(APIs.class);

            Call<GeneralPojo> generalPojoCall = apIs.insertIntoWorkshop(workshopNameString,
                    systemNameString,
                    reasonCompulasoryChecked?"Y":"N",
                    showScoreChecked?"Y":"N");
            generalPojoCall.enqueue(new Callback<GeneralPojo>() {
                @Override
                public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                    GeneralPojo generalPojo = response.body();
                    if(generalPojo.getStatus().equals("success")){
                        Toast.makeText(AddWorkshopActivity.this, "Workshop Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(AddWorkshopActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GeneralPojo> call, Throwable t) {
                    Toast.makeText(AddWorkshopActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}
