package com.example.laundryclientmobile.ui.extra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryclientmobile.R;
import com.example.laundryclientmobile.models.Customer;
import com.example.laundryclientmobile.apiconnection.SharedPrefManager;
import com.example.laundryclientmobile.ui.shop.ShopActivity;

import java.util.Calendar;

public class PreSelectShopActivity extends AppCompatActivity {

    TextView textViewAfternoonMorningName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_select_shop);

        textViewAfternoonMorningName = findViewById(R.id.textViewAfternoonMorningName);
        TextView buttonStart_washing_now = findViewById(R.id.buttonStart_washing_now);

        Customer customer = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        buttonStart_washing_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                startActivity(intent);
            }
        });

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            //Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
            textViewAfternoonMorningName.setText("Good Morning: " + customer.getCustomer_name());
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            //Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
            textViewAfternoonMorningName.setText("Good Afternoon: " + customer.getCustomer_name());
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            //Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
            textViewAfternoonMorningName.setText("Good Evening: " + customer.getCustomer_name());
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            //Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
            textViewAfternoonMorningName.setText("Good Night: " + customer.getCustomer_name());
        }
    }
}