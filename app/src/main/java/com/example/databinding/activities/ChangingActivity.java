package com.example.databinding.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.example.databinding.R;
import com.example.databinding.adapters.IngredientAdapter;
import com.example.databinding.callbacks.HandlerChangingDish;
import com.example.databinding.databinding.ActivityChangingDishBinding;
import com.example.databinding.enums.Categories;
import com.example.databinding.model.Dish;
import com.example.databinding.util.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ChangingActivity extends BaseActivity implements HandlerChangingDish {

    private AlertDialog alertDialog;
    private TextInputLayout alertText;
    private AlertDialog alertDialogCamera;
    private Spinner spinner;
    private ActivityChangingDishBinding bindingDishActivity;
    private IngredientAdapter ingredientAdapter;
    private Dish dish;
    private ArrayList<String> ingredientList;
    private com.example.databinding.room.entities.Dish dishRoom;

    public static void show(Context context, Dish dish) {
        Intent intent = new Intent(context, ChangingActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_DISH, dish);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dish = (Dish) getIntent().getSerializableExtra(Constants.BUNDLE_KEY_DISH);
        ingredientList = dish.getIngredientList();
        initBindingDishActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.CAMERA_REQUEST_CODE) {
            Uri selectedImage = data.getData();
            bindingDishActivity.changingDishAvatar.setImageURI(selectedImage);
            dish.setPath(String.valueOf(selectedImage));
        } else if (requestCode == Constants.GALLERY_REQUEST_CODE) {
            Uri selectedImage = data.getData();
            bindingDishActivity.changingDishAvatar.setImageURI(selectedImage);
            dish.setPath(String.valueOf(selectedImage));
        }
        alertDialogCamera.dismiss();
    }

    @Override
    public void onClickAddIngredientButton() {
        initBindingAlert();
    }

    @Override
    public void onClickDeleteIngredientButton() {
        deleteLastIngredient();
    }

    @Override
    public void onClickChangingDishButton() {
        initDishRoom();
        dishDao.update(dishRoom);
        Snackbar.make(bindingDishActivity.getRoot()
                                         .getRootView(), getString(R.string.dish_was_changed),
                      Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onClickAvatar() {
        showAlertForPhoto();
    }

    public void onClickAlertAddButton(View view) {
        ingredientList.add(alertText.getEditText()
                                    .getText()
                                    .toString());
        ingredientAdapter.setData(ingredientList);
        alertDialog.cancel();
    }

    public void onClickAlertCancelButton(View view) {
        alertDialog.cancel();
    }

    public void onClickAlertCameraView(View view) {
        openCamera();
    }

    public void onClickAlertGalleryView(View view) {
        openGallery();
    }

    private void initBindingDishActivity() {
        bindingDishActivity = DataBindingUtil.setContentView(this, R.layout.activity_changing_dish);
        initRecycler();
        initSpinner();
        bindingDishActivity.setModel(dish);
        bindingDishActivity.setHandler(this);
    }

    private void initSpinner() {
        spinner = bindingDishActivity.changingDishCategorySpinner;

        for (Categories category : Categories.values()) {

            if (category.getCategory()
                        .equals(dish.getCategory())) {
                spinner.setSelection(category.getId());

                return;
            }
        }

        spinner.setSelection(Constants.DEFAULT_POSITION_SPINNER);
    }

    private void initRecycler() {
        ingredientAdapter = new IngredientAdapter(ingredientList);
        bindingDishActivity.changingDishRecycler.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));
        bindingDishActivity.changingDishRecycler.setAdapter(ingredientAdapter);
    }

    private void initBindingAlert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChangingActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.addition_ingredient, null);
        alertBuilder.setView(alertView);
        alertDialog = alertBuilder.create();
        alertDialog.show();
        alertText = alertDialog.findViewById(R.id.addition_dish_alert_name_edit_text);
    }

    private void deleteLastIngredient() {
        if (ingredientList.size() > 0) {
            ingredientList.remove(ingredientList.size() - 1);
            ingredientAdapter.setData(ingredientList);
        }
    }

    private void initDishRoom() {
        dish.setIngredientList(ingredientList);
        dish.setCategory(bindingDishActivity.changingDishCategorySpinner.getSelectedItem()
                                                                        .toString());
        dishRoom = new com.example.databinding.room.entities.Dish();
        dishRoom.convertDataBindingDish(dish);
    }

    private void showAlertForPhoto() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChangingActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.alert_making_photo, null);
        alertBuilder.setView(alertView);
        alertDialogCamera = alertBuilder.create();
        alertDialogCamera.show();
    }
}
