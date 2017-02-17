package com.kencloud.partner.user.app_util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Constants {
    public static boolean fromCheckout = false;
    public static int List_Index=0;
    public static int List_Top=0;
    public static String strShPrefUserSelection = "UserSelection";
    public static String strShUserShopSelected = "UserShopSelected";
    public static String strShUserStoreID = "UserStoreid";
    public static String strShUserShopFood = "food";
    public static String strShUserShopDrink = "drink";
    public static String strShUserShopDay = "day";
    public static String strShUserShopGift = "gift";
    public static String strShUserShopEvent = "event";
    public static String strCatName="";
    public static String strAge ="age";
    public static String strAgeNumber ="18";
    public static String strTipPercent ="";
    public static Boolean strSeeAllProduct =false;
    // Shared Pref Details
    public static String strShPrefDelAddrName = "ShPrefDeliveryAddr";
    public static String strShPrefDelLat = "deliveryLat";
    public static String strShPrefDelLong = "deliveryLong";
    public static String strShPrefDelAddr = "deliveryAdd";
    public static String strShPrefDelCallFrom = "No";
    public static String strShPrefDeliver = "false";
    // Shared Pref Details
    public static String strShPrefBrowseSearch = "ShPrefBrowseSearch";
    public static String strShPrefBrowseSearchOnce = "SearchOnce";
    public static String strShPrefBrowseCheckoutOnce = "CheckoutOnce";

    // Shared Pref Details Price and Qnty of add to cart/////////////
    public static String strShPrefAddToCart = "ShPrefAddToCart";
    public static String strShPrefPrice = "price";
    public static String strShPrefOnty = "qnty";
    public static String strShPrefIsInsert = "No";


    public static String deliverytime="";
    public static String push_count="0";
    public static String deliveryDay="";
    // Shared Pref User Details
    public static String strShPrefUserPrefName = "ShPrefUser";
    public static String strShPrefUserId = "userId";
    public static String strShPrefUserFname = "fName";
    public static String strShPrefUserLname = "lName";
    public static String strShPrefUserName = "name";
    public static String strShPrefUserEmail = "email";
    public static String strShPrefUserPhone = "phone";
    public static String strShPrefUserCountryCode = "counrycode";
    public static String strShPrefUserDOB = "dob";
    public static String strShPrefUserFbId = "fbId";
    public static String strShUserProductId = "UserProductId";
    public static String strShUserStoreId = "UserStoreId";
    public static String strShStoreID = "UserStoreID";
    public static String strShPrefUserDeviceReg = "deviceIsReg";
    public static Boolean flagMapPage = false;
    public static boolean flagLocation = false;
    public static String strCurLat = "22.4";
    public static String strCurLong = "88.6";
    public static String lastDeliveryTime0="";
    public static String lastDeliveryTime40="";
    public static String lastDeliveryTime15="";
    public static String isFirstTimeDelivery="";
    public  static String deliveryInstruction="";
    public  static String deliverItToMe="";
    public  static String deliverPersonName="";
    public  static String deliverPersonSurName="";
    public  static String deliverPersonNumner="";
    public  static String deliverPersonCountryCode="";
    public  static String fName="";
    public  static String lName="";
    public  static String emailAdd="";
    public  static String mobilenum="";
    public  static String fromOrderStatus="";
    public  static String address="";
    public  static String orderID="";
    public  static String latitude="";
    public  static String longitude="";
    public  static String productIDS="";
    public  static String paymenttype="";
    public  static String deliverytypeCart="";
    public  static String deliverytypeCheckout="";
    public  static String deliveryDate="";
    public  static String deliveryDateCheckout="";
    public  static double deliveryfee=0;
    public  static float serviceTax=0;
    public  static double driver_tip=0;
    public  static String cc_owner="";
    public  static String cc_owner_surname="";
    public  static String cc_number="";
    public  static String cc_type="";
    public  static String cc_cvv="";
    public  static String cc_expiry_month="";
    public  static String cc_expiry_year="";
    public  static String cc_card_type="";
    public  static String cc_nickname="";
    public  static String cc_token="";
    public  static String order_status_date="";
    public  static String promo_coupon="";
    public  static String promo_coupon_discount_price="";
    public  static String qoute_id="";
    public  static String return_url="http://www.stockup.co.za/payfast/payfastreturn.php";
    public  static String cancel_url="http://www.stockup.co.za/payfast/payfastcancel.php";
    public  static String notify_url="http://www.stockup.co.za/payfast/payfastnotify.php";
    public static String urlMain = "http://cruiseaelb-538663152.ap-southeast-2.elb.amazonaws.com/api/v2";
    public static String urlGetLocation = "/get_latlong";

    public static String urlMainStockUp = "http://hireipho.nextmp.net/stockup/webservices";
    public static String urlGetDelData = "/order/orderLastInfo";
    public static String urlWhereWeServe = "https://www.stockup.co.za/where-serve-app";
    public static String urlFAQs = "https://www.stockup.co.za/faqs_app";
    public static String urlPrivacyPolicy = "https://www.stockup.co.za/privacy_policy_app";
    public static String urlTermsCond = "https://www.stockup.co.za/terms-conditions-app";
    public static String urlCoverage = "https://www.stockup.co.za/coverage_app";
    public static String urlShareShoppingList = "https://www.stockup.co.za/productlist/list/addlist/list_id/";

    public static ArrayList<String> arrMixed=new ArrayList<>();
    //public static ArrayList<List<String> arrSubCat=new ArrayList<>();

    public static String strShPrefBillingLat = "billingLat";
    public static String strShPrefBillingLong = "billingLong";
    public static String strShPrefBillingAddr = "billingAdd";



    public static String strGetAllHub = "http://www.stockup.co.za/webservices/hub/getAllHub";


    public static String API_BASE="https://www.stockup.co.za/webservices/";
    public static String paymentType="Payfast";

    public static String strAppVersion = "3.0.9";
}
