package com.techqamar.myapplication.CommonUtils;

public class Urls {


    public static final String BASE_URL2 = "https://flyingbrains.in/admin/";
//    public static final String BASE_URL2 = "http://192.168.29.181/";


    public static final String STORE_ITEMS = BASE_URL2 + "slide1/list_view_of_items.php?id=%s";
    public static final String STORE_ITEMS_ADD = BASE_URL2 + "slide1/tbl_crtn_instg_val.php?id=%s&c_id=%s";
    public static final String STORE_ITEMS_DELETE = BASE_URL2 + "slide1/remove_item.php?t_id=%s&item_id=%s";
    public static final String STORE_ITEMS_AVG_RATE = BASE_URL2 + "slide2/list_view_of_items_cart.php?t_id=%s";
    public static final String STORE_ITEMS_AVG_RATE_SUBMIT = BASE_URL2 + "slide2/updating_usr_price_according_to_city.php?c_id=%s&price_id=%s&user_id=%s";
    public static final String VENDOR_ITEMS_AVG_RATE_SUBMIT = BASE_URL2 + "slide3/ven123frm_admin1.php?t_id=%s&user_id=%s";
    public static final String VENDOR_PRICE_LIST = BASE_URL2 + "slide3/showig_vendor_details.php?t_id=%s&mail=%s";
    public static final String ORDERED_LIST = BASE_URL2 + "slide5/order_history_according_to_date.php?cust_id=%s";
    public static final String ORDERED_ITEM_LIST = BASE_URL2 + "slide5/order_history_by_date.php?cust_id=%s&order_id=%s";
    public static final String CONFIRM_ORDER = BASE_URL2 + "slide4/tbl_history.php?" +
            "cust_id=%s" +
            "&item_name=%s" +
            "&total_avg_price=%s" +
            "&total_usr_price=%s" +
            "&total_vendor_price=%s" +
            "&payment=%s" +
            "&delivery_loc=%s";
    public static final String REGISTRATION = BASE_URL2 + "slide1/Registration.php?name=%s&email_id=%s&mobile_no=%s&address=%s&city=%s";
    public static final String DELETE_TABLE = BASE_URL2 + "slide4/remove_item_after_payment.php?t_id=%s&payment=%s";
    public static final String EMAIL_SEND = BASE_URL2 + "slide4/admin_mail.php?order_id=%s";
    public static final String ORDER_HISTORY = BASE_URL2 + "slide5/dumping_items_to_order.php?cust_id=%s&store_id=%s&tbl_id=%s";
    public static final String ORDER_HISTORY_VENDOR_PRICE_UPDATE = BASE_URL2 + "slide5/vendor_price_from_temp.php?cust_id=%s&store_id=%s&tbl_id=%s&mail=%s&date=%s&order_id=%s";



}

