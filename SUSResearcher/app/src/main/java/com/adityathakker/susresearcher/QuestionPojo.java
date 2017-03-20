package com.adityathakker.susresearcher;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 7/10/16.
 */

public class QuestionPojo {
    @SerializedName("id")
    private String id;
    @SerializedName("question")
    private String question;

    public QuestionPojo(String id, String question) {
        this.id = id;
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
