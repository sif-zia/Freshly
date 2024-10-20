package com.example.freshly.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.freshly.room.schema.Schema;

@Entity(tableName = Schema.VENDOR)
public class Vendor {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;
    public String password;
    public String address;
    public String imagePath;
    public String phone;

    public Vendor(String username, String password, String address, String imagePath, String phone) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.imagePath = imagePath;
        this.phone = phone;
    }
}
