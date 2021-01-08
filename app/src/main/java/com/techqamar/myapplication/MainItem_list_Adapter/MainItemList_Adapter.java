package com.techqamar.myapplication.MainItem_list_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.techqamar.myapplication.CartItemActivity;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.MainItemList;
import com.techqamar.myapplication.R;
import com.techqamar.myapplication.interfaces.AddorRemoveCallbacks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainItemList_Adapter extends RecyclerView.Adapter<MainItemList_Adapter.ViewHolder> {

    private ArrayList<MainItemList_Pojo> mainItem_list_pojoArrayList;
    private Context context;
    private OnInvoiceOptionclicked onInvoiceOptionclicked;
    RequestOptions option;
    String ProductId, Customer_id;
    RequestQueue requestQueue;
    String Table_No;

    public MainItemList_Adapter(ArrayList<MainItemList_Pojo> d, OnInvoiceOptionclicked onInvoiceOptionclicked) {
        this.mainItem_list_pojoArrayList = d;
        this.onInvoiceOptionclicked = onInvoiceOptionclicked;


        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        Table_No = MainItemList.table_no().trim();
    }

    public MainItemList_Adapter() {


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final MainItemList_Pojo mainItem_list_pojo = mainItem_list_pojoArrayList.get(position);

        final String s_id = mainItem_list_pojo.get_id();
        holder.product_id = s_id;
//        ProductId=mainItem_list_pojoArrayList.get(position).get_id();
//        System.out.println("ProductId"+ProductId);
        final String imageUrl = mainItem_list_pojo.getmImageUrl();
        System.out.println("imageURl" + imageUrl);
        final String item_name = mainItem_list_pojo.getItem_name();
        final String average_price = mainItem_list_pojo.getAvg_price().trim();
        double value = Double.parseDouble(average_price);

        holder.item_id.setText(s_id);
        holder.item_name.setText(item_name);
        DecimalFormat df = new DecimalFormat("#.##");
        holder.average_price.setText( df.format(value));
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);

        holder.Add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //post function for each item
                if (!mainItem_list_pojoArrayList.get(position).isAddedTocart()) {
                    mainItem_list_pojoArrayList.get(position).setAddedTocart(true);
                    holder.Add_to_cart.setText("Remove");
                    if (context instanceof MainItemList) {
                        ((AddorRemoveCallbacks) context).onAddProduct();
//                        Add_Product(mainItem_list_pojoArrayList.get(position).get_id());





                        String url = String.format(Urls.STORE_ITEMS_ADD, holder.product_id, Table_No);

                        System.out.println("Sever Response add " + url);

                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
//                                            jsonObject.getString("message");


                                            System.out.println("Sever Response jsonObject" + response);
//                                            System.out.println("Sever Response jsonObject" + jsonObject);
                                            if (jsonObject.getString("message").equals("items Returns Successfully Inserted")) {
                                                Toast.makeText(context, "ITEM ADDED", Toast.LENGTH_SHORT).show();
                                            }
//                                            JSONArray jsonArray = jsonObject.getJSONArray("user");
//                                            System.out.println("Sever Response jsonObject" + jsonArray);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        if (error instanceof ServerError)
                                            Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                                        else if (error instanceof TimeoutError)
                                            Toast.makeText(context, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                                        else if (error instanceof NetworkError)
                                            Toast.makeText(context, "Bad Network Connection", Toast.LENGTH_SHORT).show();


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

                       RequestQueue requestQueue = Volley.newRequestQueue(context);
                        postRequest.setShouldCache(false);
                        requestQueue.add(postRequest);
                    }

                } else {
                    mainItem_list_pojoArrayList.get(position).setAddedTocart(false);
                    holder.Add_to_cart.setText("Add to Cart");

                    if (context instanceof MainItemList) {
                        ((AddorRemoveCallbacks) context).onRemoveProduct();
//                        Add_Product(mainItem_list_pojoArrayList.get(position).get_id());


                        requestQueue = Volley.newRequestQueue(context);


                        String url = String.format(Urls.STORE_ITEMS_DELETE, Table_No, holder.product_id);

                        System.out.println("Sever Response delete " + url);

                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
//                                            jsonObject.getString("message");


                                            System.out.println("Sever Response jsonObject" + response);
//                                            System.out.println("Sever Response jsonObject" + jsonObject);
                                            if (jsonObject.getString("message").equals("items Returns Successfully Inserted")) {
                                                Toast.makeText(context, "ITEM ADDED", Toast.LENGTH_SHORT).show();
                                            }
//                                            JSONArray jsonArray = jsonObject.getJSONArray("user");
//                                            System.out.println("Sever Response jsonObject" + jsonArray);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        if (error instanceof ServerError)
                                            Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                                        else if (error instanceof TimeoutError)
                                            Toast.makeText(context, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                                        else if (error instanceof NetworkError)
                                            Toast.makeText(context, "Bad Network Connection", Toast.LENGTH_SHORT).show();


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

                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        postRequest.setShouldCache(false);
                        requestQueue.add(postRequest);
                    }

                }
            }

        });


        holder.viewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, Order_Details.class);
//                intent.putExtra("selling_price",selling_price);
//                intent.putExtra("actual_price",actual_price);
//                intent.putExtra("item_name",item_name);
//                intent.putExtra("s_id",s_id);
//                intent.putExtra("image_url", Uri.parse(imageUrl));
//                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mainItem_list_pojoArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public interface OnInvoiceOptionclicked {


        void onPayOptionClicked(MainItemList_Pojo invoiceListPojo);

        void onPrintOptionClicked(MainItemList_Pojo invoiceListPojo);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView item_name, average_price, item_id;
        EditText add_rate;
        Button Add_to_cart;
        View viewitem;
        String product_id;

        public ViewHolder(View itemView) {
            super(itemView);
            viewitem = itemView;

            mImageView = itemView.findViewById(R.id.image_cartlist);
            item_name = itemView.findViewById(R.id.item_name);
            item_id = itemView.findViewById(R.id.item_id);
            average_price = itemView.findViewById(R.id.average_price);
            Add_to_cart = itemView.findViewById(R.id.add_to_cart);
            Add_to_cart.setVisibility(View.VISIBLE);
            add_rate = itemView.findViewById(R.id.add_rate);
            add_rate.setVisibility(View.GONE);

        }
    }

    private void Add_Product(String id) {

        requestQueue = Volley.newRequestQueue(context);


        String url = String.format(Urls.STORE_ITEMS_ADD, ProductId, Customer_id = "25659");

        System.out.println("Sever Response " + url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            System.out.println("Sever Response jsonObject" + response);

                            JSONArray jsonArray = jsonObject.getJSONArray("user");
                            System.out.println("Sever Response jsonObject" + jsonArray);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof ServerError)
                            Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                        else if (error instanceof TimeoutError)
                            Toast.makeText(context, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                        else if (error instanceof NetworkError)
                            Toast.makeText(context, "Bad Network Connection", Toast.LENGTH_SHORT).show();


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


        requestQueue.add(postRequest);


    }

}
