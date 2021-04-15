package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.Wish;

import java.util.ArrayList;

public class Wishlist {
    private ArrayList<Wish> wishlist;
    private DatabaseRep dbRep;
    private Wish wish;
    private User user;

    public Wishlist() {
        dbRep = new DatabaseRep();

    }

    public ArrayList<Wish> getWishlist(){
        this.wishlist = dbRep.getWishlist();
        return this.wishlist;
    }

    public void addWish(String name,int price, String description) {
        Wish newWish = new Wish(name,price,description);
        wishlist.add(newWish);
    }

    public void removeWish(int x) {
        wishlist.remove(x--);
    }

    public void editWish(int x, String name, int price, String description) {
        for (int i = 0; i < wishlist.size(); i++) {
            if (wishlist.get(i).equals(x)) {
                wishlist.get(i).setName(name);
                wishlist.get(i).setPrice(price);
                wishlist.get(i).setDescription(description);
            }
        }
    }

    public void reservWish(int x) {
        for (int i = 0; i < wishlist.size(); i++) {
            if (wishlist.get(i).equals(x)) {
                wishlist.get(i).setReserved(true);
            }
        }
    }

    public String getUsername() {
        return user.getName();
    }

    public boolean checkInformation(String username, String password) {
        //if (dbRep.checkInformation(username, password) == true) {
          this.user = new User(username);

            return true;
       // }
       // else{
          // return false;
       // }
    }
}
