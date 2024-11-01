package com.example.freshly.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.freshly.room.entity.Customer;

@Dao
public interface CustomerDao {
    @Insert
    long insert(Customer customer);

    @Update
    int update(Customer customer);

    @Delete
    int delete(Customer customer);

    @Query("SELECT * FROM Customers WHERE id = :id")
    Customer getCustomerById(long id);

    @Query("SELECT EXISTS (SELECT * FROM Customers WHERE email = :email AND password = :password)")
    boolean loginCustomer(String email, String password);

    @Query("SELECT EXISTS (SELECT * FROM Customers WHERE email = :email)")
    boolean isEmailExists(String email);
}
