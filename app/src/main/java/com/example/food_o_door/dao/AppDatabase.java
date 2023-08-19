package com.example.food_o_door.dao;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CartOffline.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
}
