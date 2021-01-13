package com.techqamar.myapplication.Ordered_list_Adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ordered_Price_List_Pojo {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("logo")
    @Expose
    private String mImageUrl;
    @SerializedName("date")
    @Expose
    private String date;

    public Ordered_Price_List_Pojo(String name, String location, String mImageUrl, String date) {
        this.name = name;
        this.location = location;
        this.mImageUrl = mImageUrl;
        this.date = date;
    }

    public Ordered_Price_List_Pojo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}