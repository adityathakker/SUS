package com.adityathakker.susresearcher;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 5/10/16.
 */

public interface APIs {

    @GET("check_credentials_researcher.php")
    Call<GeneralPojo> checkCredentials(@Query("email") String user, @Query("password") String password);

    @GET("list_questions.php")
    Call<ListQuestionsPojo> getListOfQuestions();

    @GET("edit_question.php")
    Call<GeneralPojo> editQuestion(@Query("id") String id, @Query("question") String question);

    @GET("view_score_workshop.php")
    Call<ViewScoreWorkshopPojo> getViewScoreWorkshop(@Query("id") String id);




}
