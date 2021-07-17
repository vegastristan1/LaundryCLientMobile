package com.example.laundryclientmobile.apiconnection;

public class Api {
    private static final String ROOT_URL1 = "http://192.168.88.125/LaundryAppApi/v1/Api.php?apicall=";
    private static final String ROOT_URL2 = "http://192.168.88.125/LaundryAppApi/api2/Api.php?apicall=";

    public static final String URL_REGISTER = ROOT_URL2 + "signupc";
    public static final String URL_LOGIN = ROOT_URL2 + "loginc";

    public static final String URL_CREATE_HERO = ROOT_URL1 + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL1 + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL1 + "updatehero";
    public static final String URL_DELETE_HERO = ROOT_URL1 + "deletehero&id=";

    //Customer Data
    public static final String URL_CREATE_CUSTOMER = ROOT_URL1 + "createcustomer";
    public static final String URL_READ_CUSTOMER = ROOT_URL1 + "getcustomer";
    public static final String URL_UPDATE_CUSTOMER = ROOT_URL1 + "updatecustomer";
    public static final String URL_DELETE_CUSTOMER = ROOT_URL1 + "deleteacustomer&id=";

    //Store Data
    public static final String URL_READ_ITEM_LIST_BY_CATEGORY_TO_STORE = ROOT_URL1 + "getitemlistbycategorytostore";
    public static final String URL_READ_STORES = ROOT_URL1 + "getstores";
    public static final String URL_UPDATE_STORE = ROOT_URL1 + "updatestore";
    public static final String URL_DELETE_STORE = ROOT_URL1 + "deletestore&id=";
    public static final String URL_REAL_ALL_STORES = ROOT_URL1 + "getallstores";

    //Services Data
    public static final String URL_CREATE_SERVICE_TO_ID = ROOT_URL1 + "createservicetoid";
    public static final String URL_READ_SERVICE = ROOT_URL1 + "getallservices";
    public static final String URL_READ_SERVICE_TO_ID = ROOT_URL1 + "getservicestoid";
    public static final String URL_READ_SERVICE_All_INFO = ROOT_URL1 + "getallinfoservices";
    public static final String URL_UPDATE_SERVICE_TO_ID = ROOT_URL1 + "updateservicetoid";
    public static final String URL_DELETE_SERVICE_TO_ID = ROOT_URL1 + "deleteservicetoid&id=";
    public static final String URL_POPULATE_SPINNER_CATEGORIES = ROOT_URL1 + "populatespinnercategories";
}
