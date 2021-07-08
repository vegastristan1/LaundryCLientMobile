package com.example.laundryclientmobile.ui.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laundryclientmobile.R;
import com.example.laundryclientmobile.SelectedShopActivity;
import com.example.laundryclientmobile.ui.extra.PreSelectShopActivity;

public class ShopActivity extends AppCompatActivity {

    GridView gridView;

    String[] names = {"Shop 1"};
    int[] images = {R.drawable.pickup_and_delivery_hero_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        gridView = findViewById(R.id.gridview);

        CustomAdapter customAdapter = new CustomAdapter(names, images, this);

        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = names[position];
                int selectedImage = images[position];

                startActivity(new Intent(ShopActivity.this, SelectedShopActivity.class).putExtra("name", selectedName).putExtra("image", selectedImage));
            }
        });

        findViewById(R.id.buttonBackShop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), PreSelectShopActivity.class));
            }
        });
    }

    public class CustomAdapter extends BaseAdapter{
        private String[] imageNames;
        private int[] imagePhoto;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] imageNames, int[] imagePhoto, Context context){
            this.imageNames = imageNames;
            this.imagePhoto = imagePhoto;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return imagePhoto.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.custom_shop_list, parent, false);

            }

            TextView textViewName = convertView.findViewById(R.id.textViewCustomShopName);
            ImageView imageViewPhoto = convertView.findViewById(R.id.imageViewCustomShopImage);

            textViewName.setText(imageNames[position]);
            imageViewPhoto.setImageResource(imagePhoto[position]);

            return convertView;
        }
    }
}