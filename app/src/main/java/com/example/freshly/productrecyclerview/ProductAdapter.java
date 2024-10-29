package com.example.freshly.productrecyclerview;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshly.R;
import com.example.freshly.room.relation.ProductWithVendorAndCategory;

public class ProductAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ProductWithVendorAndCategory[] productList;

    public ProductAdapter(ProductWithVendorAndCategory[] productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @NonNull View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductWithVendorAndCategory crrProduct = productList[position];

        if(crrProduct.product.imagePath != null) {
            Uri imageUri = Uri.parse(crrProduct.product.imagePath);
            holder.getProductImage().setImageURI(imageUri);
        }
        else {
            holder.getProductImage().setImageResource(R.drawable.placeholder);
        }

        holder.getProductTitle().setText(crrProduct.product.title);
        holder.getProductDesc().setText(crrProduct.product.description);
        holder.getProductVendor().setText(crrProduct.vendor.username);
    }

    @Override
    public int getItemCount() {
        return productList.length;
    }
}
