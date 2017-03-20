package com.adityathakker.susresearcher;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 7/10/16.
 */

public class QuestionAverageScorePojo {
    @SerializedName("question_no")
    private String questionNo;
    @SerializedName("avg")
    private String average;

    public QuestionAverageScorePojo(String average, String questionNo) {
        this.average = average;
        this.questionNo = questionNo;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }
}
