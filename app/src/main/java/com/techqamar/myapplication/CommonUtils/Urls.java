package com.techqamar.myapplication.CommonUtils;

public class Urls {


    public static final String BASE_URL2 = "https://flyingbrains.in/admin/";
//    public static final String BASE_URL2 = "http://192.168.29.181/";


    public static final String STORE_ITEMS = BASE_URL2 + "slide1/list_view_of_items.php?id=%s";
    public static final String STORE_ITEMS_ADD = BASE_URL2 + "slide1/tbl_crtn_instg_val.php?id=%s&c_id=%s";
    public static final String STORE_ITEMS_DELETE = BASE_URL2 + "slide1/remove_item.php?t_id=%s&item_id=%s";
    public static final String STORE_ITEMS_AVG_RATE = BASE_URL2 + "slide2/list_view_of_items_cart.php?t_id=%s";
    public static final String STORE_ITEMS_AVG_RATE_SUBMIT = BASE_URL2 + "slide2/updating_usr_price.php?c_id=%s&price_id=%s";
    public static final String VENDOR_ITEMS_AVG_RATE_SUBMIT = BASE_URL2 + "slide3/ven123frm_admin.php?t_id=%s";
    public static final String VENDOR_PRICE_LIST = BASE_URL2 + "slide3/showig_vendor_details.php?t_id=%s&mail=%s";
    public static final String ORDERED_LIST = BASE_URL2 + "slide3/showig_vendor_details.php?t_id=%s&mail=%s";
    public static final String CONFIRM_ORDER = BASE_URL2 + "slide4/tbl_history.php?" +
            "cust_id=%s" +
            "&item_name=%s" +
            "&total_avg_price=%s" +
            "&total_usr_price=%s" +
            "&total_vendor_price=%s" +
            "&payment=%s" +
            "&delivery_loc=%s";
    public static final String REGISTRATION = BASE_URL2 + "slide1/Registration.php?name=%s&email_id=%s&mobile_no=%s&address=%s";
    public static final String DELETE_TABLE = BASE_URL2 + "slide4/remove_item_after_payment.php?t_id=%s&payment=%s";




    public static final String MILK_STORE = BASE_URL2 + "item_details.php?apicall=item_details";//done
    public static final String STORE_ITEM_DETAILS = BASE_URL2 + "store_items.php?id=%s";
    public static final String CART_ITEM_LIST = BASE_URL2 + "quantity_list.php?s_id=%s";
    public static final String BANNER_IMAGES1 = BASE_URL2 + "Banner_img_by_id.php?id=1";
    public static final String ORDER_DETAILS = BASE_URL2 + "active_service_users11.php?";
    public static final String ITEM_DETAILS = BASE_URL2 + "milk_items.php?id=%s";//done
    public static final String ORDER_LIST = BASE_URL2 + "Order_list.php?id=%s";
    public static final String ORDER_DETAILS1 = BASE_URL2 + "active_service_users11.php?" +
            "_id=%s" +
            "&name=%s" +
            "&mobile_no=%s" +
            "&mail=%s" +
            "&product=%s" +
            "&price=%s" +
            "&date_choosen=%s" +
            "&end_date=%s" +
            "&Total_amount=%s" +
            "&payment_status=%s" +
            "&delivery_location=%s" +
            "&order_id=%s" +
            "&ordered_date_time=%s";


}

