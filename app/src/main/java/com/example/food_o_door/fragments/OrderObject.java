package com.example.food_o_door.fragments;

public class OrderObject {

    private  String name,orderid,price,items,orderaddress,phoneno;
    public OrderObject() {}

    public String getName() {
        return name;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getPrice() {
        return price;
    }

    public String getItems() {
        return items;
    }

    public String getOrderaddress() {
        return orderaddress;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public OrderObject(String name, String orderid, String price, String items, String orderaddress, String phoneno) {
        this.name = name;
        this.orderid = orderid;
        this.price = price;
        this.items = items;
        this.orderaddress = orderaddress;
        this.phoneno = phoneno;
    }
}
