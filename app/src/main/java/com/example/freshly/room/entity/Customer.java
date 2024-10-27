package com.example.freshly.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.freshly.room.schema.Schema;

@Entity(tableName = Schema.CUSTOMER)
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String email;
    public String password;
    public String imagePath;
    public String gender;

    public Customer(String name, String email, String password, String imagePath, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imagePath = imagePath;
        this.gender = gender;
    }
}