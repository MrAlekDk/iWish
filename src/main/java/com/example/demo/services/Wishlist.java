package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.Wish;

import java.util.ArrayList;
import java.util.HashMap;

public class Wishlist {
    private DatabaseRep dbRep;
    private HashMap<String, ArrayList<Wish>> wishlists;

    public Wishlist() {
        wishlists = new HashMap<>();
        dbRep = new DatabaseRep();
    }

    public ArrayList<Wish> getWishlist(String username,int ID) {
        if (wishlists.containsKey(username)) {
            return wishlists.get(username);
        } else {
            ArrayList<Wish> newWishlist = dbRep.getWishlist(ID);
            wishlists.put(username, newWishlist);
            return wishlists.get(username);
        }
    }

    public void addWish(String name, int price, String description,int ID) {

        dbRep.createWish(name, price, description, ID);
    }

    public void removeWish(int x,int ID) {
        this.dbRep.deleteWish(x, ID);
    }
/*
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
*/
    public User checkInformation(String username, String password) {
        return dbRep.checkUser(username, password);

    }

    public boolean createNewUser(String username, String password) {
        return dbRep.createUser(username, password);
    }

    public void shareWishlist(int ID,String sharedUser) {
        dbRep.shareWishlist(ID,sharedUser);

    }

    public ArrayList<String> getSharedwishlists(String username) {
        return dbRep.getSharedWishlists(username);
    }

    public ArrayList<Wish> getSharedWishlist(String username) {
        return dbRep.getSharedWishlist(username);
    }
}
