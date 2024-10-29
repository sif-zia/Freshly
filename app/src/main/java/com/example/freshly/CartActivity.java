package com.example.freshly;

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

import com.example.freshly.listview.ListViewAdapter;
import com.example.freshly.productrecyclerview.ProductAdapter;
import com.example.freshly.room.database.FreshlyDB;
import com.example.freshly.room.relation.ProductWithVendorAndCategory;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        new GetAllPrducts().execute();
    }

    class GetAllPrducts extends AsyncTask<Void, Void, List<ProductWithVendorAndCategory>> {

        @Override
        protected List<ProductWithVendorAndCategory> doInBackground(Void... voids) {
            return FreshlyDB
                    .getInstance(CartActivity.this)
                    .productDao()
                    .getAllProductsWithDetails();
        }

        @Override
        protected void onPostExecute(List<ProductWithVendorAndCategory> productWithVendorAndCategories) {
            super.onPostExecute(productWithVendorAndCategories);

            if(productWithVendorAndCategories == null || productWithVendorAndCategories.isEmpty())
                return;

            ListView listView = findViewById(R.id.cart_list_view);
            ListViewAdapter listViewAdapter = new ListViewAdapter(productWithVendorAndCategories, CartActivity.this);
            listView.setAdapter(listViewAdapter);
        }
    }
}