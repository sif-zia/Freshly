package com.example.freshly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshly.keys.SharedPreferencesKeys;
import com.example.freshly.listview.ListViewAdapter;
import com.example.freshly.productrecyclerview.ProductAdapter;
import com.example.freshly.room.database.FreshlyDB;
import com.example.freshly.room.relation.ProductWithVendorAndCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        List<ProductWithVendorAndCategory> productList = getProducts();
        ListView listView = findViewById(R.id.cart_list_view);
        ListViewAdapter listViewAdapter = new ListViewAdapter(productList, this);
        listView.setAdapter(listViewAdapter);
    }

    protected List<ProductWithVendorAndCategory> JSONToProduct(String productJSON) {
        Type type = new TypeToken<List<ProductWithVendorAndCategory>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(productJSON, type);
    }

    protected List<ProductWithVendorAndCategory> getProducts() {
        Context context = CartActivity.this;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesKeys.PREFERENCES_NAME, Context.MODE_PRIVATE);

        String productJSON = sharedPreferences.getString(SharedPreferencesKeys.CART, "[]");

        return JSONToProduct(productJSON);
    }
}