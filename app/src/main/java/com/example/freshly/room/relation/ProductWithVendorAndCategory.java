package com.example.freshly.room.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.freshly.room.entity.Category;
import com.example.freshly.room.entity.Product;
import com.example.freshly.room.entity.Vendor;

public class ProductWithVendorAndCategory {
    @Embedded
    public Product product;

    @Relation(parentColumn = "vendorID", entityColumn = "id")
    public Vendor vendor;

    @Relation(parentColumn = "categoryID", entityColumn = "id")
    public Category category;
}