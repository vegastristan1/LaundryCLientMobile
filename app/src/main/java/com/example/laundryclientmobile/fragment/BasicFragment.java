package com.example.laundryclientmobile.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryclientmobile.R;
import com.example.laundryclientmobile.apiconnection.Api;
import com.example.laundryclientmobile.apiconnection.HoldTitle;
import com.example.laundryclientmobile.apiconnection.RequestHandler;
import com.example.laundryclientmobile.apiconnection.Service;
import com.example.laundryclientmobile.apiconnection.SharedPrefManager;
import com.example.laundryclientmobile.apiconnection.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView listView;
    ProgressBar progressBar;
    TextView textViewItemName;
    TextView storeTitle;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String getStoreTitle;

    List<Service> serviceList;
    List<Store> storeList;
    public BasicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicFragment newInstance(String param1, String param2) {
        BasicFragment fragment = new BasicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, container, false);
        View dataView = inflater.inflate(R.layout.activity_selected_shop, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_basic, container, false);
        listView = view.findViewById(R.id.listViewBasicItems);
        progressBar = view.findViewById(R.id.progressBarBasicServiceList);
        storeTitle = dataView.findViewById(R.id.textViewTitle);

        serviceList = new ArrayList<>();
        storeList = new ArrayList<>();

        HoldTitle holdTitle = SharedPrefManager.getInstance(getContext()).getStore();

        //setting the values to the textviews
        storeTitle.setText(holdTitle.getStore_name());

        getStoreTitle = storeTitle.getText().toString();
        readItemCategoryByStore(getStoreTitle);

        return view;
    }

    private void readItemCategoryByStore(String getStoreTitle) {
        HashMap<String, String> params = new HashMap<>();
        params.put("store_name", String.valueOf(getStoreTitle));
        params.put("category_name_type", "Basic Items");

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_ITEM_LIST_BY_CATEGORY_TO_STORE, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void refreshServiceList(JSONArray services) throws JSONException {
        serviceList.clear();

        for (int i = 0; i < services.length(); i++) {
            JSONObject obj = services.getJSONObject(i);

            serviceList.add(new Service(
                    obj.getInt("id"),
                    obj.getString("services_name"),
                    obj.getString("services_price"),
                    obj.getString("services_desc"),
                    obj.getString("item_name_type"),
                    obj.getString("category_name_type"),
                    obj.getString("store_name")
            ));

        }

        ServiceAdapter adapter = new ServiceAdapter(serviceList);
        listView.setAdapter(adapter);
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshServiceList(object.getJSONArray("services"));
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

    class ServiceAdapter extends ArrayAdapter<com.example.laundryclientmobile.apiconnection.Service> {
        List<Service> serviceList;

        public ServiceAdapter(List<Service> serviceList) {
            super(listView.getContext(), R.layout.layout_of_services, serviceList);
            this.serviceList = serviceList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_of_services, null, true);

            //Button updateService = listViewItem.findViewById(R.id.buttonEditServiceItem);
            //Button deleteService = listViewItem.findViewById(R.id.buttonDeleteServiceItem);
            Button buttonAdd = listViewItem.findViewById(R.id.buttonAdd);

            textViewItemName = listViewItem.findViewById(R.id.textViewItemName);
            TextView textViewItemDesc = listViewItem.findViewById(R.id.textViewItemDesc);
            TextView textViewItemPrice = listViewItem.findViewById(R.id.textViewItemPrice);

            final Service service = serviceList.get(position);

            textViewItemName.setText(service.getItem_name());
            textViewItemDesc.setText(service.getServices_desc());
            textViewItemPrice.setText(service.getServices_price());
            //add more here to make a visual of the variable

            /*//attaching click listener to update
            updateService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    isUpdating = true;

                    //we will set the selected hero to the UI elements
                    editTextServiceId.setText(String.valueOf(service.getId()));
                    editTextServiceName.setText(String.valueOf(service.getServices_name()));
                    editTextServicePrice.setText(String.valueOf(service.getServices_price()));
                    editTextServiceDesc.setText(String.valueOf(service.getServices_desc()));
                    spinnerItem.setSelection(((ArrayAdapter<String>) spinnerItem.getAdapter()).getPosition(service.getItem_name()));
                    spinnerCategories.setSelection(((ArrayAdapter<String>) spinnerCategories.getAdapter()).getPosition(service.getCategory_name()));

                    //we will also make the button text to Update
                    dialog_btn.setText("Update");
                    dialog.show();
                }
            });*/

            /*//when the user selected delete
            deleteService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // we will display a confirmation dialog before deleting
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Delete " + service.getStore_name())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //if the choice is yes we will delete the hero
                                    //method is commented because it is not yet created
                                    deleteHero(service.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });*/

            return listViewItem;
        }
    }
}