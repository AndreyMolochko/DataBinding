package com.example.databinding.room.database;

import com.example.databinding.room.dao.DishDao;
import com.example.databinding.room.entities.Dish;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Dish.class,version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DishDao dishDao();
}
