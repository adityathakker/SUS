package com.adityathakker.susadmin.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 7/10/16.
 */

public class ViewScoreWorkshopPojo {
    @SerializedName("status")
    private String status;
    @SerializedName("avg_score")
    private String averageScore;
    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private ArrayList<UserScorePojo> user;
    @SerializedName("questions")
    private ArrayList<QuestionAverageScorePojo> questionAverageScorePojos;

    public ViewScoreWorkshopPojo(String status, String averageScore, String message, ArrayList<UserScorePojo> user, ArrayList<QuestionAverageScorePojo> questionAverageScorePojos) {
        this.status = status;
        this.averageScore = averageScore;
        this.message = message;
        this.user = user;
        this.questionAverageScorePojos = questionAverageScorePojos;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<UserScorePojo> getUser() {
        return user;
    }

    public void setUser(ArrayList<UserScorePojo> user) {
        this.user = user;
    }

    public ArrayList<QuestionAverageScorePojo> getQuestionAverageScorePojos() {
        return questionAverageScorePojos;
    }

    public void setQuestionAverageScorePojos(ArrayList<QuestionAverageScorePojo> questionAverageScorePojos) {
        this.questionAverageScorePojos = questionAverageScorePojos;
    }
}
