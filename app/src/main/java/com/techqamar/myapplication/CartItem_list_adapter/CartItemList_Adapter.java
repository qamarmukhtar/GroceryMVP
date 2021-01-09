package com.techqamar.myapplication.CartItem_list_adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.techqamar.myapplication.CommonUtils.VolleyMultipartRequest;
import com.techqamar.myapplication.MainItemList;
import com.techqamar.myapplication.MainItem_list_Adapter.MainItemList_Adapter;

import com.techqamar.myapplication.MainItem_list_Adapter.MainItemList_Pojo;
import com.techqamar.myapplication.R;
import com.techqamar.myapplication.interfaces.AddorRemoveCallbacks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartItemList_Adapter extends RecyclerView.Adapter<CartItemList_Adapter.ViewHolder> {

    private ArrayList<CartItemList_Pojo> mainItem_list_pojoArrayList;
    private Context context;

    private CartItemList_Adapter.OnInvoiceOptionclicked onInvoiceOptionclicked;
    RequestOptions option;
    RequestQueue requestQueue;
    String ADD_RATE, Customer_id;
    double Avg_Price;

    double Total_Price = 0;
    String Table_No;

    public CartItemList_Adapter(ArrayList<CartItemList_Pojo> d, OnInvoiceOptionclicked onInvoiceOptionclicked) {
        this.mainItem_list_pojoArrayList = d;
        this.onInvoiceOptionclicked = onInvoiceOptionclicked;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        Table_No = CartItemActivity.table_no().trim();


    }

    public CartItemList_Adapter(Context context) {
        this.context = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        final CartItemList_Pojo mainItem_list_pojo = mainItem_list_pojoArrayList.get(position);

        final String s_id = mainItem_list_pojo.get_id();
        final String item_id = mainItem_list_pojo.getItem_id();
        final String imageUrl = mainItem_list_pojo.getmImageUrl();
//        System.out.println("imageURl" + imageUrl);
        final String item_name = mainItem_list_pojo.getItem_name();
        final String average_price = mainItem_list_pojo.getAvg_price();

        holder.item_name.setText(item_name);
        holder.average_price.setText(average_price);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);

        holder.add_Rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String value = charSequence.toString();

                mainItem_list_pojoArrayList.get(position).setAdd_rate(holder.add_Rate.getText().toString());

////                String value =editable.toString();
//                Avg_Price=Double.parseDouble(((average_price)));
//                int price =(int)(Avg_Price);
//                if(Integer.parseInt(value)<Avg_Price ){
//                    Toast.makeText(context, "Pleas check the amount you entered", Toast.LENGTH_SHORT).show();
//                    mainItem_list_pojoArrayList.get(position).setAdd_rate(average_price);
////                    holder.add_Rate.setText(average_price);
//                }else{
//                    mainItem_list_pojoArrayList.get(position).setAdd_rate(holder.add_Rate.getText().toString());
//                }


//                if(charSequence.toString().equals(average_price)) {
//                    holder.add_Rate.setTextColor(Color.GREEN);
//                }
//                else {
//                    holder.add_Rate.setTextColor(Color.RED);
//                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Double availCredit = Double.parseDouble(average_price);
                Double five_percent = (availCredit) / 100 * 5;
                System.out.println("five_percent " + five_percent);

                String entrVal = editable.toString();

                if (entrVal != null && !entrVal.isEmpty()) {
                    if ((availCredit - five_percent) >= Double.parseDouble(entrVal)) {
                        Toast.makeText(context, "pleas enter the same amount or up to 5% less amount  ", Toast.LENGTH_SHORT).show();
                        mainItem_list_pojoArrayList.get(position).setAdd_rate(average_price);


                        //  holder.add_Rate.setText(entrVal.substring(0, entrVal.length() - 1));
                    }
                    if (Double.parseDouble(average_price) < Double.parseDouble(entrVal)) {
                        Toast.makeText(context, "pleas enter the same amount or Below Average Price  ", Toast.LENGTH_SHORT).show();
                        holder.add_Rate.setText(entrVal.substring(0, entrVal.length() - 1));
                    } else {

                        mainItem_list_pojoArrayList.get(position).setAdd_rate(holder.add_Rate.getText().toString());
                    }
                }
            }
        });


//        holder.Add_to_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //post function for each item
//
//
//            }
//        });
        holder.remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //post function for each item

                String url = String.format(Urls.STORE_ITEMS_DELETE, Table_No, item_id);

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
                                    if (jsonObject.getString("message").equals("Item Deleted Successfully")) {
                                        Toast.makeText(context, "ITEM Deleted", Toast.LENGTH_SHORT).show();
//                                        notifyItemRemoved(position);
//                                        notifyItemRangeChanged(position, mainItem_list_pojoArrayList.size());

                                        mainItem_list_pojoArrayList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, mainItem_list_pojoArrayList.size());
                                        notifyDataSetChanged();
                                        if (context instanceof CartItemActivity) {
                                            ((CartItemActivity)context).getGroceryStoreDetails();
                                        }


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


        CartItemActivity.Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Add_Rate_ListPojo> productDetailArrayList = new ArrayList<>();

                boolean callServer = true;
                ADD_RATE = (holder.add_Rate.getText().toString());

                try {
                    ADD_RATE = String.valueOf(Integer.parseInt(ADD_RATE));
                } catch (NumberFormatException e) {
                    callServer = false;
                    System.out.println("Add rate to the item");
                    Toast.makeText(context, "Add rate to the item", Toast.LENGTH_LONG).show();
                    return;
                }


                for (int i = 0; i < mainItem_list_pojoArrayList.size(); i++) {


//                    if (Integer.parseInt(mainItem_list_pojoArrayList.get(i).getPending()) < Integer.parseInt(mainItem_list_pojoArrayList.get(i).getReturned())) {
//                        Toast.makeText(context, "Error : Please check the enter number.", Toast.LENGTH_SHORT).show();
//
//                        callServer = false;
//                        break;
//                    }


                       /* String returnJar = dataModelArrayList.get(i).getReturned();
                        System.out.println("RetunrnJar3 == " + returnJar);
                        holder.productid = (dataModelArrayList.get(i).getProductid());
                        System.out.println("productid2 == " + (dataModelArrayList.get(i).getProductid()));
                        jar_pending = Integer.parseInt((dataModelArrayList.get(i).getPending()));

                        String return_jar = (returnJar);


                        Emptypojo emptypojo = stringProductPojoHashMap;*/

                    /////////////////


                    Add_Rate_ListPojo add_rate_listPojo = new Add_Rate_ListPojo();

                    add_rate_listPojo.setP_id(mainItem_list_pojoArrayList.get(i).get_id());
//                    System.out.println("Productid =" + mainItem_list_pojoArrayList.get(i).get_id());
                    add_rate_listPojo.setP_price(mainItem_list_pojoArrayList.get(i).getAdd_rate());
//                    System.out.println("Set Price =" + mainItem_list_pojoArrayList.get(i).getAdd_rate());

                    productDetailArrayList.add(add_rate_listPojo);
//                    System.out.println("productList " + new Gson().toJson(add_rate_listPojo));


                }

                Add_Rate_ListPojo add_rate_listPojo = new Add_Rate_ListPojo();
                add_rate_listPojo.setProductList(productDetailArrayList);

                System.out.println("product List in json" + new Gson().toJson(add_rate_listPojo));

                if (callServer && mainItem_list_pojoArrayList.size() > 3) {

                    //call server API here
                    uploadToServer(add_rate_listPojo);
                } else {

                    Toast.makeText(context, "Cart item must bet more then 3 ", Toast.LENGTH_LONG).show();
                }

            }
        });


//        for(int i = 0 ; i < mainItem_list_pojoArrayList.size(); i++) {
//            Total_Price = Total_Price + Double.parseDouble(mainItem_list_pojoArrayList.get(i).getAvg_price());
//        }
//
//        CartItemActivity.Total_Price_toolbar_text.setText(String.valueOf (Total_Price));


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


//        void onPayOptionClicked(MainItemList_Pojo invoiceListPojo);
//        void onPrintOptionClicked(MainItemList_Pojo invoiceListPojo);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView, slash1;
        public TextView item_name, average_price;
        public EditText add_Rate;
        Button Add_to_cart, remove_from_cart;
        View viewitem;
        LinearLayout average_price_liner;


        public ViewHolder(View itemView) {
            super(itemView);
            viewitem = itemView;

            mImageView = itemView.findViewById(R.id.image_cartlist);
            slash1 = itemView.findViewById(R.id.slash1);
            slash1.setVisibility(View.VISIBLE);
            item_name = itemView.findViewById(R.id.item_name);
            average_price = itemView.findViewById(R.id.average_price);
            add_Rate = itemView.findViewById(R.id.add_rate);
            add_Rate.setVisibility(View.VISIBLE);
//            Add_to_cart = itemView.findViewById(R.id.add_to_cart);
//            Add_to_cart.setVisibility(View.INVISIBLE);
            remove_from_cart = itemView.findViewById(R.id.remove_from_cart);
            remove_from_cart.setVisibility(View.VISIBLE);
            average_price_liner = itemView.findViewById(R.id.average_price_liner);
            average_price_liner.setVisibility(View.VISIBLE);


        }
    }


    private void uploadToServer(final Add_Rate_ListPojo add_rate_listPojo) {
        requestQueue = Volley.newRequestQueue(CartItemList_Adapter.this.context);

        String item_list = new Gson().toJson(add_rate_listPojo);
        System.out.println("JSON" + item_list);


        String url = String.format(Urls.STORE_ITEMS_AVG_RATE_SUBMIT, Table_No, item_list);

        System.out.println("Sever Response " + url);

        final JSONArray jsonArray = new JSONArray();


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        System.out.println("resultResponse=" + resultResponse);

                        String result = resultResponse;

                        if (result.contains("successfully updated user value")) {


                            Toast.makeText(context, "Data uploaded Successfully.", Toast.LENGTH_SHORT).show();

//                    Deliveerd();

//                    dataModelArrayList.remove(position);
//                    notifyDataSetChanged();

//                    Intent ieventreport = new Intent(context, HomeScreen.class);
//                    context.startActivity(ieventreport);
                        } else {
                            Toast.makeText(context, "Please check All info Carefully and try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, new VolleyMultipartRequest.VolleyProgressListener() {
            @Override
            public void onProgressUpdate(long progress) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

//                params.put("productList", new Gson().toJson(add_rate_listPojo));
//                System.out.println("ADD_RATE List" + new Gson().toJson(add_rate_listPojo));
//                params.put("c_id", customerid);
//                System.out.println("customerid" + customerid);
//                params.put("productid", productid = (dataModelArrayList.get(position).getProductid()));
//                System.out.println("productid" + (dataModelArrayList.get(position).getProductid()));
//                params.put("emptyContainer", return_jar);
//                System.out.println("emptyContainer" + return_jar);
//                params.put("regno", REGNO);
//                System.out.println("regno" + REGNO);
//                params.put("branchid", branchid);
//                System.out.println("branchid" + branchid);
//                params.put("companyid", companyid);
//                System.out.println("companyid" + companyid);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        multipartRequest.setShouldCache(false);
        requestQueue.add(multipartRequest);


    }


}