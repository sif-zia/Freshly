package com.example.freshly.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshly.R;
import com.example.freshly.productrecyclerview.ProductAdapter;
import com.example.freshly.room.database.FreshlyDB;
import com.example.freshly.room.relation.ProductWithVendorAndCategory;

import java.util.List;

public class FruitsFragment extends Fragment {
    public FruitsFragment() {
    }

    protected View fragmentView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        fragmentView = inflater.inflate(R.layout.fragment_all_prod, container, false);

        new GetAllPrducts().execute();

        return fragmentView;
    }

    class GetAllPrducts extends AsyncTask<Void, Void, List<ProductWithVendorAndCategory>> {

        @Override
        protected List<ProductWithVendorAndCategory> doInBackground(Void... voids) {
            return FreshlyDB
                    .getInstance(getContext())
                    .productDao()
                    .getAllProductsWithDetailsByCat("Fruits");
        }

        @Override
        protected void onPostExecute(List<ProductWithVendorAndCategory> productWithVendorAndCategories) {
            super.onPostExecute(productWithVendorAndCategories);

            if(productWithVendorAndCategories == null || productWithVendorAndCategories.isEmpty())
                return;

            ProductWithVendorAndCategory[] productList = productWithVendorAndCategories.toArray(new ProductWithVendorAndCategory[0]);
            ProductAdapter productAdapter = new ProductAdapter(productList);

            RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(productAdapter);
        }
    }
}
