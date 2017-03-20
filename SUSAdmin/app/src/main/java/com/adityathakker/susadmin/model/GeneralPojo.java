package com.adityathakker.susadmin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 5/10/16.
 */

public class GeneralPojo {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public GeneralPojo(String username, String message) {
        this.status = username;
        this.message = message;
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
}
