package com.example.laundryclientmobile;

import android.content.Intent;
import android.os.Bundle;

import com.example.laundryclientmobile.apiconnection.SharedPrefManager;
import com.example.laundryclientmobile.fragment.BasicFragment;
import com.example.laundryclientmobile.models.Controller;
import com.example.laundryclientmobile.ui.shop.ShopActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryclientmobile.ui.main.SectionsPagerAdapter;
import com.example.laundryclientmobile.databinding.ActivitySelectedShopBinding;

public class SelectedShopActivity extends AppCompatActivity {

    Button buttonProfile;
    String titleSelectShop;
    TextView textViewTitle, noOfItemInCart;
    private ActivitySelectedShopBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final Controller aController = (Controller) getApplicationContext();
        binding = ActivitySelectedShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        noOfItemInCart = binding.noOfItemInCart;
        textViewTitle = binding.textViewTitle;

        titleSelectShop = bundle.getString("name");

        textViewTitle.setText(titleSelectShop);

        //storing the user in shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).selectedStore(titleSelectShop);

        findViewById(R.id.buttonBackSelectedShop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), ShopActivity.class));
            }
        });

        noOfItemInCart.setText(aController.getCart().getCartSize() + " Item >");
        noOfItemInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noOfItemInCart.getText().toString().equals("Item >")){
                    Toast.makeText(SelectedShopActivity.this, "No Item In Cart!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(getApplicationContext(), CartActivity.class);
                    i.putExtra("name", textViewTitle.getText().toString());
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setNoOfItemInCart(String countInCart){
        if(1 >= Integer.parseInt(countInCart)){
            noOfItemInCart.setText(countInCart + " Item >");
        } else{
            noOfItemInCart.setText(countInCart + " Item's >");
        }
    }
}