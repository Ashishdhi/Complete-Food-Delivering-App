package com.example.food_o_door.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insertNew(CartOffline cartOffline);

    @Query("SELECT * FROM CartOffline")
    List<CartOffline> getall();


    @Query("SELECT * FROM CartOffline WHERE priceUnitId=:priceUnitId")
    List<CartOffline> getCartProduct(String priceUnitId);

    @Query("UPDATE CartOffline SET quantity = :quantity where priceunitid=:priceunitid")
    void updateObj(long quantity, String priceunitid);


    @Query("DELETE From CartOffline Where  priceunitid = :priceunitid")
    void deleteObjbyPid(String priceunitid);



}
