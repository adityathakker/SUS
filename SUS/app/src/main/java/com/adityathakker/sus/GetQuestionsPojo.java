package com.adityathakker.sus;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 8/10/16.
 */

public class GetQuestionsPojo {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("content")
    ArrayList<QuestionPojo> questionPojoArrayList;

    public GetQuestionsPojo(String status, String message, ArrayList<QuestionPojo> questionPojoArrayList) {
        this.status = status;
        this.message = message;
        this.questionPojoArrayList = questionPojoArrayList;
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

    public ArrayList<QuestionPojo> getQuestionPojoArrayList() {
        return questionPojoArrayList;
    }

    public void setQuestionPojoArrayList(ArrayList<QuestionPojo> questionPojoArrayList) {
        this.questionPojoArrayList = questionPojoArrayList;
    }
}
