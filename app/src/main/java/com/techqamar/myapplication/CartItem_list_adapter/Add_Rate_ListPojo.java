package com.techqamar.myapplication.CartItem_list_adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Add_Rate_ListPojo {

    @SerializedName("P_id")
    @Expose
    private String P_id;
    @SerializedName("P_price")
    @Expose
    private String P_price;

    @SerializedName("productList")
    @Expose
    public ArrayList<Add_Rate_ListPojo> productList = null;




    public String getP_id() {
        return P_id;
    }

    public void setP_id(String p_id) {
        P_id = p_id;
    }

    public String getP_price() {
        return P_price;
    }

    public void setP_price(String p_price) {
        P_price = p_price;
    }


    public ArrayList<Add_Rate_ListPojo> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Add_Rate_ListPojo> productDetails) {
        this.productList = productDetails;
    }
}
