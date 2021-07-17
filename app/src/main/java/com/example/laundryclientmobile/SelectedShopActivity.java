package com.example.laundryclientmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.laundryclientmobile.apiconnection.Api;
import com.example.laundryclientmobile.apiconnection.HoldTitle;
import com.example.laundryclientmobile.apiconnection.RequestHandler;
import com.example.laundryclientmobile.apiconnection.SharedPrefManager;
import com.example.laundryclientmobile.ui.extra.PreSelectShopActivity;
import com.example.laundryclientmobile.ui.shop.ShopActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryclientmobile.ui.main.SectionsPagerAdapter;
import com.example.laundryclientmobile.databinding.ActivitySelectedShopBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SelectedShopActivity extends AppCompatActivity {

    Button buttonProfile;

    String titleSelectShop;

    TextView textViewtitle;

    private ActivitySelectedShopBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        binding = ActivitySelectedShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        textViewtitle = binding.textViewTitle;

        titleSelectShop = bundle.getString("name");

        textViewtitle.setText(titleSelectShop);

        //storing the user in shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).selectedStore(titleSelectShop);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.buttonBackSelectedShop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), ShopActivity.class));
            }
        });
    }
}