package com.techqamar.myapplication.Vendors_TypeListRecViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.techqamar.myapplication.R;
import com.techqamar.myapplication.Vendor_Price_List;


import java.util.ArrayList;


public class Vendors_TypeListRecViewAdapter extends RecyclerView.Adapter<Vendors_TypeListRecViewAdapter.ViewHolder> {

    private ArrayList<Vendors_TypeListPojo> milkTypeListPojoArrayList;
    private Context context;
    private OnInvoiceOptionclicked onInvoiceOptionclicked;
    RequestOptions option;

    public Vendors_TypeListRecViewAdapter(ArrayList<Vendors_TypeListPojo> d, OnInvoiceOptionclicked onInvoiceOptionclicked) {
        this.milkTypeListPojoArrayList = d;
        this.onInvoiceOptionclicked = onInvoiceOptionclicked;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.grocery_store_item_layout, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Vendors_TypeListPojo milkTypeListPojo = milkTypeListPojoArrayList.get(position);

        final String getname = milkTypeListPojo.getName();
        final String getemail = milkTypeListPojo.getEmail_id();
        final String getsum = milkTypeListPojo.getSum();
        final String imageUrl = milkTypeListPojo.getLogo();

        holder.item_name.setText(getname);
        holder.Total_price.setText("Total Price " + getsum);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
        

        holder.viewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Vendor_Price_List.class);
                intent.putExtra("email_id",milkTypeListPojo.getEmail_id());
                System.out.println("EMAIL ID"+milkTypeListPojo.getEmail_id());

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
        return milkTypeListPojoArrayList.size();
    }


    public interface OnInvoiceOptionclicked {

        void onPayOptionClicked(Vendors_TypeListPojo invoiceListPojo);

        void onPrintOptionClicked(Vendors_TypeListPojo invoiceListPojo);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView item_name, Total_price, actual_price;
        //        MaterialRatingBar materialRatingBar;
        View viewitem;

        public ViewHolder(View itemView) {
            super(itemView);
            viewitem = itemView;

            mImageView = itemView.findViewById(R.id.image_cartlist);
            item_name = itemView.findViewById(R.id.item_name);
            Total_price = itemView.findViewById(R.id.Total_price);
            actual_price = itemView.findViewById(R.id.actual_price);


        }
    }

}
