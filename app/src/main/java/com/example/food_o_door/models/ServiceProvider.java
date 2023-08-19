package com.example.food_o_door.models;

public class ServiceProvider {
    private String address, category, image, name, phonenumber, place, providerid, rating;

    public ServiceProvider() {
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getPlace() {
        return place;
    }

    public String getProviderid() {
        return providerid;
    }

    public String getRating() {
        return rating;
    }

    public ServiceProvider(String address, String category, String image, String name, String phonenumber, String place, String providerid, String rating) {
        this.address = address;
        this.category = category;
        this.image = image;
        this.name = name;
        this.phonenumber = phonenumber;
        this.place = place;
        this.providerid = providerid;
        this.rating = rating;
    }
}
