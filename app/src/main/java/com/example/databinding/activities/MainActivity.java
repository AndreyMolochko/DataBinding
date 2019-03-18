package com.example.databinding.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.example.databinding.R;
import com.example.databinding.adapters.DishAdapter;
import com.example.databinding.databinding.ActivityMainBinding;
import com.example.databinding.enums.Categories;
import com.example.databinding.model.Dish;
import com.example.databinding.room.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public class MainActivity extends BaseActivity implements DishAdapter.OnClickListener {

    private ActivityMainBinding binding;
    private DishAdapter dishAdapter;

    @BindingAdapter({ "app:path" })
    public static void loadImage(ImageView view, String path) {
        if (path != null) {
            view.setImageURI(Uri.parse(path));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        initRecycler();
        initBottomNavigationBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                dishAdapter.getFilter()
                           .filter(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            AdditionDishActivity.show(MainActivity.this);
            return true;
        } else if (id == R.id.action_search) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickMore(View view, Dish dish) {
        showPopupMenu(view, dish);
    }

    @Override
    public void onClickCooking(Dish dish) {
        CookingActivity.show(MainActivity.this, dish);
    }

    private void initBottomNavigationBar() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            int id = menuItem.getItemId();

            switch (id) {
                case R.id.action_soup:
                    dishAdapter.setData(transformRoomToModel(
                            dishDao.getDishByCategory(Categories.SOUPS.getCategory())));
                    break;

                case R.id.action_main:
                    dishAdapter.setData(transformRoomToModel(
                            dishDao.getDishByCategory(Categories.MAIN.getCategory())));
                    break;

                case R.id.action_salads:
                    dishAdapter.setData(transformRoomToModel(
                            dishDao.getDishByCategory(Categories.SALADS.getCategory())));
                    break;

                case R.id.action_dessert:
                    dishAdapter.setData(transformRoomToModel(
                            dishDao.getDishByCategory(Categories.DESSERT.getCategory())));
                    break;

                case R.id.action_drinks:
                    dishAdapter.setData(transformRoomToModel(
                            dishDao.getDishByCategory(Categories.DRINKS.getCategory())));
                    break;
            }

            return true;
        });
    }

    private List<Dish> transformRoomToModel(
            List<com.example.databinding.room.entities.Dish> dishRoomList) {
        List<Dish> dishList = new ArrayList<>();

        for (int i = 0; i < dishRoomList.size(); i++) {
            Dish dish = new Dish();
            dish.convertDishRoom(dishRoomList.get(i));
            dishList.add(dish);
        }

        return dishList;
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    private void initRecycler() {
        dishAdapter = new DishAdapter(
                transformRoomToModel(dishDao.getDishByCategory(Categories.SOUPS.getCategory())),
                this);
        binding.activityMainDishesRecycler.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));
        binding.activityMainDishesRecycler.setAdapter(dishAdapter);
    }

    private void showPopupMenu(View view, Dish dish) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popup_menu_more);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_change_dish:
                    ChangingActivity.show(MainActivity.this, dish);

                    return true;
                default:

                    return false;
            }
        });
        popupMenu.show();
    }
}
