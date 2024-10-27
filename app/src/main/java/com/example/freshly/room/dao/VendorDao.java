package com.example.freshly.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.freshly.room.entity.Vendor;

@Dao
public interface VendorDao {
    @Insert
    long insert(Vendor vendor);

    @Update
    int update(Vendor vendor);

    @Delete
    int delete(Vendor vendor);

    @Query("SELECT * FROM Vendors WHERE id = :id")
    Vendor getVendorById(long id);

    @Query("SELECT EXISTS (SELECT * FROM Vendors WHERE username = :username)")
    boolean doesUsernameExists(String username);

    @Query("SELECT EXISTS (SELECT * FROM Vendors WHERE username = :username AND password = :password)")
    boolean loginVendor(String username, String password);
}
