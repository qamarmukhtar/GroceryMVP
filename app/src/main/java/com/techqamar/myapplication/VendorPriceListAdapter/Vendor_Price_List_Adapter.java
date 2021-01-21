package com.techqamar.myapplication.VendorPriceListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.techqamar.myapplication.CartItem_list_adapter.Add_Rate_ListPojo;
import com.techqamar.myapplication.CartItem_list_adapter.CartItemList_Adapter;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.CommonUtils.VolleyMultipartRequest;
import com.techqamar.myapplication.MainItemList;
import com.techqamar.myapplication.R;
import com.techqamar.myapplication.Vendor_Price_List;
import com.techqamar.myapplication.Vendors_TypeListRecViewAdapter.Vendors_TypeListPojo;
import com.techqamar.myapplication.Vendors_Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.techqamar.myapplication.Vendor_Price_List.Useraddress;
import static com.techqamar.myapplication.Vendor_Price_List.UserId;

public class Vendor_Price_List_Adapter extends RecyclerView.Adapter<Vendor_Price_List_Adapter.ViewHolder> {

    private ArrayList<Vendor_Price_List_Pojo> vendor_list_pojoArrayList;
    private Context context;
    private Vendor_Price_List_Adapter.OnInvoiceOptionclicked onInvoiceOptionclicked;
    RequestOptions option;
    RequestQueue requestQueue;
    String ADD_RATE;
    String Table_No, store_id, email_id, compare_string;
    String V_T_P;
    String U_T_P;
    String A_T_P;
    String ITEM_NAME;
    String UserAddress;
    String UserID;
    String DATE;
    String order_id;

    String payment_status = "";
    String payment_status1 = "";

    public Vendor_Price_List_Adapter(ArrayList<Vendor_Price_List_Pojo> d, Vendor_Price_List_Adapter.OnInvoiceOptionclicked onInvoiceOptionclicked) {
        this.vendor_list_pojoArrayList = d;
        this.onInvoiceOptionclicked = onInvoiceOptionclicked;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        Table_No = Vendor_Price_List.table_no().trim();
        store_id = Vendor_Price_List.store_id().trim();
        email_id = Vendor_Price_List.email_id().trim();


    }

    public Vendor_Price_List_Adapter() {

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Vendor_Price_List_Pojo vendor_list_pojo = vendor_list_pojoArrayList.get(position);

        final String item_id = vendor_list_pojo.getItem_id();
        final String item_name = vendor_list_pojo.getItem_name();
        final String avg_price = vendor_list_pojo.getAvg_price();
        final String user_price = vendor_list_pojo.getUser_price();
        final String vendor_price = vendor_list_pojo.getVendor_price();
        final String imageUrl = vendor_list_pojo.getmImageUrl();

        holder.item_name.setText(item_name);
//        holder.average_price.setText("Average Price " + avg_price);
//        holder.user_price.setText("User Price " + user_price);
//        holder.vendor_price.setText("Vendor Price " + vendor_price);
        holder.average_price.setText(avg_price);
        holder.add_price.setText("Your Added Price");
        holder.user_price.setText(user_price);
        holder.vendor_price.setText(vendor_price);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);


//        holder.Add_to_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //post function for each item
//
//
//            }
//        });


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
        Vendor_Price_List.cod_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Add_Rate_ListPojo> productDetailArrayList = new ArrayList<>();

                boolean callServer = true;
//                ADD_RATE = (holder.add_Rate.getText().toString());

//                try {
//                    ADD_RATE = String.valueOf(Integer.parseInt(ADD_RATE));
//                } catch (NumberFormatException e) {
//                    callServer = false;
//                    System.out.println("Add rate to the item");
//                    Toast.makeText(context, "Add rate to the item", Toast.LENGTH_LONG).show();
//                    return;
//                }


                for (int i = 0; i < vendor_list_pojoArrayList.size(); i++) {


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

                    add_rate_listPojo.setP_id(vendor_list_pojoArrayList.get(i).getItem_id());
//                    System.out.println("Productid =" + mainItem_list_pojoArrayList.get(i).get_id());
                    add_rate_listPojo.setP_price(vendor_list_pojoArrayList.get(i).getVendor_price());
//                    System.out.println("Set Price =" + mainItem_list_pojoArrayList.get(i).getAdd_rate());

                    productDetailArrayList.add(add_rate_listPojo);
//                    System.out.println("productList " + new Gson().toJson(add_rate_listPojo));


                }

                Add_Rate_ListPojo add_rate_listPojo = new Add_Rate_ListPojo();
                add_rate_listPojo.setProductList(productDetailArrayList);

                System.out.println("product List in json" + new Gson().toJson(add_rate_listPojo));

                if (callServer) {

                    showAlertDialog();

                    //call server API here
//                    uploadToServer(add_rate_listPojo);
                }

            }
        });


    }

    private void showAlertDialog() {
        final String[] Flag = {""};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Payment Mode");
        String[] items = {"COD Pay", "Online Pay"};
        int checkedItem = -1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Flag[0] = "COD";
                        Toast.makeText(context, "Clicked on COD", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(context, "Clicked on Online Payment", Toast.LENGTH_LONG).show();
                        break;
//                    case 2:
//                        Toast.makeText(Order_Details.this, "Clicked on Data Structures", Toast.LENGTH_LONG).show();
//                        break;
//                    case 3:
//                        Toast.makeText(Order_Details.this, "Clicked on HTML", Toast.LENGTH_LONG).show();
//                        break;
//                    case 4:
//                        Toast.makeText(Order_Details.this, "Clicked on CSS", Toast.LENGTH_LONG).show();
//                        break;
                }
            }
        });

        // add OK and Cancel buttons
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Flag[0] == "COD") {

                    payment_status = "0";
                    uploadToServer();
//                    Toast.makeText(Order_Details.this, "Clicked on COD payment", Toast.LENGTH_LONG).show();

                } else {
                    payment_status = "1";
                    uploadToServer();
//                    Toast.makeText(Order_Details.this, "Clicked on Online payment OK", Toast.LENGTH_LONG).show();
                }
                // user clicked OK
            }
        });
        alertDialog.setNegativeButton("Cancel", null);
// create and show the alert dialog
        AlertDialog dialog = alertDialog.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.black));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
                //dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.black));
            }
        });

        dialog.show();
//        AlertDialog alert = alertDialog.create();
//        alert.setCanceledOnTouchOutside(false);
//        alert.show();
    }

    @Override
    public int getItemCount() {
        return vendor_list_pojoArrayList.size();
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

        public ImageView mImageView, slash1, slash2;
        public TextView item_name, average_price, vendor_price, user_price,add_price;
        public EditText add_Rate;
        Button Add_to_cart;
        View viewitem;
        LinearLayout average_price_liner, vendor_price_liner;

        public ViewHolder(View itemView) {
            super(itemView);
            viewitem = itemView;

            mImageView = itemView.findViewById(R.id.image_cartlist);
            slash1 = itemView.findViewById(R.id.slash1);
            slash1.setVisibility(View.VISIBLE);
            slash2 = itemView.findViewById(R.id.slash2);
            slash2.setVisibility(View.VISIBLE);
            item_name = itemView.findViewById(R.id.item_name);
            vendor_price = itemView.findViewById(R.id.vendor_price);
            vendor_price.setVisibility(View.VISIBLE);
            user_price = itemView.findViewById(R.id.user_price);
            user_price.setVisibility(View.VISIBLE);
            average_price = itemView.findViewById(R.id.average_price);
            add_price = itemView.findViewById(R.id.add_price);
            average_price_liner = itemView.findViewById(R.id.average_price_liner);
            average_price_liner.setVisibility(View.VISIBLE);
            vendor_price_liner = itemView.findViewById(R.id.vendor_price_liner);
            vendor_price_liner.setVisibility(View.VISIBLE);
//            add_Rate = itemView.findViewById(R.id.add_rate);
//            add_Rate.setVisibility(View.INVISIBLE);
//            Add_to_cart = itemView.findViewById(R.id.add_to_cart);
//            Add_to_cart.setVisibility(View.INVISIBLE);

        }
    }


    private void uploadToServer() {

        V_T_P = Vendor_Price_List.v_t_p().trim();
        U_T_P = Vendor_Price_List.u_t_p().trim();
        A_T_P = Vendor_Price_List.a_t_p().trim();
        ITEM_NAME = Vendor_Price_List.item_name().trim();
        UserAddress = Vendor_Price_List.user_address().trim();
        UserID = Vendor_Price_List.user_id().trim();

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait.....");
        dialog.show();


        String customerid = "25659";
//        String item_list = new Gson().toJson(add_rate_listPojo);
//        System.out.println("JSON" + item_list);


        String url = String.format(Urls.CONFIRM_ORDER, UserId, ITEM_NAME
                , A_T_P, U_T_P, V_T_P, payment_status, Useraddress);

        System.out.println("Sever Response " + url);

        final JSONArray jsonArray = new JSONArray();


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                        String resultResponse = new String(response.data);
                        //TODO pk value
                        try {

                            JSONObject result = new JSONObject(resultResponse);
                            System.out.println("resultResponse" + result);
                            JSONObject object = result.getJSONObject("user");
                            String name = result.getString("user");
                            Log.e("sever", name + " ");
                            Log.e("object", object + " ");
                            order_id = object.getString("order_id");
                            Log.e("order_id", order_id + " ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("resultResponse=" + resultResponse);

                        String result = resultResponse.replace("\"", "");

                        if (result.contains("items Returns Successfully Inserted")) {

                            dialog.dismiss();
                            //     Toast.makeText(context, "Data uploaded Successfully.", Toast.LENGTH_SHORT).show();

                            Order_History();
//                            deleteTable();

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

//                params.put("cust_id", "3");
//                params.put("item_name", "Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,");
//                params.put("total_avg_price", "1659.65");
//                params.put("total_usr_price", "6548.65");
//                params.put("total_vendor_price", "9584.36");
//                params.put("COD", "0");
//                params.put("online", "1");
//                params.put("delivery_loc", "K E Board High Scholl Kill Dharwad");

                return params;

            }
        };

        requestQueue = Volley.newRequestQueue(Vendor_Price_List_Adapter.this.context);
        requestQueue.add(multipartRequest);


    }


    private void Order_History() {


        String url = String.format(Urls.ORDER_HISTORY, UserId, store_id, Table_No);

        System.out.println("Sever Response " + url);

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait.....");
        dialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

//                            JSONArray jsonArray = new JSONArray(response);
                            System.out.println("Sever Response jsonObject" + response);
                            JSONObject Object = new JSONObject(response);
                            compare_string = Object.getString("user");
                            System.out.println("compare_string" + compare_string);
                            DATE = Object.getString("date");
                            System.out.println("DATE" + DATE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                        }
                        if (compare_string.contains("items Returns Successfully Inserted")) {
                            dialog.dismiss();
                            Order_History_Vendor_price_update();

                        } else {
                            Toast.makeText(context, "Order History not created.", Toast.LENGTH_SHORT).show();
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


                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(Vendor_Price_List_Adapter.this.context);
        requestQueue.add(postRequest);


    }

    private void Order_History_Vendor_price_update() {


        String url = String.format(Urls.ORDER_HISTORY_VENDOR_PRICE_UPDATE, UserId, store_id, Table_No, email_id, DATE,order_id);

        System.out.println("Sever Response " + url);

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait.....");
        dialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

//                            JSONArray jsonArray = new JSONArray(response);
                            System.out.println("Sever Response jsonObject" + response);
                            JSONObject Object = new JSONObject(response);
                            String compare_string = Object.getString("user");
                            System.out.println("compare_string" + compare_string);


                            if (compare_string.contains("Successfully updated")) {

                                Toast.makeText(context, "Data uploaded Successfully.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                email_send();
                                deleteTable();

                            } else {
                                Toast.makeText(context, "Vendor price Not updated.", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();

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


                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(Vendor_Price_List_Adapter.this.context);
        requestQueue.add(postRequest);


    }



    private void email_send() {

        String url = String.format(Urls.EMAIL_SEND,order_id);

        System.out.println("Sever Response " + url);

        final JSONArray jsonArray = new JSONArray();
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait.....");
        dialog.show();


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        System.out.println("resultResponse=" + resultResponse);

                        String result = resultResponse.replace("\"", "");

                        if (result.contains("successfully sent")) {

                            dialog.dismiss();
//



                        } else {
                            Toast.makeText(context, "Please check All info Carefully and try again.", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
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

//                params.put("cust_id", "3");
//                params.put("item_name", "Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,");
//                params.put("total_avg_price", "1659.65");
//                params.put("total_usr_price", "6548.65");
//                params.put("total_vendor_price", "9584.36");
//                params.put("COD", "0");
//                params.put("online", "1");
//                params.put("delivery_loc", "K E Board High Scholl Kill Dharwad");

                return params;

            }
        };

        requestQueue = Volley.newRequestQueue(Vendor_Price_List_Adapter.this.context);
        requestQueue.add(multipartRequest);


    }

    private void deleteTable() {

        String url = String.format(Urls.DELETE_TABLE, Table_No, payment_status);

        System.out.println("Sever Response " + url);

        final JSONArray jsonArray = new JSONArray();
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait.....");
        dialog.show();


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        System.out.println("resultResponse=" + resultResponse);

                        String result = resultResponse.replace("\"", "");

                        if (result.contains("Success")) {

                            dialog.dismiss();
                            Toast.makeText(context, "Data uploaded Successfully.", Toast.LENGTH_SHORT).show();

//                    Deliveerd();

//                    dataModelArrayList.remove(position);
//                    notifyDataSetChanged();

                            Intent ieventreport = new Intent(context, MainItemList.class);
                            context.startActivity(ieventreport);
                            ((Activity)context).finish();
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

//                params.put("cust_id", "3");
//                params.put("item_name", "Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,Toor Dal 1kg, Red Lobia/ Alasandi Kalu 1kg, Toor Dal 1kg,");
//                params.put("total_avg_price", "1659.65");
//                params.put("total_usr_price", "6548.65");
//                params.put("total_vendor_price", "9584.36");
//                params.put("COD", "0");
//                params.put("online", "1");
//                params.put("delivery_loc", "K E Board High Scholl Kill Dharwad");

                return params;

            }
        };

        requestQueue = Volley.newRequestQueue(Vendor_Price_List_Adapter.this.context);
        requestQueue.add(multipartRequest);


    }
}