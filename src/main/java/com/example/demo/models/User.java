package com.example.demo.models;

public class User {
    private String name;
    private int wishListID;

    public User(String navn, int wishListID) {
        this.name = navn;
        this.wishListID = wishListID;
    }

    public User(String navn) {
        this.name = navn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getWishlistID() {
        return this.wishListID;
    }
}

