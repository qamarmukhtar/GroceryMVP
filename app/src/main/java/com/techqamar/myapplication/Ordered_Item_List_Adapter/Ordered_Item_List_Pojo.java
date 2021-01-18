package com.techqamar.myapplication.Ordered_Item_List_Adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ordered_Item_List_Pojo {
    @SerializedName("_id")
    @Expose
    private String item_id;
    @SerializedName("item_name")
    @Expose
    private String item_name;
    @SerializedName("image")
    @Expose
    private String mImageUrl;
    @SerializedName("avg_price")
    @Expose
    private String avg_price;


    public Ordered_Item_List_Pojo(String item_id, String item_name, String mImageUrl, String avg_price) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.mImageUrl = mImageUrl;
        this.avg_price = avg_price;

    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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
