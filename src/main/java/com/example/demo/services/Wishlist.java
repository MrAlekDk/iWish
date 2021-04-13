package com.example.demo.services;

import com.example.demo.models.Wish;

import java.util.ArrayList;

public class Wishlist {
    private ArrayList<Wish> wishArrayList;
    private Wish wish;

    public Wishlist(ArrayList<Wish> wishArrayList){
        this.wishArrayList = wishArrayList;
    }

    public void addWish(Wish wish){
        wishArrayList.add(wish);
    }

    public void removeWish(int x){
        wishArrayList.remove(x--);
    }

    public void editWish(int x, String name, int price, String description){
        for (int i = 0; i < wishArrayList.size(); i++) {
            if(wishArrayList.get(i).equals(x)){
                wishArrayList.get(i).setName(name);
                wishArrayList.get(i).setPrice(price);
                wishArrayList.get(i).setDescription(description);
            }
        }
    }

    public void reservWish(int x){
        for (int i = 0; i < wishArrayList.size(); i++) {
            if(wishArrayList.get(i).equals(x)){
                wishArrayList.get(i).setReserved(true);
            }
        }
    }
}
