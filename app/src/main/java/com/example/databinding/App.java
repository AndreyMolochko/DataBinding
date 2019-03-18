package com.example.databinding;

import android.app.Application;

import com.example.databinding.room.database.AppDatabase;
import com.facebook.stetho.Stetho;

import androidx.room.Room;

public class App extends Application {

    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                       .allowMainThreadQueries()
                       .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
