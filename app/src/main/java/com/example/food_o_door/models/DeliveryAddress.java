package com.example.food_o_door.models;

public class DeliveryAddress {
    private String address;
    private String addressid;
    private String city;
    private String defaultis;
    private String name;
    private String phoneno;
    private String pincode;

    private String state;
    public DeliveryAddress() {}

    public String getDefaultis() {
        return defaultis;
    }

    public DeliveryAddress(String address, String addressid, String city, String defaultis, String name, String phoneno, String pincode, String state) {
        this.address = address;
        this.addressid = addressid;
        this.city = city;
        this.defaultis = defaultis;
        this.name = name;
        this.phoneno = phoneno;
        this.pincode = pincode;
        this.state = state;
    }

    public String getAddress() {
        return address;
    }
    public String getAddressid() {
        return addressid;
    }
    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getPincode() {
        return pincode;
    }

    public String getState() {
        return state;
    }
}
