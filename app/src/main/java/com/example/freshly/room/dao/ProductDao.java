package com.example.freshly.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.freshly.room.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    long insert(Product product);

    @Update
    int update(Product product);

    @Delete
    int delete(Product product);

    @Query("SELECT * FROM Products WHERE id = :id")
    Product getProductById(long id);

    @Query("SELECT * FROM Products")
    List<Product> getAllProducts();
}
