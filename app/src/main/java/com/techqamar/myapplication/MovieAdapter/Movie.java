package com.techqamar.myapplication.MovieAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {
//    @SerializedName("_id")
//    @Expose
    private String _id;
//    @SerializedName("item_id")
//    @Expose
    private String item_id;
//    @SerializedName("item_name")
//    @Expose
    private String item_name;
//    @SerializedName("image")
//    @Expose
    private String mImageUrl;
//    @SerializedName("avg_price")
//    @Expose
    private String avg_price;
//    @SerializedName("add_rate")
//    @Expose
    private String add_rate;



    public Movie(String _id, String item_name, String mImageUrl, String avg_price, String add_rate, String item_id) {
        this._id = _id;
        this.item_name = item_name;
        this.mImageUrl = mImageUrl;
        this.avg_price = avg_price;
        this.add_rate = add_rate;
        this.item_id = item_id;
    }

    public Movie() {

    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getAdd_rate() {
        return add_rate;
    }

    public void setAdd_rate(String add_rate) {
        this.add_rate = add_rate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(String avg_price) {
        this.avg_price = avg_price;
    }

}

