package com.techqamar.myapplication.MainItem_list_Adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainItemList_Pojo {
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("item_name")
    @Expose
    private String item_name;
    @SerializedName("image")
    @Expose
    private String mImageUrl;
    @SerializedName("avg_price")
    @Expose
    private String avg_price;

    private boolean addedTocart = false;

    public MainItemList_Pojo(String _id, String item_name, String mImageUrl, String avg_price) {
        this._id = _id;
        this.item_name = item_name;
        this.mImageUrl = mImageUrl;
        this.avg_price = avg_price;
    }

    public boolean isAddedTocart() {
        return addedTocart;
    }
    public void setAddedTocart(boolean addedTocart) {
        this.addedTocart = addedTocart;
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