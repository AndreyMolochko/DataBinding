package com.example.databinding.activities;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.databinding.R;
import com.example.databinding.adapters.IngredientAdapter;
import com.example.databinding.callbacks.HandlerChangingDish;
import com.example.databinding.databinding.ActivityChangingDishBinding;
import com.example.databinding.model.Dish;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class TestChangingActivity extends BaseActivity implements HandlerChangingDish {

    private TextInputEditText nameEditText;
    private CircleImageView logoView;
    private TextInputEditText cookingListEditText;
    private RecyclerView recyclerView;
    private Button addEngridientButton;
    private Button deleteEngridientButton;
    private Button changeDishButton;

    private AlertDialog alertDialog;
    private TextInputLayout alertText;
    private AlertDialog alertDialogCamera;
    private Spinner spinner;
    private IngredientAdapter ingredientAdapter;
    private Dish dish;
    private ArrayList<String> ingredientList;
    private com.example.databinding.room.entities.Dish dishRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_changing_dish);

        initDish();
        nameEditText = findViewById(R.id.changing_dish_name_edit_text);
        logoView = findViewById(R.id.changing_dish_avatar);
        cookingListEditText = findViewById(R.id.addition_dish_alert_name_edit_text);
        recyclerView = findViewById(R.id.changing_dish_recycler);
        addEngridientButton = findViewById(R.id.changing_dish_add_ingredient_button);
        deleteEngridientButton = findViewById(R.id.changing_dish_delete_ingredient_button);
        changeDishButton = findViewById(R.id.changing_dish_add_dish_button);
        nameEditText.setText(dish.getName());
        Glide.with(getApplicationContext())
                .load(dish.getPath())
                .into(logoView);
        cookingListEditText.setText(dish.getPointCookingList());
        addEngridientButton.setOnClickListener(v -> {

        });
        deleteEngridientButton.setOnClickListener(v -> {

        });
        changeDishButton.setOnClickListener(v -> {

        });

        initRecycler();
        initSpinner();
    }

    private void initDish() {
        dish = new Dish();
    }

    private void initSpinner() {

    }

    private void initRecycler() {

    }

    @Override
    public void onClickAddIngredientButton() {

    }

    @Override
    public void onClickDeleteIngredientButton() {

    }

    @Override
    public void onClickChangingDishButton() {

    }

    @Override
    public void onClickAvatar() {

    }
}
