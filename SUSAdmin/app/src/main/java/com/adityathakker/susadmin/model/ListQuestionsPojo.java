package com.adityathakker.susadmin.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 7/10/16.
 */

public class ListQuestionsPojo {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("content")
    private ArrayList<QuestionPojo> content;

    public ListQuestionsPojo(String status, String message, ArrayList<QuestionPojo> content) {
        this.status = status;
        this.message = message;
        this.content = content;
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

    public ArrayList<QuestionPojo> getContent() {
        return content;
    }

    public void setContent(ArrayList<QuestionPojo> content) {
        this.content = content;
    }
}
