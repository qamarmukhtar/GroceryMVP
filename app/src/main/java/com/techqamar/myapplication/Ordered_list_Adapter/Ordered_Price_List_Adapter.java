package com.techqamar.myapplication.Ordered_list_Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.techqamar.myapplication.CartItem_list_adapter.Add_Rate_ListPojo;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.CommonUtils.VolleyMultipartRequest;
import com.techqamar.myapplication.Ordered_Item_List;
import com.techqamar.myapplication.R;
import com.techqamar.myapplication.VendorPriceListAdapter.Vendor_Price_List_Adapter;
import com.techqamar.myapplication.VendorPriceListAdapter.Vendor_Price_List_Pojo;
import com.techqamar.myapplication.Vendor_Price_List;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.techqamar.myapplication.Vendor_Price_List.UserId;
import static com.techqamar.myapplication.Vendor_Price_List.Useraddress;

public class Ordered_Price_List_Adapter extends RecyclerView.Adapter<Ordered_Price_List_Adapter.ViewHolder> {

    private ArrayList<Ordered_Price_List_Pojo> Ordered_list_pojoArrayList;
    private Context context;
    private Ordered_Price_List_Adapter.OnInvoiceOptionclicked onInvoiceOptionclicked;
    RequestOptions option;
    RequestQueue requestQueue;


    public Ordered_Price_List_Adapter(ArrayList<Ordered_Price_List_Pojo> d, Ordered_Price_List_Adapter.OnInvoiceOptionclicked onInvoiceOptionclicked) {
        this.Ordered_list_pojoArrayList = d;
        this.onInvoiceOptionclicked = onInvoiceOptionclicked;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);



    }

    public Ordered_Price_List_Adapter() {

    }


    @Override
    public Ordered_Price_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false);
        return new Ordered_Price_List_Adapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(Ordered_Price_List_Adapter.ViewHolder holder, final int position) {

        final Ordered_Price_List_Pojo vendor_list_pojo = Ordered_list_pojoArrayList.get(position);

        final String name = vendor_list_pojo.getName();
        final String date = vendor_list_pojo.getDate();
        final String order_id = vendor_list_pojo.getOrder_id();
        final String location = vendor_list_pojo.getLocation();
        final String imageUrl = vendor_list_pojo.getmImageUrl();

        holder.item_name.setText(name);
        holder.date.setText(date);
        holder.location.setText(location);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);



        holder.viewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Ordered_Item_List.class);
//                String DATE = date.replaceAll("[^0-9]","");
//                System.out.println("date replase"+DATE);
                intent.putExtra("order_id",order_id);
//                intent.putExtra("actual_price",actual_price);
//                intent.putExtra("item_name",item_name);
//                intent.putExtra("s_id",s_id);
//                intent.putExtra("image_url", Uri.parse(imageUrl));
                context.startActivity(intent);
            }
        });



    }



    @Override
    public int getItemCount() {
        return Ordered_list_pojoArrayList.size();
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

        public ImageView mImageView;
        public TextView item_name, location, date;
        View viewitem;

        public ViewHolder(View itemView) {
            super(itemView);
            viewitem = itemView;
            mImageView = itemView.findViewById(R.id.image_cartlist);
            item_name = itemView.findViewById(R.id.item_name);
            location = itemView.findViewById(R.id.average_price);
            date = itemView.findViewById(R.id.user_price);

        }
    }






}
