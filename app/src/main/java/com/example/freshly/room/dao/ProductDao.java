package com.example.freshly.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.freshly.room.entity.Product;
import com.example.freshly.room.relation.ProductWithVendorAndCategory;

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
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT *"+
            " FROM Products AS P"+
            " JOIN Categories AS C ON P.categoryID = C.id"+
            " JOIN Vendors AS V ON P.vendorID = V.id" +
            " WHERE P.id = :id")
    ProductWithVendorAndCategory getProductDetailsById(long id);

    @Query("SELECT *"+
            " FROM Products AS P"+
            " JOIN Categories AS C ON P.categoryID = C.id"+
            " JOIN Vendors AS V ON P.vendorID = V.id")
    List<ProductWithVendorAndCategory> getAllProductsWithDetails();

    @Query("SELECT *"+
            " FROM Products AS P"+
            " JOIN Categories AS C ON P.categoryID = C.id"+
            " JOIN Vendors AS V ON P.vendorID = V.id"+
            " WHERE C.name LIKE :cat")
    List<ProductWithVendorAndCategory> getAllProductsWithDetailsByCat(String cat);

    @Query("SELECT * FROM Products WHERE categoryID = :id")
    LiveData<List<Product>> getProductsByCategoryId(long id);

    @Query("SELECT * FROM Products WHERE vendorID = :id")
    LiveData<List<Product>> getProductsByVendorId(long id);

    @Query("SELECT * FROM Products WHERE title LIKE '%' || :search || '%'")
    LiveData<List<Product>> findProductsByTitle(String search);

    @Query("SELECT * FROM Products WHERE title LIKE '%' || :search || '%' AND categoryID = :id")
    LiveData<List<Product>> findProductsByTitleAndCategory(String search, long id);
}
