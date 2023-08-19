package com.example.food_o_door.models;

public class CategoryModel {
    private  String image,name;

    public  CategoryModel(){
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public CategoryModel(String image, String name) {
        this.image = image;
        this.name = name;
    }
}
