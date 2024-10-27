package com.example.freshly.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
}, version = 2)
public abstract class FreshlyDB extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract CustomerDao customerDao();
    public abstract ProductDao productDao();
    public abstract VendorDao vendorDao();

    private static volatile FreshlyDB instance = null;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // For example, adding a new column
            database.execSQL("ALTER TABLE Customers ADD COLUMN name TEXT");
        }
    };

    public static FreshlyDB getInstance(Context context) {
        if (instance == null) {
            synchronized (FreshlyDB.class) {
                if (instance == null) { // Check again within the synchronized block
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FreshlyDB.class,
                            Schema.DB_NAME
                    )
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return instance;
    }
}
