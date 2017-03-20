package com.adityathakker.susadmin.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 5/10/16.
 */

public class ListWorkshopsPojo {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("content")
    private ArrayList<WorkshopPojo> content;

    public ListWorkshopsPojo(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<WorkshopPojo> getContent() {
        return content;
    }

    public void setContent(ArrayList<WorkshopPojo> content) {
        this.content = content;
    }
}
