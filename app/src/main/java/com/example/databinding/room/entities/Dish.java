package com.example.databinding.room.entities;

import com.example.databinding.room.converters.IngredientConverter;

import java.util.ArrayList;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "dishes")
public class Dish {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int counterCooking;
    private String category;
    @TypeConverters({ IngredientConverter.class })
    private ArrayList<String> ingredientList;
    private String pointCookingList;
    private String path;

    public Dish() {

    }

    public Dish(int id, String name, int counterCooking, String category,
                ArrayList<String> ingredientList, String pointCookingList, String path) {
        this.id = id;
        this.name = name;
        this.counterCooking = counterCooking;
        this.category = category;
        this.ingredientList = ingredientList;
        this.pointCookingList = pointCookingList;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCounterCooking() {
        return counterCooking;
    }

    public void setCounterCooking(int counterCooking) {
        this.counterCooking = counterCooking;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getPointCookingList() {
        return pointCookingList;
    }

    public void setPointCookingList(String pointCookingList) {
        this.pointCookingList = pointCookingList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void convertDataBindingDish(com.example.databinding.model.Dish dish) {
        id = dish.getId();
        name = dish.getName();
        category = dish.getCategory();
        counterCooking = dish.getCounterCooking();
        ingredientList = dish.getIngredientList();
        pointCookingList = dish.getPointCookingList();
        path = dish.getPath();
    }
}
