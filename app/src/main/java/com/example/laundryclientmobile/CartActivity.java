package com.example.laundryclientmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryclientmobile.fragment.BasicFragment;
import com.example.laundryclientmobile.models.Cart;
import com.example.laundryclientmobile.models.Controller;
import com.example.laundryclientmobile.models.Service;
import com.example.laundryclientmobile.models.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    ListView listViewCartItem;
    ListView listViewPaymentItem;
    Double totalPrice = (double) 0.0;
    float tempPrice;
    int qty = 1;

    String[] arrayListCartQty;

    Button addButton;
    Button minusButton;
    String click = "";

    TextView grandTotalPrice;
    TextView cartTotalPrice;
    String titleSelectShop;

    ArrayList<String> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Bundle bundle = getIntent().getExtras();
        titleSelectShop = bundle.getString("name");

        listViewCartItem = findViewById(R.id.listViewCartItemList);
        listViewPaymentItem = findViewById(R.id.listViewPaymentSummaryList);
        TextView showStoreName = findViewById(R.id.textViewStoreNameCart);
        ImageButton back = findViewById(R.id.imageButtonBackToSelectedShop);
        grandTotalPrice = findViewById(R.id.textViewGrandTotalPrice);
        cartTotalPrice = findViewById(R.id.textViewCartTotalPrice);

        showStoreName.setText(titleSelectShop);

        cartList = new ArrayList<>();

        refreshCartList();
        refreshPaymentList();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, SelectedShopActivity.class);
                intent.putExtra("name", showStoreName.getText().toString());
                refreshPaymentList();
                refreshCartList();

                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void refreshCartList() {
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();
        // Get Cart Size
        final int cartSize = aController.getCart().getCartSize();
        cartList.clear();

        for (int i = 0; i < cartSize; i++) {
            Service obj = aController.getCart().getService(i);
            cartList.add(String.valueOf(new Service(
                    obj.getId(),
                    obj.getServices_name(),
                    obj.getServices_price(),
                    obj.getServices_desc(),
                    obj.getItem_name(),
                    obj.getCategory_name(),
                    obj.getStore_name()
            )));
        }

        CartAdapter adapter = new CartAdapter(cartList);
        listViewCartItem.setAdapter(adapter);
    }

    class CartAdapter extends ArrayAdapter<String> {
        ArrayList<String> cartService;

        final Controller aController = (Controller) getApplicationContext();

        public CartAdapter(ArrayList<String> cartService) {
            super(listViewCartItem.getContext(), R.layout.layout_cart_item, cartService);
            this.cartService = cartService;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_cart_item, null, true);

            /*System.out.println(aController.getCart().getService(position).getId() + " "
                    + aController.getCart().getService(position).getServices_price() + " qty: " + qty);*/

            addButton = listViewItem.findViewById(R.id.addBtn);
            minusButton = listViewItem.findViewById(R.id.removeBtn);
            ImageButton imageButtonDeleteItem = listViewItem.findViewById(R.id.imageButtonDeleteSelectedCartItem);

            TextView textViewItemQty = listViewItem.findViewById(R.id.itemQuanEt);

            TextView textViewItemName = listViewItem.findViewById(R.id.textViewServiceName);
            TextView textViewItemDesc = listViewItem.findViewById(R.id.textViewServiceDesc);
            TextView textViewItemPrice = listViewItem.findViewById(R.id.textViewServicePrice);

            int intSelectedID = aController.getCart().getService(position).getId();

            textViewItemName.setText(aController.getCart().getService(position).getItem_name());
            textViewItemDesc.setText(aController.getCart().getService(position).getServices_desc());
            textViewItemPrice.setText(aController.getCart().getService(position).getServices_price());
            textViewItemQty.setText(aController.getCart().getService(position).getItem_qty());
            //add more here to make a visual of the variable

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click = "";

                    int tempQty = Integer.parseInt(aController.getCart().getService(position).getItem_qty());
                    tempQty = tempQty + 1;

                    textViewItemQty.setText(String.valueOf(tempQty));
                    aController.getCart().getService(position).setItem_qty(textViewItemQty.getText().toString());

                    int tempQtyForTotal = Integer.parseInt(aController.getCart().getService(position).getItem_qty());
                    String tempPrice = aController.getCart().getService(position).getServices_price();

                    double convertTempPriceToInt = Double.parseDouble(tempPrice);

                    System.out.println("Total Price of " + tempPrice + " and " + tempQtyForTotal + " = " + (convertTempPriceToInt * tempQtyForTotal));
                    System.out.println("this is the selected cart Item no " + intSelectedID);
                    System.out.println("this is the selected cart position no " + position);

                    aController.getCart().getService(position).setItem_totalPrice(String.valueOf(convertTempPriceToInt * tempQtyForTotal));

                    //set add text for switch to add the value of qty
                    click = "add";
                    refreshPaymentList();
                    //minusButton.setEnabled(true);

                }
            });

            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click = "";
                    int tempQty = Integer.parseInt(aController.getCart().getService(position).getItem_qty());
                    tempQty = tempQty - 1;

                    textViewItemQty.setText(String.valueOf(tempQty));
                    aController.getCart().getService(position).setItem_qty(textViewItemQty.getText().toString());

                    int tempQtyForTotal = Integer.parseInt(aController.getCart().getService(position).getItem_qty());
                    String tempPrice = aController.getCart().getService(position).getServices_price();

                    double convertTempPriceToInt = Double.parseDouble(tempPrice);

                    System.out.println("Total Price of " + tempPrice + " and " + tempQtyForTotal + " = " + (convertTempPriceToInt / tempQtyForTotal));
                    System.out.println("this is the selected cart Item no " + intSelectedID);
                    System.out.println("this is the selected cart position no " + position);

                    aController.getCart().getService(position).setItem_totalPrice(String.valueOf(convertTempPriceToInt / tempQtyForTotal));

                    //set minus text for switch to minus the value of qty
                    click = "minus";

                    refreshPaymentList();
                }
            });

            imageButtonDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aController.getCart().removeService(position);
                    Toast.makeText(CartActivity.this, "Cart Item No. " + position + " Remove!", Toast.LENGTH_SHORT).show();
                    refreshCartList();
                    refreshPaymentList();
                }
            });

            return listViewItem;
        }
    }

    private void refreshPaymentList() {
        totalPrice = 0.0;
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Get Cart Size
        final int cartSize = aController.getCart().getCartSize();
        cartList.clear();

        for (int i = 0; i < cartSize; i++) {
            Service obj = aController.getCart().getService(i);

            cartList.add(String.valueOf(new Service(
                    obj.getId(),
                    obj.getServices_name(),
                    obj.getServices_price(),
                    obj.getServices_desc(),
                    obj.getItem_name(),
                    obj.getCategory_name(),
                    obj.getStore_name()
            )));

            double tempPrice = Double.parseDouble(aController.getCart().getService(i).getItem_totalPrice());
            if(tempPrice == 0.0){
                double tempPrice1 = Double.parseDouble(aController.getCart().getService(i).getServices_price());
                totalPrice = (totalPrice + tempPrice1);
            }else{
                totalPrice = (totalPrice + tempPrice);
            }

            if(cartList.isEmpty()){
                totalPrice = 0.0;
            }

        }

        grandTotalPrice.setText(String.format("Php: %.2f", totalPrice));
        cartTotalPrice.setText(String.format("Total Price: Php %.2f", totalPrice));

        PaymentAdapter adapter = new PaymentAdapter(cartList);
        listViewPaymentItem.setAdapter(adapter);
    }

    class PaymentAdapter extends ArrayAdapter<String> {
        ArrayList<String> cartService;

        final Controller aController = (Controller) getApplicationContext();

        public PaymentAdapter(ArrayList<String> cartService) {
            super(listViewPaymentItem.getContext(), R.layout.layout_payment_item, cartService);
            this.cartService = cartService;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_payment_item, null, true);
            //Button updateService = listViewItem.findViewById(R.id.buttonEditServiceItem);
            //Button deleteService = listViewItem.findViewById(R.id.buttonDeleteServiceItem);

            int tempQtyForTotal = Integer.parseInt(aController.getCart().getService(position).getItem_qty());
            float tempPrice = Float.parseFloat(aController.getCart().getService(position).getServices_price());

            TextView textViewItemQty = listViewItem.findViewById(R.id.textViewPaymentQty);
            TextView textViewSubTotal = listViewItem.findViewById(R.id.textViewSubTotalByItem);
            TextView textViewItemName = listViewItem.findViewById(R.id.textViewPaymentServiceName);
            TextView textViewItemDesc = listViewItem.findViewById(R.id.textViewPaymentDesc);
            TextView textViewItemPrice = listViewItem.findViewById(R.id.textViewPaymentAmount);

            textViewItemName.setText(aController.getCart().getService(position).getItem_name());
            textViewItemDesc.setText(aController.getCart().getService(position).getServices_desc());
            textViewItemPrice.setText("Php " + aController.getCart().getService(position).getServices_price());
            textViewItemQty.setText("Qty: " + aController.getCart().getService(position).getItem_qty());
            textViewSubTotal.setText("Sub Total: " + (tempPrice * tempQtyForTotal));
            //textViewItemQty.setText("Qty: " + aController.getCart().getService(position).getItemQty());
            //add more here to make a visual of the variable
            return listViewItem;
        }
    }
}