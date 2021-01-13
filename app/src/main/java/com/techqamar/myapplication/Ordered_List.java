package com.techqamar.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.Ordered_list_Adapter.Ordered_Price_List_Adapter;
import com.techqamar.myapplication.Ordered_list_Adapter.Ordered_Price_List_Pojo;
import com.techqamar.myapplication.VendorPriceListAdapter.Vendor_Price_List_Adapter;
import com.techqamar.myapplication.VendorPriceListAdapter.Vendor_Price_List_Pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Ordered_List extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgBackButton;
    RequestQueue requestQueue;
    public static String Username, UserPhno, Useremail, UserId, Useraddress;

    String email_id;
    double Vendor_Total_Price=0;
    double User_Total_Price=0;
    double Avg_Total_Price=0;
    public static String ITEM_NAME="";
    private SwipeRefreshLayout mSwipeRefreshLayout;

    Ordered_Price_List_Adapter ordered_list_adapter;
    ArrayList<Ordered_Price_List_Pojo> ordered_Price_list_pojosPojoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered__list);
        SharedPreferences sh = this.getSharedPreferences("profiledata", Context.MODE_PRIVATE);
        Username = sh.getString("username", "");
        UserPhno = sh.getString("phoneno", "");
        Useremail = sh.getString("email", "");
        UserId = sh.getString("id", "");
        Useraddress = sh.getString("address", "");


        mSwipeRefreshLayout = findViewById(R.id.swipe_container);

        Intent intent = getIntent();
        email_id = intent.getStringExtra("email_id");



        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to make your refresh action
                // CallYourRefreshingMethod();
                getGroceryStoreDetails();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

//        tv_no_cards = findViewById(R.id.tv_no_cards);




//        new CheckInternetConnection(this).checkConnection();
//        Submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        ordered_list_adapter = new Ordered_Price_List_Adapter(ordered_Price_list_pojosPojoArrayList, new Ordered_Price_List_Adapter.OnInvoiceOptionclicked() {
//            @Override
//            public void onPayOptionClicked(MainItemList_Pojo invoiceListPojo) {
//
//            }
//
//            @Override
//            public void onPrintOptionClicked(MainItemList_Pojo invoiceListPojo) {
//
//            }


        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Ordered_List.this);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ordered_list_adapter);

       // getGroceryStoreDetails();
    }



    private void getGroceryStoreDetails() {
        ordered_Price_list_pojosPojoArrayList.clear();


        String url = String.format(Urls.ORDERED_LIST,UserId);

        System.out.println("Sever Response " + url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            System.out.println("Sever Response jsonObject response" + response);

                            JSONArray jsonArray = jsonObject.getJSONArray("user");
//                            System.out.println("Sever Response jsonArray user" + jsonArray);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String vendor_price = hit.getString("vendor_price");
                                String user_price = hit.getString("user_price");
                                String avg_price = hit.getString("avg_price");
                                String item_name = hit.getString("item_name");

                            }

//                            DcListPojo content[] = new Gson().fromJson(jsonArray.toString(), DcListPojo[].class);
//                            ArrayList<DcListPojo> dataList = new ArrayList<DcListPojo>(Arrays.asList(content));
                            Ordered_Price_List_Pojo content[] = new Gson().fromJson(jsonArray.toString(), Ordered_Price_List_Pojo[].class);
                            ArrayList<Ordered_Price_List_Pojo> dataList = new ArrayList<Ordered_Price_List_Pojo>(Arrays.asList(content));

                            ordered_Price_list_pojosPojoArrayList.addAll(dataList);

                            ordered_list_adapter.notifyDataSetChanged();

                            if (ordered_Price_list_pojosPojoArrayList.size() == 0) {

                                Toast.makeText(Ordered_List.this, "List is empty", Toast.LENGTH_SHORT).show();

//                                tv_no_cards.setVisibility(View.VISIBLE);

                            } else {

//                                tv_no_cards.setVisibility(View.INVISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Ordered_List.this, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof ServerError)
                            Toast.makeText(Ordered_List.this, "Server Error", Toast.LENGTH_SHORT).show();
                        else if (error instanceof TimeoutError)
                            Toast.makeText(Ordered_List.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                        else if (error instanceof NetworkError)
                            Toast.makeText(Ordered_List.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();


//                        String body = null;
//                        try {
//                            body = new String(error.networkResponse.data, "UTF-8");
//                            Log.d("Error.Response", body);
//
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }

                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };




        requestQueue = Volley.newRequestQueue(Ordered_List.this);
        requestQueue.add(postRequest);


    }



}