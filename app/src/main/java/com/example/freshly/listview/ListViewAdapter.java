package com.example.freshly.listview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.freshly.R;
import com.example.freshly.room.relation.ProductWithVendorAndCategory;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private final List<ProductWithVendorAndCategory> productList;
    private final Context context;

    public ListViewAdapter(List<ProductWithVendorAndCategory> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View itemView, ViewGroup viewGroup) {
        ProductWithVendorAndCategory crrProduct = productList.get(i);

        if(itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.product_item_layout, viewGroup, false);
        }
        ImageView productImage = itemView.findViewById(R.id.product_image);
        TextView productTitle = itemView.findViewById(R.id.product_title);
        TextView productDesc = itemView.findViewById(R.id.product_description);
        TextView productVendor = itemView.findViewById(R.id.product_vendor);

        if(crrProduct.product.imagePath != null) {
            Uri imageUri = Uri.parse(crrProduct.product.imagePath);
            productImage.setImageURI(imageUri);
        }
        else {
            productImage.setImageResource(R.drawable.placeholder);
        }

        productTitle.setText(crrProduct.product.title);
        productDesc.setText(crrProduct.product.description);
        productVendor.setText(crrProduct.vendor.username);

        return itemView;
    }
}
