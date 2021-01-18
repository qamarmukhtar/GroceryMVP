package com.techqamar.myapplication.Vendors_TypeListRecViewAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Vendors_TypeListPojo {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email_id")
    @Expose
    private String email_id;
    @SerializedName("sum")
    @Expose
    private String sum;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("store_id")
    @Expose
    private String store_id;

    public Vendors_TypeListPojo(String name, String email_id, String sum,String logo,String store_id) {
        this.name = name;
        this.email_id = email_id;
        this.sum = sum;
        this.logo = logo;
        this.store_id = store_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}