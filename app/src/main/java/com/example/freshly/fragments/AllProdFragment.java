package com.example.freshly.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshly.CartActivity;
import com.example.freshly.R;
import com.example.freshly.keys.SharedPreferencesKeys;
import com.example.freshly.listview.ListViewAdapter;
import com.example.freshly.productrecyclerview.ProductAdapter;
import com.example.freshly.room.database.FreshlyDB;
import com.example.freshly.room.relation.ProductWithVendorAndCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AllProdFragment extends Fragment {
    public AllProdFragment() {
    }

    protected View fragmentView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        fragmentView = inflater.inflate(R.layout.fragment_list, container, false);

        new GetAllPrducts().execute();

        return fragmentView;
    }

    class GetAllPrducts extends AsyncTask<Void, Void, List<ProductWithVendorAndCategory>> {

        @Override
        protected List<ProductWithVendorAndCategory> doInBackground(Void... voids) {
            return FreshlyDB
                    .getInstance(getContext())
                    .productDao()
                    .getAllProductsWithDetails();
        }

        @Override
        protected void onPostExecute(List<ProductWithVendorAndCategory> productWithVendorAndCategories) {
            super.onPostExecute(productWithVendorAndCategories);

            if(productWithVendorAndCategories == null || productWithVendorAndCategories.isEmpty())
                return;

            ListView listView = fragmentView.findViewById(R.id.list_view);
            ListViewAdapter listViewAdapter = new ListViewAdapter(productWithVendorAndCategories, getContext());
            listView.setAdapter(listViewAdapter);

            listView.setOnItemClickListener(((adapterView, view, i, l) -> {
                ProductWithVendorAndCategory selectedProduct = productWithVendorAndCategories.get(i);

                new AlertDialog.Builder(requireContext())
                        .setTitle("Add to Cart")
                        .setMessage("Do you want to add this product to cart?")
                        .setPositiveButton("Add to Cart", ((dialogInterface, i1) -> {
                            addProduct(selectedProduct);
                            Toast.makeText(getContext(), "Product added to Cart!", Toast.LENGTH_SHORT).show();
                        }))
                        .setNegativeButton("Cancel", null)
                        .show();
            }));
        }

        protected String productToJSON(List<ProductWithVendorAndCategory> productList) {
            Gson gson = new Gson();
            return gson.toJson(productList);
        }

        protected List<ProductWithVendorAndCategory> JSONToProduct(String productJSON) {
            Type type = new TypeToken<List<ProductWithVendorAndCategory>>(){}.getType();
            Gson gson = new Gson();
            return gson.fromJson(productJSON, type);
        }

        protected void addProduct(ProductWithVendorAndCategory product) {
            SharedPreferences sharedPreferences = getContext()
                    .getSharedPreferences(SharedPreferencesKeys.PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String productJSON = sharedPreferences.getString(SharedPreferencesKeys.CART, "[]");
            List<ProductWithVendorAndCategory> productList = JSONToProduct(productJSON);

            productList.add(product);

            productJSON = productToJSON(productList);
            editor.putString(SharedPreferencesKeys.CART, productJSON);
            editor.apply();
        }
    }
}
