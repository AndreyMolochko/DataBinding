package com.example.databinding.room.dao;

import com.example.databinding.room.entities.Dish;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DishDao {

    @Query("SELECT * FROM dishes")
    List<Dish> getAllElements();

    @Query("SELECT * FROM dishes WHERE  category =:category")
    List<Dish> getDishByCategory(String category);

    @Insert
    void insert(Dish dish);

    @Update
    void update(Dish dish);
}
