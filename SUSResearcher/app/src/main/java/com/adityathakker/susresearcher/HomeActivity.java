package com.adityathakker.susresearcher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = HomeActivity.class.getSimpleName();


    private TextView averageScoreTextView;
    private TextView averageScoreTextViewSupport;
    private TextView noInternet;

    Retrofit retrofit;
    APIs apIs;
    
    private Toolbar toolbar;
    private android.support.design.widget.TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout content_toolbar;

    public static ArrayList<String> username;
    public static ArrayList<String> score;
    public static ArrayList<String> date;
    public static ArrayList<String> averageScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        averageScoreTextView = (TextView) findViewById(R.id.content_home_avg_score);
        averageScoreTextViewSupport = (TextView) findViewById(R.id.content_home_avg_score_support);
        noInternet = (TextView) findViewById(R.id.no_internet);
        content_toolbar = (LinearLayout) findViewById(R.id.content_toolbar);
        setSupportActionBar(toolbar);

        username = new ArrayList<>();
        score = new ArrayList<>();
        date = new ArrayList<>();
        averageScore = new ArrayList<>();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        noInternet.setText("No Internet Connection");
        if(CommonTasks.isInternetAvailable(this)){
            content_toolbar.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            noInternet.setVisibility(GONE);
        }else{
            content_toolbar.setVisibility(GONE);
            viewPager.setVisibility(GONE);
            tabLayout.setVisibility(GONE);
            noInternet.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Internet Connection Available", Toast.LENGTH_SHORT).show();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.URLs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apIs = retrofit.create(APIs.class);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_drawer_questions){
            startActivity(new Intent(this, QuestionsActivity.class));
        }
        else if (id == R.id.nav_drawer_logout){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor  = sharedPreferences.edit();
            editor.putBoolean(AppConst.SharedPreferences.IS_ALREADY_LOGGED_IN, false);
            editor.commit();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(CommonTasks.isInternetAvailable(this)){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String workshopSelected = sharedPreferences.getString(AppConst.SharedPreferences.WORKSHOP_ID, null);
            Log.d(TAG, "onItemSelected: Workshop ID: " + workshopSelected);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            Call<ViewScoreWorkshopPojo> viewScoreWorkshopPojoCall = apIs.getViewScoreWorkshop(workshopSelected);
            viewScoreWorkshopPojoCall.enqueue(new Callback<ViewScoreWorkshopPojo>() {
                @Override
                public void onResponse(Call<ViewScoreWorkshopPojo> call, Response<ViewScoreWorkshopPojo> response) {
                    ViewScoreWorkshopPojo viewScoreWorkshopPojo = response.body();
                    if(viewScoreWorkshopPojo.getStatus().equals("success")){
                        averageScoreTextView.setText(viewScoreWorkshopPojo.getAverageScore());
                        ArrayList<UserScorePojo> userScorePojoArrayList = viewScoreWorkshopPojo.getUser();
                        ArrayList<QuestionAverageScorePojo> questionAverageScorePojoArrayList = viewScoreWorkshopPojo.getQuestionAverageScorePojos();

                        setupViewPager(viewPager, userScorePojoArrayList, questionAverageScorePojoArrayList);
                        progressDialog.dismiss();
                        setVisibility(View.VISIBLE);
                        content_toolbar.setVisibility(View.VISIBLE);
                        noInternet.setVisibility(GONE);
                        noInternet.setText("No Internet Connection");
                    }else{
                        Toast.makeText(HomeActivity.this, viewScoreWorkshopPojo.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        setVisibility(GONE);
                        content_toolbar.setVisibility(GONE);
                        noInternet.setVisibility(View.VISIBLE);
                        noInternet.setText("No Content Yet!");
                    }
                }

                @Override
                public void onFailure(Call<ViewScoreWorkshopPojo> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                    Toast.makeText(HomeActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    setVisibility(GONE);
                }
            });
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            averageScoreTextView.setVisibility(View.VISIBLE);
            averageScoreTextViewSupport.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);
        }else{
            tabLayout.setVisibility(GONE);
            viewPager.setVisibility(GONE);
            averageScoreTextView.setVisibility(GONE);
            averageScoreTextViewSupport.setVisibility(GONE);
            noInternet.setVisibility(View.VISIBLE);
        }
    }


    private void setVisibility(int code){
        averageScoreTextView.setVisibility(code);
        averageScoreTextViewSupport.setVisibility(code);
        tabLayout.setVisibility(code);
        viewPager.setVisibility(code);
    }

    private void setupViewPager(ViewPager viewPager, ArrayList<UserScorePojo> userScorePojoArrayList, ArrayList<QuestionAverageScorePojo> questionAverageScorePojoArrayList) {

        username.clear();
        score.clear();
        date.clear();
        averageScore.clear();

        for(UserScorePojo userScorePojo: userScorePojoArrayList){
            username.add(userScorePojo.getUser());
            score.add(userScorePojo.getScore());
            date.add(userScorePojo.getDate());
            Log.d(TAG, "setupViewPager: USername: " + userScorePojo.getUser() + " Score:" + userScorePojo.getScore());
        }

        for(QuestionAverageScorePojo questionAverageScorePojo: questionAverageScorePojoArrayList){
            averageScore.add(questionAverageScorePojo.getAverage());
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundleUsers = new Bundle();
        bundleUsers.putStringArrayList("username", username);
        bundleUsers.putStringArrayList("score", score);
        bundleUsers.putStringArrayList("date", date);
        HomeTabsUserFragment homeTabsUserFragment = new HomeTabsUserFragment();
        homeTabsUserFragment.setArguments(bundleUsers);
        adapter.addFragment(homeTabsUserFragment, "Users");
        if(HomeTabsUserFragment.userAverageScoreAdapter!= null){
            HomeTabsUserFragment.userAverageScoreAdapter.setDate(date);
            HomeTabsUserFragment.userAverageScoreAdapter.setScore(score);
            HomeTabsUserFragment.userAverageScoreAdapter.setUsername(username);
            HomeTabsUserFragment.userAverageScoreAdapter.notifyDataSetChanged();
        }


        Bundle bundleQuestions = new Bundle();
        bundleQuestions.putStringArrayList("average", averageScore);
        HomeTabsQuestionsFragment homeTabsQuestionsFragment = new HomeTabsQuestionsFragment();
        homeTabsQuestionsFragment.setArguments(bundleQuestions);
        adapter.addFragment(homeTabsQuestionsFragment, "Questions");
        if(HomeTabsQuestionsFragment.ques!=null){
            HomeTabsQuestionsFragment.ques.setAverageScore(averageScore);
            HomeTabsQuestionsFragment.ques.notifyDataSetChanged();
        }

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
