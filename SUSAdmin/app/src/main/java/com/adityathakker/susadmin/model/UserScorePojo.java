package com.adityathakker.susadmin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 7/10/16.
 */

public class UserScorePojo {
    @SerializedName("id")
    private String id;
    @SerializedName("user")
    private String user;
    @SerializedName("score")
    private String score;
    @SerializedName("user_uid")
    private String user_uid;
    @SerializedName("idd")
    private String idd;
    @SerializedName("date")
    private String date;

    public UserScorePojo(String id, String user, String score, String user_uid, String idd, String date) {
        this.id = id;
        this.user = user;
        this.score = score;
        this.user_uid = user_uid;
        this.idd = idd;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
