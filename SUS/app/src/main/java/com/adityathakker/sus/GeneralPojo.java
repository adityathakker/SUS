package com.adityathakker.sus;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 8/10/16.
 */

public class GeneralPojo {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;

    public GeneralPojo(String status, String message) {
        this.status = status;
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
