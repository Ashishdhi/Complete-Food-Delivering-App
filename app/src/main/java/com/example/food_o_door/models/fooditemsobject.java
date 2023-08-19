package com.example.food_o_door.models;

public class fooditemsobject {

    private String category,description,fulldescription,image,name,pack,price,productID,quantity ;

    public fooditemsobject(String category, String description,String fulldescription, String image, String name,String pack, String price, String productID, String quantity) {
        this.category = category;
        this.description = description;
        this.fulldescription = fulldescription;
        this.image = image;
        this.name = name;
        this.pack = pack;
        this.price = price;
        this.productID = productID;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getPack() {
        return pack;
    }

    public String getDescription() {
        return description;
    }public String getFulldescription() {
        return fulldescription;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getProductID() {
        return productID;
    }

    public String getQuantity() {
        return quantity;
    }

    public fooditemsobject() {}
}
