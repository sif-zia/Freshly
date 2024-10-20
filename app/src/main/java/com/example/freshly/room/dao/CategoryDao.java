package com.example.freshly.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.freshly.room.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    long insert(Category category);

    @Update
    int update(Category category);

    @Delete
    int delete(Category category);

    @Query("SELECT * FROM Categories WHERE id = :id")
    Category getCategoryById(long id);

    @Query("SELECT * FROM Categories")
    List<Category> getAllCategories();
}
