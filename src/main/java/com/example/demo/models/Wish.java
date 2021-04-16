package com.example.demo.models;

public class Wish {

    private int wishID;
    private String name;
    private int price;
    private String description;
    private boolean reserved;

    public Wish(int wishID, String name, int price, String description){
        this.wishID=wishID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.reserved = false;
    }
    public Wish(){};

    /*----------------------- Getters -----------------------*/

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReserved() {
        return reserved;
    }

    public boolean getReserved(){
        return this.reserved;
    }

    public int getWishID(){
        return this.wishID;
    }

    /*----------------------- Setters -----------------------*/

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }


}
