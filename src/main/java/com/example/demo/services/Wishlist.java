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

    public void addWish(String username, String name, int price, String description,int ID) {
        dbRep.createWish(name, price, description, ID);
        wishlists.put(username,dbRep.getWishlist(ID));
    }

    public void removeWish(String username, int x,int ID) {
        this.dbRep.deleteWish(x, ID);
        wishlists.put(username,dbRep.getWishlist(ID));
    }

    public void reserveWish(int wishID) {
        dbRep.reserveWish(wishID);
    }

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
