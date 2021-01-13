package com.techqamar.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.MainItem_list_Adapter.MainItemList_Adapter;
import com.techqamar.myapplication.MainItem_list_Adapter.MainItemList_Pojo;
import com.techqamar.myapplication.interfaces.AddorRemoveCallbacks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainItemList extends AppCompatActivity implements AddorRemoveCallbacks, NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ImageView imgBackButton;
    RequestQueue requestQueue;
    public String Username, UserPhno, Useremail, UserId;
    String id = "86";
    Button Proceed;


    MenuItem menuItem;
    // badge text view
    TextView badgeCounter, venderCounter;
    // change the number to see badge in action
    int pendingNotifications = 0;
    private static int cart_count = 0;
    private static String Table_No;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    MainItemList_Adapter mainItem_list_adapter;
    ArrayList<MainItemList_Pojo> mainItem_list_pojosPojoArrayList = new ArrayList<>();
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        TextView textView = findViewById(R.id.Category_toolbar_text);
//        textView.setText("Milk Packets");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setDrawerLayout(drawer)
//                .build();


        SharedPreferences sh = MainItemList.this.getSharedPreferences("profiledata", Context.MODE_PRIVATE);
        Username = sh.getString("username", "");
        UserPhno = sh.getString("phoneno", "");
        Useremail = sh.getString("email", "");
        UserId = sh.getString("id", "");
        System.out.println("UserId"+UserId);

        Table_No =UserPhno;

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);


//        tv_no_cards = findViewById(R.id.tv_no_cards);


        Proceed = (Button) findViewById(R.id.proceed);

//        new CheckInternetConnection(this).checkConnection();
        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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


        mainItem_list_adapter = new MainItemList_Adapter(mainItem_list_pojosPojoArrayList, new MainItemList_Adapter.OnInvoiceOptionclicked() {
            @Override
            public void onPayOptionClicked(MainItemList_Pojo invoiceListPojo) {


            }

            @Override
            public void onPrintOptionClicked(MainItemList_Pojo invoiceListPojo) {

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mainItem_list_adapter);

        getGroceryStoreDetails();
    }

    public static String table_no() {
        return Table_No;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            startActivity( new Intent(MainItemList.this, MainItemList.class));

        }
        else if (id == R.id.nav_cart) {
            startActivity(new Intent(MainItemList.this, CartItemActivity.class));

        }
        else if (id == R.id.nav_vendor) {
            startActivity(new Intent(MainItemList.this, Vendors_Types.class));

        } else if (id == R.id.nav_ordered) {
            startActivity(new Intent(MainItemList.this, Ordered_List.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getGroceryStoreDetails() {
        cart_count = 0;
        mainItem_list_pojosPojoArrayList.clear();


        String url = String.format(Urls.STORE_ITEMS, id);

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

//                            DcListPojo content[] = new Gson().fromJson(jsonArray.toString(), DcListPojo[].class);
//                            ArrayList<DcListPojo> dataList = new ArrayList<DcListPojo>(Arrays.asList(content));
                            MainItemList_Pojo content[] = new Gson().fromJson(jsonArray.toString(), MainItemList_Pojo[].class);
                            ArrayList<MainItemList_Pojo> dataList = new ArrayList<MainItemList_Pojo>(Arrays.asList(content));

                            mainItem_list_pojosPojoArrayList.addAll(dataList);

                            mainItem_list_adapter.notifyDataSetChanged();

                            if (mainItem_list_pojosPojoArrayList.size() == 0) {

                                Toast.makeText(MainItemList.this, "List is empty", Toast.LENGTH_SHORT).show();

//                                tv_no_cards.setVisibility(View.VISIBLE);

                            } else {

//                                tv_no_cards.setVisibility(View.INVISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainItemList.this, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof ServerError)
                            Toast.makeText(MainItemList.this, "Server Error", Toast.LENGTH_SHORT).show();
                        else if (error instanceof TimeoutError)
                            Toast.makeText(MainItemList.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                        else if (error instanceof NetworkError)
                            Toast.makeText(MainItemList.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();


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

        requestQueue = Volley.newRequestQueue(MainItemList.this);
        postRequest.setShouldCache(false);
        requestQueue.add(postRequest);


    }


    public void viewCart(View view) {
        startActivity(new Intent(MainItemList.this, CartItemActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification, menu);

        menuItem = menu.findItem(R.id.nav_notification);


        // check if any pending notification
//        if (pendingNotifications == 0) {
//            // if no pending notification remove badge
//            menuItem.setActionView(null);
//        } else {

        // if notification than set the badge icon layout
        menuItem.setActionView(R.layout.notification_badge);
        // get the view from the nav item
        View view = menuItem.getActionView();
        // get the text view of the action view for the nav item
        badgeCounter = view.findViewById(R.id.badge_counter);
        venderCounter = view.findViewById(R.id.vendor_counter);

        badgeCounter = view.findViewById(R.id.badge_counter);
        if (cart_count == 0) {
            badgeCounter.setVisibility(View.GONE);
        } else {
            badgeCounter.setVisibility(View.VISIBLE);
        }
        badgeCounter.setText(Integer.toString(cart_count));

        badgeCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainItemList.this, CartItemActivity.class));
                finish();

            }
        });

        venderCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainItemList.this, Vendors_Types.class));

            }
        });

        ImageView baged_cart = (ImageView) view.findViewById(R.id.baged_cart);
        ImageView vender_cart = (ImageView) view.findViewById(R.id.vendor_cart);

        baged_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainItemList.this, CartItemActivity.class));
                finish();
            }
        });
        vender_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainItemList.this, Vendors_Types.class));
              //  finish();
            }
        });
        // set the pending notifications value
//        badgeCounter.setText(String.valueOf(pendingNotifications));
//        }

        return super.onCreateOptionsMenu(menu);
    }

    //


    @Override
    public void onAddProduct() {
        cart_count++;

        invalidateOptionsMenu();
        Snackbar.make((RelativeLayout) findViewById(R.id.stats), "Added to cart !!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    @Override
    public void onRemoveProduct() {
        cart_count--;
        invalidateOptionsMenu();
        Snackbar.make((RelativeLayout) findViewById(R.id.stats), "Removed from cart !!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }


}