package com.example.food_o_door.models;

public class BannerModel {
    private  String image,productid;
    public BannerModel() {}

    public String getImage() {
        return image;
    }

    public String getProductid() {
        return productid;
    }

    public BannerModel(String image, String productid) {
        this.image = image;
        this.productid = productid;
    }
}
