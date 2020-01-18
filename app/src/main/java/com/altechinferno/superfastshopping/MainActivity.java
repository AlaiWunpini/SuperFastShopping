package com.altechinferno.superfastshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {

    SpaceNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigationView = findViewById(R.id.space);

        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("History", R.drawable.ic_history_black_24dp));
        navigationView.addSpaceItem(new SpaceItem("Trolley", R.drawable.ic_shopping_cart_black_24dp));
        navigationView.addSpaceItem(new SpaceItem("Profile", R.drawable.ic_person_black_24dp));
        navigationView.addSpaceItem(new SpaceItem("Settings", R.drawable.ic_settings_black_24dp));

        navigationView.showIconOnly();

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                startActivity(new Intent(MainActivity.this,productScan.class));
            }

            @Override
            public void onItemClick(int itemIndex, String Trolley) {
                startActivity(new Intent(MainActivity.this,cart.class));
            }



            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });

    }

    }
