package com.example.laundryclientmobile.apiconnection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.laundryclientmobile.models.Customer;
import com.example.laundryclientmobile.models.HoldTitle;
import com.example.laundryclientmobile.models.Service;
import com.example.laundryclientmobile.ui.login.LoginActivity;

public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_CONTACT_NUMBER = "keycontactnumber";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ADDRESS = "keyaddress";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_ID = "keyid";

    private static final String KEY_SERVICES_ID = "keyservicesid";
    private static final String KEY_SERVICES_NAME = "keyservicesname";
    private static final String KEY_SERVICES_PRICE = "keyservicesprice";
    private static final String KEY_SERVICES_DESC = "keyservicesdesc";
    private static final String KEY_ITEM_NAME_TYPE = "keyitemnametype";
    private static final String KEY_CATEGORY_NAME_TYPE = "keycategorynametype";
    private static final String KEY_STORE_NAME = "keystorename";

    private static final String KEY_STORE_TITLE_NAME = "keystoretitlename";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(Customer customer) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, customer.getId());
        editor.putString(KEY_NAME, customer.getCustomer_name());
        editor.putString(KEY_CONTACT_NUMBER, customer.getCustomer_phone_number());
        editor.putString(KEY_EMAIL, customer.getCustomer_email());
        editor.putString(KEY_ADDRESS, customer.getCustomer_address());
        editor.putString(KEY_USERNAME, customer.getCustomer_username());
        editor.putString(KEY_PASSWORD, customer.getCustomer_password());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public Customer getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Customer(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_CONTACT_NUMBER, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PASSWORD, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    //this method will store the service data in shared preferences
    public void selectedService(Service service) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SERVICES_ID, service.getId());
        editor.putString(KEY_SERVICES_NAME, service.getServices_name());
        editor.putString(KEY_SERVICES_PRICE, service.getServices_price());
        editor.putString(KEY_SERVICES_DESC, service.getServices_desc());
        editor.putString(KEY_ITEM_NAME_TYPE, service.getItem_name());
        editor.putString(KEY_CATEGORY_NAME_TYPE, service.getCategory_name());
        editor.putString(KEY_STORE_NAME, service.getStore_name());
        editor.apply();
    }

    //
    public Service getService() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Service(
                sharedPreferences.getInt(KEY_SERVICES_ID, -1),
                sharedPreferences.getString(KEY_SERVICES_NAME, null),
                sharedPreferences.getString(KEY_SERVICES_PRICE, null),
                sharedPreferences.getString(KEY_SERVICES_DESC, null),
                sharedPreferences.getString(KEY_ITEM_NAME_TYPE, null),
                sharedPreferences.getString(KEY_CATEGORY_NAME_TYPE, null),
                sharedPreferences.getString(KEY_STORE_NAME, null)
        );
    }

    //this method will store the service data in shared preferences
    public void selectedStore(String holdTitle) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_STORE_TITLE_NAME, holdTitle);
        editor.apply();
    }

    //
    public HoldTitle getStore() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new HoldTitle(
                sharedPreferences.getString(KEY_STORE_TITLE_NAME, null)
        );
    }
}
