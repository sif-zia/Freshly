package com.example.freshly.room.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.freshly.room.schema.Schema;

@Entity(tableName = Schema.PRODUCT)
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public String imagePath;

    public int categoryID;
    public int vendorID;

    public Product(String title, String description, String imagePath, int categoryID, int vendorID) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryID = categoryID;
        this.vendorID = vendorID;
    }
}
