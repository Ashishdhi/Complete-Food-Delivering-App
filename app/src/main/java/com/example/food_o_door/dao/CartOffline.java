package com.example.food_o_door.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartOffline {

    @PrimaryKey(autoGenerate = true)
    int id;

    String pid;
    long quantity;
    String price;
    String name;
    String imageUrl;
    String priceUnit;
    String priceUnitName;
    String priceUnitId;


    public CartOffline(long quantity, String price, String name, String imageUrl, String priceUnit, String priceUnitName, String priceUnitId) {
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.priceUnit = priceUnit;
        this.priceUnitName = priceUnitName;
        this.priceUnitId = priceUnitId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getPriceUnitName() {
        return priceUnitName;
    }

    public void setPriceUnitName(String priceUnitName) {
        this.priceUnitName = priceUnitName;
    }

    public String getPriceUnitId() {
        return priceUnitId;
    }

    public void setPriceUnitId(String priceUnitId) {
        this.priceUnitId = priceUnitId;
    }
}
