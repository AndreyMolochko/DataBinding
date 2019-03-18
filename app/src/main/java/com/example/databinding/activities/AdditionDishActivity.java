package com.example.databinding.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.databinding.R;
import com.example.databinding.adapters.IngredientAdapter;
import com.example.databinding.callbacks.HandlerAdditionDish;
import com.example.databinding.databinding.ActivityAdditionDishBinding;
import com.example.databinding.util.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AdditionDishActivity extends BaseActivity implements HandlerAdditionDish {

    private ActivityAdditionDishBinding bindingDishActivity;
    private IngredientAdapter ingredientAdapter;

    private AlertDialog alertDialog;
    private AlertDialog alertDialogCamera;
    private TextInputLayout alertText;

    private ArrayList<String> ingredientList;

    private com.example.databinding.model.Dish dish;
    private com.example.databinding.room.entities.Dish dishRoom;
    final String TAG = "myLogs";

    public static void show(Context context) {
        Intent intent = new Intent(context, AdditionDishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ingredientList = new ArrayList<>();
        initBindingDishActivity();
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
    public void onClickAddDishButton() {
        initDishRoom();
        dishDao.insert(dishRoom);
        Snackbar.make(bindingDishActivity.getRoot()
                                         .getRootView(), getString(R.string.dish_was_added),
                      Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onClickAvatar() {
        showAlertForPhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            bindingDishActivity.additionDishAvatar.setImageBitmap(bitmap);

        } else if (requestCode == Constants.GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            bindingDishActivity.additionDishAvatar.setImageURI(selectedImage);
            dish.setPath(String.valueOf(selectedImage));
        }
        alertDialogCamera.dismiss();
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
        bindingDishActivity = DataBindingUtil.setContentView(this, R.layout.activity_addition_dish);
        initRecycler();
        dish = new com.example.databinding.model.Dish();
        bindingDishActivity.setModel(dish);
        bindingDishActivity.setHandler(this);
    }

    private void initBindingAlert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdditionDishActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.addition_ingredient, null);
        alertBuilder.setView(alertView);
        alertDialog = alertBuilder.create();
        alertDialog.show();
        alertText = alertDialog.findViewById(R.id.addition_dish_alert_name_edit_text);
    }

    private void initRecycler() {
        ingredientAdapter = new IngredientAdapter(ingredientList);
        bindingDishActivity.additionDishRecycler.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));
        bindingDishActivity.additionDishRecycler.setAdapter(ingredientAdapter);
    }

    private void initDishRoom() {
        dish.setIngredientList(ingredientList);
        dish.setCategory(bindingDishActivity.additionDishCategorySpinner.getSelectedItem()
                                                                        .toString());
        dishRoom = new com.example.databinding.room.entities.Dish();
        dishRoom.convertDataBindingDish(dish);
    }

    private void deleteLastIngredient() {
        if (ingredientList.size() > 0) {
            ingredientList.remove(ingredientList.size() - 1);
            ingredientAdapter.setData(ingredientList);
        }
    }

    private void showAlertForPhoto() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdditionDishActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.alert_making_photo, null);
        alertBuilder.setView(alertView);
        alertDialogCamera = alertBuilder.create();
        alertDialogCamera.show();
    }
}
