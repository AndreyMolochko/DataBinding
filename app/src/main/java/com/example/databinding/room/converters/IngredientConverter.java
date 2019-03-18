package com.example.databinding.room.converters;

import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

public class IngredientConverter {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public String fromIngredients(ArrayList<String> dishes) {
        return dishes.stream()
                     .collect(Collectors.joining(","));
    }

    @TypeConverter
    public ArrayList<String> toIngredients(String data) {
        List<String>arrayList = new ArrayList<>();
        Collections.addAll(arrayList,data.split(","));
        return (ArrayList<String>) arrayList;
    }
}
