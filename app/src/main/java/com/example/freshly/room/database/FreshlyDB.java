package com.example.freshly.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.freshly.room.dao.CategoryDao;
import com.example.freshly.room.dao.CustomerDao;
import com.example.freshly.room.dao.ProductDao;
import com.example.freshly.room.dao.VendorDao;
import com.example.freshly.room.entity.Category;
import com.example.freshly.room.entity.Customer;
import com.example.freshly.room.entity.Product;
import com.example.freshly.room.entity.Vendor;
import com.example.freshly.room.schema.Schema;

@Database(entities = {
        Product.class,
        Vendor.class,
        Customer.class,
        Category.class
}, version = 1)
public abstract class FreshlyDB extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract CustomerDao customerDao();
    public abstract ProductDao productDao();
    public abstract VendorDao vendorDao();

    private static FreshlyDB instance = null;

    public static FreshlyDB getInstance(Context context) {
        if (instance == null) {
            synchronized (FreshlyDB.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), FreshlyDB.class, Schema.DB_NAME).build();
            }
        }
        return instance;
    }
}
