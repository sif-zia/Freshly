package com.example.freshly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.freshly.fragments.ProductPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    protected String category = "";
    protected ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup ViewPager
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        ProductPagerAdapter productPagerAdapter = new ProductPagerAdapter(this);
        viewPager.setAdapter(productPagerAdapter);

        // Setup TabLayout
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("All");
                    break;
                case 1:
                    tab.setText("Fruits");
                    break;
                case 2:
                    tab.setText("Vegetables");
                    break;
                case 3:
                    tab.setText("Dry Fruits");
                    break;
            }
        }).attach();

        // Cart Button Handler
        Button cartButton = findViewById(R.id.cart_button);
        cartButton.setOnClickListener(view -> {
            Intent launchCartActivity = new Intent(MainActivity.this, CartActivity.class);
            startActivity(launchCartActivity);
        });
    }
}