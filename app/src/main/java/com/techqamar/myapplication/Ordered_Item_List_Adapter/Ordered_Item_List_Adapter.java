package com.techqamar.myapplication.Ordered_Item_List_Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.techqamar.myapplication.R;
import com.techqamar.myapplication.Vendor_Price_List;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Ordered_Item_List_Adapter extends RecyclerView.Adapter<Ordered_Item_List_Adapter.ViewHolder> {

    private ArrayList<Ordered_Item_List_Pojo> ordered_Item_list_pojoArrayList;
    private Context context;
    private Ordered_Item_List_Adapter.OnInvoiceOptionclicked onInvoiceOptionclicked;
    RequestOptions option;
    RequestQueue requestQueue;
    double value = Double.parseDouble(null);

    String Table_No, store_id,email_id, compare_string;


    public Ordered_Item_List_Adapter(ArrayList<Ordered_Item_List_Pojo> d, Ordered_Item_List_Adapter.OnInvoiceOptionclicked onInvoiceOptionclicked) {
        this.ordered_Item_list_pojoArrayList = d;
        this.onInvoiceOptionclicked = onInvoiceOptionclicked;


    }

//    public Ordered_Item_List_Adapter() {
//
//    }


    @Override
    public Ordered_Item_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new Ordered_Item_List_Adapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(Ordered_Item_List_Adapter.ViewHolder holder, final int position) {

        final Ordered_Item_List_Pojo vendor_list_pojo = ordered_Item_list_pojoArrayList.get(position);

        final String item_id = vendor_list_pojo.getItem_id();
        final String item_name = vendor_list_pojo.getItem_name();
        final String avg_price = vendor_list_pojo.getAvg_price();
         value = Double.parseDouble(avg_price);
        final String imageUrl = vendor_list_pojo.getmImageUrl();

        holder.item_name.setText(item_name);
//        holder.average_price.setText("Average Price " + avg_price);
//        holder.user_price.setText("User Price " + user_price);
//        holder.vendor_price.setText("Vendor Price " + vendor_price);
        DecimalFormat df = new DecimalFormat("#.##");
        holder.average_price.setText( df.format(value));
        holder.below_average_price.setText("Final Price");
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





    }


    @Override
    public int getItemCount() {
        return ordered_Item_list_pojoArrayList.size();
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
        public TextView item_name, average_price, vendor_price, user_price,below_average_price;
        public EditText add_Rate;
        Button Add_to_cart;
        View viewitem;
        LinearLayout average_price_liner, vendor_price_liner;

        public ViewHolder(View itemView) {
            super(itemView);
            viewitem = itemView;

            mImageView = itemView.findViewById(R.id.image_cartlist);
//            slash1 = itemView.findViewById(R.id.slash1);
//            slash1.setVisibility(View.VISIBLE);
//            slash2 = itemView.findViewById(R.id.slash2);
//            slash2.setVisibility(View.VISIBLE);
            item_name = itemView.findViewById(R.id.item_name);
//            vendor_price = itemView.findViewById(R.id.vendor_price);
//            vendor_price.setVisibility(View.VISIBLE);
//            user_price = itemView.findViewById(R.id.user_price);
//            user_price.setVisibility(View.VISIBLE);
            average_price = itemView.findViewById(R.id.average_price);
            below_average_price = itemView.findViewById(R.id.below_average_price);
//            average_price_liner = itemView.findViewById(R.id.average_price_liner);
//            average_price_liner.setVisibility(View.VISIBLE);
//            vendor_price_liner = itemView.findViewById(R.id.vendor_price_liner);
//            vendor_price_liner.setVisibility(View.VISIBLE);
//            add_Rate = itemView.findViewById(R.id.add_rate);
//            add_Rate.setVisibility(View.INVISIBLE);
//            Add_to_cart = itemView.findViewById(R.id.add_to_cart);
//            Add_to_cart.setVisibility(View.INVISIBLE);

        }
    }


}