package com.adityathakker.susadmin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 5/10/16.
 */

public class WorkshopPojo {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("show_score")
    private String showScore;
    @SerializedName("Sys_name")
    private String systemName;
    @SerializedName("reason")
    private String reasonCompulsory;

    public WorkshopPojo(int id, String name, String showScore, String systemName, String reasonCompulsory) {
        this.id = id;
        this.name = name;
        this.showScore = showScore;
        this.systemName = systemName;
        this.reasonCompulsory = reasonCompulsory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowScore() {
        return showScore;
    }

    public void setShowScore(String showScore) {
        this.showScore = showScore;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getReasonCompulsory() {
        return reasonCompulsory;
    }

    public void setReasonCompulsory(String reasonCompulsory) {
        this.reasonCompulsory = reasonCompulsory;
    }
}
