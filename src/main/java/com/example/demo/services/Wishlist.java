package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.Wish;
import java.util.ArrayList;

public class Wishlist {
    private ArrayList<Wish> wishlist;
    private DatabaseRep dbRep;
    private User user;

    public Wishlist() {
        dbRep = new DatabaseRep();
    }

    public ArrayList<Wish> getWishlist() {
        this.wishlist = dbRep.getWishlist(user.getWishlistID());
        return this.wishlist;
    }

    public void addWish(String name, int price, String description) {

        dbRep.createWish(name,price,description,user.getWishlistID());
    }

    public void removeWish(int x) {
       this.dbRep.deleteWish(x,this.user.getWishlistID());
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
            this.user = dbRep.checkUser(username, password);
            if(user!=null){
                return true;
            }
            return false;
    }

    public boolean createNewUser(String username,String password){
        return dbRep.createUser(username,password);
    }

    public void shareWishlist(String sharedUser) {
        dbRep.shareWishlist(this.user.getWishlistID(), sharedUser);

    }

    public ArrayList<String> getSharedwishlists(){

        return dbRep.getSharedWishlists(this.user.getName());
    }

    public ArrayList<Wish> getSharedWishlist(String username) {

        return dbRep.getSharedWishlist(username);
    }
}
