package com.example.laundryclientmobile.ui.extra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.laundryclientmobile.R;
import com.example.laundryclientmobile.SelectedShopActivity;
import com.example.laundryclientmobile.ui.shop.ShopActivity;

public class PreSelectShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_select_shop);

        TextView buttonStart_washing_now = findViewById(R.id.buttonStart_washing_now);

        buttonStart_washing_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                startActivity(intent);
            }
        });
    }
}