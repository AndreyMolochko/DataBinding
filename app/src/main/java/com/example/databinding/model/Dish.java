package com.example.databinding.model;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Dish extends BaseObservable implements Serializable {

    private int id;
    private String name;
    private int counterCooking;
    private String category;
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

    //@Bindable
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

    //@Bindable
    public ArrayList<String> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Bindable
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

    public void convertDishRoom(com.example.databinding.room.entities.Dish dish){
        id = dish.getId();
        name = dish.getName();
        category = dish.getCategory();
        counterCooking = dish.getCounterCooking();
        ingredientList = dish.getIngredientList();
        pointCookingList = dish.getPointCookingList();
        path = dish.getPath();
    }
}
