package com.adityathakker.sus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 8/10/16.
 */

public interface APIs {
    @GET("check_if_workshop_exists.php")
    Call<GeneralPojo> checkIfWorkshopExists(@Query("id") String id);

    @GET("get_questions_by_workshop.php")
    Call<GetQuestionsPojo> getQuestions(@Query("id") String id);

    @GET("submit_form.php")
    Call<GeneralPojo> submitForm(@Query("user") String user,
                                 @Query("idd") String idd,
                                 @Query("quest1") String quest1, @Query("comment1") String comment1,
                                 @Query("quest2") String quest2, @Query("comment2") String comment2,
                                 @Query("quest3") String quest3, @Query("comment3") String comment3,
                                 @Query("quest4") String quest4, @Query("comment4") String comment4,
                                 @Query("quest5") String quest5, @Query("comment5") String comment5,
                                 @Query("quest6") String quest6, @Query("comment6") String comment6,
                                 @Query("quest7") String quest7, @Query("comment7") String comment7,
                                 @Query("quest8") String quest8, @Query("comment8") String comment8,
                                 @Query("quest9") String quest9, @Query("comment9") String comment9,
                                 @Query("quest10") String quest10, @Query("comment10") String comment10);


}
