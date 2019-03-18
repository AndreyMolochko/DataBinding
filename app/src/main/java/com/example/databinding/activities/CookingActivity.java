package com.example.databinding.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.databinding.R;
import com.example.databinding.adapters.IngredientAdapter;
import com.example.databinding.callbacks.HandlerCookingDish;
import com.example.databinding.databinding.ActivityCookingBinding;
import com.example.databinding.model.Dish;
import com.example.databinding.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CookingActivity extends BaseActivity implements HandlerCookingDish {

    private ActivityCookingBinding binding;
    private Dish dish;
    private IngredientAdapter ingredientAdapter;
    private ArrayList<String> ingredientList;
    private com.example.databinding.room.entities.Dish dishRoom;

    public static void show(Context context, Dish dish) {
        Intent intent = new Intent(context, CookingActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_DISH, dish);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dish = (Dish) getIntent().getSerializableExtra(Constants.BUNDLE_KEY_DISH);
        ingredientList = dish.getIngredientList();
        initBinding();
    }

    @Override
    public void endCookingDish() {
        Snackbar.make(binding.getRoot(), getString(R.string.do_you_really_want_to_exit), Snackbar.LENGTH_LONG).setAction(
                getString(R.string.yes), v -> {
                    finishCooking();
                }).show();
    }

    @Override
    public void cancelCookingDish() {
        Snackbar.make(binding.getRoot(), getString(R.string.do_you_really_want_to_leave),
                      Snackbar.LENGTH_LONG).setAction(
                getString(R.string.yes), v -> {
                    onBackPressed();
                }).show();
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cooking);
        initRecycler();
        binding.setModel(dish);
        binding.setHandler(this);
    }

    private void initRecycler() {
        ingredientAdapter = new IngredientAdapter(ingredientList);
        binding.cookingDishRecycler.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));
        binding.cookingDishRecycler.setAdapter(ingredientAdapter);
    }

    private void initDishRoom() {
        dishRoom = new com.example.databinding.room.entities.Dish();
        dishRoom.convertDataBindingDish(dish);
    }

    private void updateCookingCounter(){
        dish.setCounterCooking(dish.getCounterCooking()+1);
    }

    private void finishCooking(){
        updateCookingCounter();
        initDishRoom();
        dishDao.update(dishRoom);
        onBackPressed();
    }

}
