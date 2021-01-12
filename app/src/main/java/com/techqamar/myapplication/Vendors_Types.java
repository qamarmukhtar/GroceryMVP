package com.techqamar.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.Vendors_TypeListRecViewAdapter.Vendors_TypeListPojo;
import com.techqamar.myapplication.Vendors_TypeListRecViewAdapter.Vendors_TypeListRecViewAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Vendors_Types extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgBackButton;
    RequestQueue requestQueue;
    private static String Table_No;
    public String Username, UserPhno, Useremail, UserId;

    private StaggeredGridLayoutManager mLayoutManager;
//    private LottieAnimationView tv_no_cards;

    Vendors_TypeListRecViewAdapter vendorsTypeListRecViewAdapter;
    ArrayList<Vendors_TypeListPojo> vendorsTypeListPojoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery__stores);
        TextView textView = findViewById(R.id.Category_toolbar_text);
        textView.setText("Supplier List");
        SharedPreferences sh = Vendors_Types.this.getSharedPreferences("profiledata", Context.MODE_PRIVATE);
        Username = sh.getString("username", "");
        UserPhno = sh.getString("phoneno", "");
        Useremail = sh.getString("email", "");
        UserId = sh.getString("id", "");

        Table_No =UserPhno;

//        imgBackButton = (ImageView) findViewById(R.id.imgBackButton);
//        imgBackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Grocery_Stores.this, MainActivity.class));
//                finish();
//            }
//        });
//        tv_no_cards = findViewById(R.id.tv_no_cards);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        requestQueue = Volley.newRequestQueue(Vendors_Types.this);


//        new CheckInternetConnection(this).checkConnection();


        vendorsTypeListRecViewAdapter = new Vendors_TypeListRecViewAdapter(vendorsTypeListPojoArrayList, new Vendors_TypeListRecViewAdapter.OnInvoiceOptionclicked() {
            @Override
            public void onPayOptionClicked(Vendors_TypeListPojo invoiceListPojo) {


            }

            @Override
            public void onPrintOptionClicked(Vendors_TypeListPojo invoiceListPojo) {

            }
        });
        //using staggered grid pattern in recyclerview
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setFocusable(false);
//        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(vendorsTypeListRecViewAdapter);

        getGroceryStoreDetails();
    }

    private void getGroceryStoreDetails() {


        String url = String.format(Urls.VENDOR_ITEMS_AVG_RATE_SUBMIT,Table_No);

        System.out.println("Sever Response " + url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            System.out.println("Sever Response jsonObject" + response);

//                            JSONObject jsonObject = new JSONObject(response);
//
//                            System.out.println("Sever Response jsonObject" + response);
//
//                            JSONObject jsonObject1 = jsonObject.getJSONObject("user");
////                            System.out.println("Sever Response jsonObject1" + jsonObject1);
//
//                            JsonStreamParser parser = new JsonStreamParser((response));
//                            while (parser.hasNext()) {
//                                JsonElement object = parser.next();
//                                System.out.println("Sever JsonStreamParser" +object.getAsJsonObject().get("user"));
//                            }

//                            DcListPojo content[] = new Gson().fromJson(jsonArray.toString(), DcListPojo[].class);
//                            ArrayList<DcListPojo> dataList = new ArrayList<DcListPojo>(Arrays.asList(content));
                            Vendors_TypeListPojo content[] = new Gson().fromJson(jsonArray.toString(), Vendors_TypeListPojo[].class);
                            ArrayList<Vendors_TypeListPojo> dataList = new ArrayList<Vendors_TypeListPojo>(Arrays.asList(content));

                            vendorsTypeListPojoArrayList.addAll(dataList);

                            vendorsTypeListRecViewAdapter.notifyDataSetChanged();

                            if (vendorsTypeListPojoArrayList.size() == 0) {

                                Toast.makeText(Vendors_Types.this, "List is empty", Toast.LENGTH_SHORT).show();

//                                tv_no_cards.setVisibility(View.VISIBLE);

                            } else {

//                                tv_no_cards.setVisibility(View.INVISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Vendors_Types.this, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof ServerError)
                            Toast.makeText(Vendors_Types.this, "Server Error", Toast.LENGTH_SHORT).show();
                        else if (error instanceof TimeoutError)
                            Toast.makeText(Vendors_Types.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                        else if (error instanceof NetworkError)
                            Toast.makeText(Vendors_Types.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();


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


//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//
//                        System.out.println("Sever Response"+response);
//
//
//                        GroceryListPojo content[] = new Gson().fromJson(jsonArray.toString(), GroceryListPojo[].class);
//                        ArrayList<GroceryListPojo> dataList = new ArrayList<GroceryListPojo>(Arrays.asList(content));
//
//
//                        groceryListPojoArrayList.addAll(dataList);
//
//                        groceryListRecViewAdapter.notifyDataSetChanged();
//
//                        if(groceryListPojoArrayList.size()==0){
//
//                            tv_no_cards.setVisibility(View.VISIBLE);
//
//                        }
//
//
//
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//
//
//                        String body = null;
//                        try {
//                            body = new String(error.networkResponse.data, "UTF-8");
//                            Log.d("Error.Response", body);
//
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                return params;
//            }
//        };

        requestQueue.add(postRequest);


    }

}