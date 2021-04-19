package com.example.demo.models;

public class User {
    private String name;
    private int wishListID;

    public User(String name, int wishListID) {
        this.name = name;
        this.wishListID = wishListID;
    }

    public User(String name) {
        this.name = name;
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

    @Override
    public String toString(){
        return this.name + this.wishListID;
    }
}

