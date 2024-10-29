package com.example.freshly.productrecyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshly.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    private final ImageView productImage;
    private final TextView productTitle;
    private final TextView productDesc;
    private final TextView productVendor;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.product_image);
        productTitle = itemView.findViewById(R.id.product_title);
        productDesc = itemView.findViewById(R.id.product_description);
        productVendor = itemView.findViewById(R.id.product_vendor);
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public TextView getProductTitle() {
        return productTitle;
    }

    public TextView getProductDesc() {
        return productDesc;
    }

    public TextView getProductVendor() {
        return productVendor;
    }
}
