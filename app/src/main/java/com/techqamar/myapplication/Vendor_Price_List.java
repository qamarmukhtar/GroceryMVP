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
import com.techqamar.myapplication.CartItem_list_adapter.CartItemList_Adapter;
import com.techqamar.myapplication.CartItem_list_adapter.CartItemList_Pojo;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.VendorPriceListAdapter.Vendor_Price_List_Adapter;
import com.techqamar.myapplication.VendorPriceListAdapter.Vendor_Price_List_Pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Vendor_Price_List extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgBackButton;
    RequestQueue requestQueue;
    public static String Username, UserPhno, Useremail, UserId, Useraddress;
    public static Button cod_online;

    String email_id;
    double Vendor_Total_Price=0;
    double User_Total_Price=0;
    double Avg_Total_Price=0;
    public static String ITEM_NAME="";
    public static TextView Total_Price_toolbar_text,avrage_total_price,user_total_price,vendor_total_price;
    private static String Table_No;
    private static String V_T_P;
    private static String U_T_P;
    private static String A_T_P;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    Vendor_Price_List_Adapter vendor_list_adapter;
    ArrayList<Vendor_Price_List_Pojo> Vendor_Price_list_pojosPojoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor__price__list);
        SharedPreferences sh = this.getSharedPreferences("profiledata", Context.MODE_PRIVATE);
        Username = sh.getString("username", "");
        UserPhno = sh.getString("phoneno", "");
        Useremail = sh.getString("email", "");
        UserId = sh.getString("id", "");
        Useraddress = sh.getString("address", "");

        Table_No =UserPhno;
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);

        Intent intent = getIntent();
        email_id = intent.getStringExtra("email_id");
        Total_Price_toolbar_text = findViewById(R.id.Total_Price_toolbar_text);
        avrage_total_price = findViewById(R.id.average_price);
        user_total_price = findViewById(R.id.user_price);
        vendor_total_price = findViewById(R.id.vendor_price);
        cod_online = (Button) findViewById(R.id.cod_online);

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


        vendor_list_adapter = new Vendor_Price_List_Adapter(Vendor_Price_list_pojosPojoArrayList, new Vendor_Price_List_Adapter.OnInvoiceOptionclicked() {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Vendor_Price_List.this);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(vendor_list_adapter);

        getGroceryStoreDetails();
    }

    public static String table_no() {
        return Table_No;
    }

    private void getGroceryStoreDetails() {
        Vendor_Price_list_pojosPojoArrayList.clear();


        String url = String.format(Urls.VENDOR_PRICE_LIST,Table_No,email_id);

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
                                System.out.println("avg_price" + vendor_price);
                                Vendor_Total_Price = Vendor_Total_Price + Double.parseDouble(vendor_price);
                                V_T_P=Double. toString(Vendor_Total_Price);
                                User_Total_Price = User_Total_Price + Double.parseDouble(user_price);
                                U_T_P=Double. toString(User_Total_Price);
                                Avg_Total_Price = Avg_Total_Price + Double.parseDouble(avg_price);
                                A_T_P=Double. toString(Avg_Total_Price);

                                ITEM_NAME = ITEM_NAME+","+item_name;
                                System.out.println("avg_price" + ITEM_NAME);


                            }
                          //  Total_Price_toolbar_text.setText(String.valueOf (" Total Price = "+Vendor_Total_Price));
                            avrage_total_price.setText(String.valueOf (Avg_Total_Price));
                            user_total_price.setText(String.valueOf (User_Total_Price));
                            vendor_total_price.setText(String.valueOf (Vendor_Total_Price));

//                            DcListPojo content[] = new Gson().fromJson(jsonArray.toString(), DcListPojo[].class);
//                            ArrayList<DcListPojo> dataList = new ArrayList<DcListPojo>(Arrays.asList(content));
                            Vendor_Price_List_Pojo content[] = new Gson().fromJson(jsonArray.toString(), Vendor_Price_List_Pojo[].class);
                            ArrayList<Vendor_Price_List_Pojo> dataList = new ArrayList<Vendor_Price_List_Pojo>(Arrays.asList(content));

                            Vendor_Price_list_pojosPojoArrayList.addAll(dataList);

                            vendor_list_adapter.notifyDataSetChanged();

                            if (Vendor_Price_list_pojosPojoArrayList.size() == 0) {

                                Toast.makeText(Vendor_Price_List.this, "List is empty", Toast.LENGTH_SHORT).show();

//                                tv_no_cards.setVisibility(View.VISIBLE);

                            } else {

//                                tv_no_cards.setVisibility(View.INVISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Vendor_Price_List.this, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof ServerError)
                            Toast.makeText(Vendor_Price_List.this, "Server Error", Toast.LENGTH_SHORT).show();
                        else if (error instanceof TimeoutError)
                            Toast.makeText(Vendor_Price_List.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                        else if (error instanceof NetworkError)
                            Toast.makeText(Vendor_Price_List.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();


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




        requestQueue = Volley.newRequestQueue(Vendor_Price_List.this);
        requestQueue.add(postRequest);


    }

    public static String v_t_p() {
        return V_T_P;
    }
    public static String u_t_p() {
        return U_T_P;
    }
    public static String a_t_p() {
        return A_T_P;
    }
    public static String item_name() {
        return ITEM_NAME;
    }
   public static String user_address() {
        return Useraddress;
    }
   public static String user_id() {
        return UserId;
    }

}