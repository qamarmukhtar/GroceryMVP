package com.techqamar.myapplication.MovieAdapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.techqamar.myapplication.CartItemActivity;
import com.techqamar.myapplication.CartItem_list_adapter.Add_Rate_ListPojo;
import com.techqamar.myapplication.CartItem_list_adapter.CartItemList_Adapter;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.CommonUtils.VolleyMultipartRequest;
import com.techqamar.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> list;
    RequestQueue requestQueue;
    String Table_No;
    String ADD_RATE;

    public MovieAdapter(Context context, List<Movie> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = list.get(position);

        final String s_id = movie.get_id();
        final String item_id = movie.getItem_id();
        final String imageUrl = movie.getmImageUrl();
//        System.out.println("imageURl" + imageUrl);
        final String item_name = movie.getItem_name();
        final String average_price = movie.getAvg_price();

        holder.item_name.setText(item_name);
        holder.average_price.setText("Average Price " + average_price);
//        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);

        holder.add_Rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String value =charSequence.toString();

                list.get(position).setAdd_rate(holder.add_Rate.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String availCredit = average_price;
                String entrVal = editable.toString();

                if (entrVal != null && !entrVal.isEmpty()) {
                    if (Double.parseDouble(availCredit) >= Double.parseDouble(entrVal)) {
                        Toast.makeText(context, "pleas enter the same amount or grater amount ", Toast.LENGTH_SHORT).show();
                        list.get(position).setAdd_rate(average_price);
                        //     holder.add_Rate.setText(entrVal.substring(0, entrVal.length() - 1));
                    } else {

                        list.get(position).setAdd_rate(holder.add_Rate.getText().toString());
                    }
                }
            }
        });



        holder.remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //post function for each item





                String url = String.format(Urls.STORE_ITEMS_DELETE, Table_No, item_id);

                System.out.println("Sever Response delete " + url);

                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
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
//                                        notifyItemRangeChanged(position, list.size());

                                        list.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, list.size());


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



                            }
                        }
                ) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        return params;
                    }
                };

                requestQueue = Volley.newRequestQueue(context);
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


//        CartItemActivity.Submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ArrayList<Add_Rate_ListPojo> productDetailArrayList = new ArrayList<>();
//
//                boolean callServer = true;
//                ADD_RATE = (holder.add_Rate.getText().toString());
//
//                try {
//                    ADD_RATE = String.valueOf(Integer.parseInt(ADD_RATE));
//                } catch (NumberFormatException e) {
//                    callServer = false;
//                    System.out.println("Add rate to the item");
//                    Toast.makeText(context, "Add rate to the item", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//
//                for (int i = 0; i < list.size(); i++) {
//
//
//
//                    Add_Rate_ListPojo add_rate_listPojo = new Add_Rate_ListPojo();
//
//                    add_rate_listPojo.setP_id(list.get(i).get_id());
////                    System.out.println("Productid =" + list.get(i).get_id());
//                    add_rate_listPojo.setP_price(list.get(i).getAdd_rate());
////                    System.out.println("Set Price =" + list.get(i).getAdd_rate());
//
//                    productDetailArrayList.add(add_rate_listPojo);
////                    System.out.println("productList " + new Gson().toJson(add_rate_listPojo));
//
//
//                }
//
//                Add_Rate_ListPojo add_rate_listPojo = new Add_Rate_ListPojo();
//                add_rate_listPojo.setProductList(productDetailArrayList);
//
//                System.out.println("product List in json" + new Gson().toJson(add_rate_listPojo));
//
//                if (callServer && list.size() > 3) {
//
//                    //call server API here
//                    uploadToServer(add_rate_listPojo);
//                } else {
//
//                    Toast.makeText(context, "Cart item must bet more then 3 ", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView item_name, average_price;
        public EditText add_Rate;
        Button Add_to_cart, remove_from_cart;
        View viewitem;

        public ViewHolder(View itemView) {
            super(itemView);

            viewitem = itemView;
            mImageView = itemView.findViewById(R.id.image_cartlist);
            item_name = itemView.findViewById(R.id.item_name);
            average_price = itemView.findViewById(R.id.average_price);
            add_Rate = itemView.findViewById(R.id.add_rate);
            add_Rate.setVisibility(View.VISIBLE);
//            Add_to_cart = itemView.findViewById(R.id.add_to_cart);
//            Add_to_cart.setVisibility(View.INVISIBLE);
            remove_from_cart = itemView.findViewById(R.id.remove_from_cart);
            remove_from_cart.setVisibility(View.VISIBLE);
        }
    }

    private void uploadToServer(final Add_Rate_ListPojo add_rate_listPojo) {
        requestQueue = Volley.newRequestQueue(context);

        String item_list = new Gson().toJson(add_rate_listPojo);
        System.out.println("JSON" + item_list);


        String url = String.format(Urls.STORE_ITEMS_AVG_RATE_SUBMIT,Table_No, item_list);

        System.out.println("Sever Response " + url);

        final JSONArray jsonArray = new JSONArray();


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.GET, url,
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


        requestQueue.add(multipartRequest);


    }

}
