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
import android.view.View;
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
import com.techqamar.myapplication.CartItem_list_adapter.CartItemList_Adapter;
import com.techqamar.myapplication.CartItem_list_adapter.CartItemList_Pojo;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.MainItem_list_Adapter.MainItemList_Pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CartItemActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgBackButton;
    RequestQueue requestQueue;
    public String Username, UserPhno, Useremail, UserId;
    public static Button Submit;
    public static TextView Total_Price_toolbar_text;
    private static String Table_No;

    CartItemList_Adapter cartItem_list_adapter;
    ArrayList<CartItemList_Pojo> cartItem_list_pojosPojoArrayList = new ArrayList<>();

    double Total_Price=0;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item);
        SharedPreferences sh = CartItemActivity.this.getSharedPreferences("profiledata", Context.MODE_PRIVATE);
        Username = sh.getString("username", "");
        UserPhno = sh.getString("phoneno", "");
        Useremail = sh.getString("email", "");
        UserId = sh.getString("id", "");

        Table_No =UserPhno;
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);

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
        Total_Price_toolbar_text = findViewById(R.id.Total_Price_toolbar_text);


        Submit = (Button) findViewById(R.id.submit);

//        new CheckInternetConnection(this).checkConnection();
//        Submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        cartItem_list_adapter = new CartItemList_Adapter(cartItem_list_pojosPojoArrayList, new CartItemList_Adapter.OnInvoiceOptionclicked() {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartItemActivity.this);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartItem_list_adapter);

        getGroceryStoreDetails();
    }

    public static String table_no() {
        return Table_No;
    }

    public void getGroceryStoreDetails() {
        cartItem_list_pojosPojoArrayList.clear();
        Total_Price=0;


        String url = String.format(Urls.STORE_ITEMS_AVG_RATE,UserPhno);

        System.out.println("Sever Response " + url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.getString(response).equals("{\"error\":false,\"message\":\"Store items Returns Successfully\"}")) {
//                                Total_Price_toolbar_text.setText(String.valueOf (" Total Price = "+"0"));
//                            }

                            System.out.println("Sever Response jsonObject response" + response);

                            JSONArray jsonArray = jsonObject.getJSONArray("user");
                            System.out.println("Sever Response jsonArray user" + jsonArray);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String avg_price = hit.getString("avg_price");
                                System.out.println("avg_price" + avg_price);
                                Total_Price = Total_Price + Double.parseDouble(avg_price);
                                cartItem_list_adapter.notifyDataSetChanged();

                            }
                            Total_Price_toolbar_text.setText(String.valueOf (" Total Price = "+Total_Price));

//                            DcListPojo content[] = new Gson().fromJson(jsonArray.toString(), DcListPojo[].class);
//                            ArrayList<DcListPojo> dataList = new ArrayList<DcListPojo>(Arrays.asList(content));
                            CartItemList_Pojo content[] = new Gson().fromJson(jsonArray.toString(), CartItemList_Pojo[].class);
                            ArrayList<CartItemList_Pojo> dataList = new ArrayList<CartItemList_Pojo>(Arrays.asList(content));

                            cartItem_list_pojosPojoArrayList.addAll(dataList);

                            cartItem_list_adapter.notifyDataSetChanged();

                            if (cartItem_list_pojosPojoArrayList.size() == 0) {

                                Toast.makeText(CartItemActivity.this, "List is empty", Toast.LENGTH_SHORT).show();

//                                tv_no_cards.setVisibility(View.VISIBLE);

                            } else {

//                                tv_no_cards.setVisibility(View.INVISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CartItemActivity.this, "List is empty Bad Response From Server", Toast.LENGTH_SHORT).show();
                            Total_Price_toolbar_text.setText(String.valueOf (" Total Price = "+"0"));
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof ServerError)
                            Toast.makeText(CartItemActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        else if (error instanceof TimeoutError)
                            Toast.makeText(CartItemActivity.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                        else if (error instanceof NetworkError)
                            Toast.makeText(CartItemActivity.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();


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



        requestQueue = Volley.newRequestQueue(CartItemActivity.this);
        postRequest.setShouldCache(false);
        requestQueue.add(postRequest);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // perform your action here
        finish();
        startActivity(new Intent(CartItemActivity.this, MainItemList.class));

    }

}