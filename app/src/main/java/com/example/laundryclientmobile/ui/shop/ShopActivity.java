package com.example.laundryclientmobile.ui.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryclientmobile.R;
import com.example.laundryclientmobile.SelectedShopActivity;
import com.example.laundryclientmobile.apiconnection.RequestHandler;
import com.example.laundryclientmobile.models.Store;
import com.example.laundryclientmobile.ui.extra.PreSelectShopActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class ShopActivity extends AppCompatActivity {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    GridView gridView;
    ProgressBar progressBarLoadStore;

    String[] names = {"Shop 1"};
    int[] images = {R.drawable.pickup_and_delivery_hero_1};

    //List<Store>storeList;
    List<Store>storeList;

    String selectedItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        gridView = findViewById(R.id.gridview);

        progressBarLoadStore = findViewById(R.id.progressBarLoadStore);

        storeList = new ArrayList<>();

        //CustomAdapter customAdapter = new CustomAdapter(names, images, this);

        //gridView.setAdapter(customAdapter);

        findViewById(R.id.buttonBackShop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), PreSelectShopActivity.class));
            }
        });

        readStores();
    }

    private void readStores() {
        PerformNetworkRequest request = new PerformNetworkRequest(com.example.laundryclientmobile.apiconnection.Api.URL_REAL_ALL_STORES, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshStoreList(JSONArray stores) throws JSONException {
        storeList.clear();

        for (int i = 0; i < stores.length(); i++) {
            JSONObject obj = stores.getJSONObject(i);

            storeList.add(new Store(
                    obj.getInt("id"),
                    obj.getString("store_username"),
                    obj.getString("store_email_address"),
                    obj.getString("store_password"),
                    obj.getString("store_name"),
                    obj.getString("store_owner"),
                    obj.getString("store_contact_number"),
                    obj.getString("store_address")
            ));
        }
        CustomAdapter adapter = new CustomAdapter(storeList);
        gridView.setAdapter(adapter);
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLoadStore.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBarLoadStore.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshStoreList(object.getJSONArray("storemanagers"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class CustomAdapter extends ArrayAdapter<Store> {
        private final List<Store> storeList;

        private String[] imageNames;
        private int[] imagePhoto;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(List<Store> storeList) {
            super(ShopActivity.this, R.layout.layout_custom_shop_list, storeList);
            this.storeList = storeList;
        }

        /*public CustomAdapter(String[] imageNames, int[] imagePhoto, Context context){
            this.imageNames = imageNames;
            this.imagePhoto = imagePhoto;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }*/

        @Override
        public int getCount() {
            return storeList.size();
        }

//        @Override
//        public Store getItem(int position) {
//            return storeList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            /*if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.layout_custom_shop_list, parent, false);
            }

            TextView textViewName = convertView.findViewById(R.id.textViewCustomShopName);
            ImageView imageViewPhoto = convertView.findViewById(R.id.imageViewCustomShopImage);

            textViewName.setText(imageNames[position]);
            imageViewPhoto.setImageResource(imagePhoto[position]);*/

            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_custom_shop_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewCustomShopName);
            ImageView imageViewPhoto = listViewItem.findViewById(R.id.imageViewCustomShopImage);

            final Store store = storeList.get(position);

            textViewName.setText(store.getStore_name());

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "Selected Shop " + storeList.get(position).getStore_name(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ShopActivity.this, SelectedShopActivity.class);
                    intent.putExtra("name", storeList.get(position).getStore_name());
                    //intent.putExtra("image", selectedImage);
                    startActivity(intent);
                }
            });

            return listViewItem;
        }
    }






}