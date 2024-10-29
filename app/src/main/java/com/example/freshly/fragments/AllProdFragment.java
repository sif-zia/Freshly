package com.example.freshly.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.freshly.listview.ListViewAdapter;
import com.example.freshly.productrecyclerview.ProductAdapter;
import com.example.freshly.room.database.FreshlyDB;
import com.example.freshly.room.relation.ProductWithVendorAndCategory;

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
                Toast.makeText(getContext(), "Item No. " + i + " Clicked", Toast.LENGTH_SHORT).show();
            }));
        }
    }
}
