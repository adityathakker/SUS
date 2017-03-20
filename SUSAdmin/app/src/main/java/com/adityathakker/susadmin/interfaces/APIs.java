package com.adityathakker.susadmin.interfaces;

import com.adityathakker.susadmin.model.ListQuestionsPojo;
import com.adityathakker.susadmin.model.ListResearchersPojo;
import com.adityathakker.susadmin.model.ListWorkshopsPojo;
import com.adityathakker.susadmin.model.GeneralPojo;
import com.adityathakker.susadmin.model.ViewScoreWorkshopPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 5/10/16.
 */

public interface APIs {

    @GET("check_credentials.php")
    Call<GeneralPojo> checkCredentials(@Query("username") String user, @Query("password") String password);

    @GET("list_workshops.php")
    Call<ListWorkshopsPojo> getListOfWorkshops();

    @GET("list_researchers.php")
    Call<ListResearchersPojo> getListOfResearchers();

    @GET("list_questions.php")
    Call<ListQuestionsPojo> getListOfQuestions();

    @GET("edit_question.php")
    Call<GeneralPojo> editQuestion(@Query("id") String id, @Query("question") String question);

    @GET("view_score_workshop.php")
    Call<ViewScoreWorkshopPojo> getViewScoreWorkshop(@Query("id") String id);

    @GET("insert_into_workshop.php")
    Call<GeneralPojo> insertIntoWorkshop(@Query("workshopName") String workshopName,
                                         @Query("systemName") String systemName,
                                         @Query("showScore") String showScore,
                                         @Query("reasonCompulsory") String reasonCompulsory);

    @GET("insert_into_researcher.php")
    Call<GeneralPojo> insertIntoResearcher(@Query("fname") String fname,
                                         @Query("lname") String lname,
                                         @Query("email") String email,
                                         @Query("workshopId") String workshopId);



}
