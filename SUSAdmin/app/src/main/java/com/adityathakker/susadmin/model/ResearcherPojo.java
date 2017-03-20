package com.adityathakker.susadmin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 6/10/16.
 */

public class ResearcherPojo {
    @SerializedName("id")
    private int id;
    @SerializedName("fname")
    private String firstname;
    @SerializedName("lname")
    private String lastname;
    @SerializedName("email")
    private String email;
    @SerializedName("passwd")
    private String password;
    @SerializedName("workshop")
    private String workshopId;
    @SerializedName("workshop_name")
    private String workshopName;

    public ResearcherPojo(int id, String firstname, String lastname, String email, String password, String workshopId, String workshopName) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.workshopId = workshopId;
        this.workshopName = workshopName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(String workshopId) {
        this.workshopId = workshopId;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }
}
