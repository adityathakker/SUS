package com.adityathakker.sus;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 8/10/16.
 */

public class QuestionPojo {
    @SerializedName("id")
    int id;
    @SerializedName("question")
    String question;

    public QuestionPojo(int id, String question) {
        this.id = id;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String toString(){
        return "ID: " + id + " Question: " + question;
    }
}
