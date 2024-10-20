package com.example.freshly.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.freshly.room.schema.Schema;

@Entity(tableName = Schema.CATEGORY)
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public Category(String name) {
        this.name = name;
    }
}
