package com.example.demo.services;


import com.example.demo.models.User;
import com.example.demo.models.Wish;
import com.mysql.cj.util.TestUtils;

import javax.naming.Name;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseRep {

    public DatabaseRep() {

    }

    public ArrayList<Wish> getWishlist(int wishlistID) {
        ArrayList<Wish> wishlist = new ArrayList<Wish>();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM wishList WHERE wishlist_ID=?");
            stmt.setInt(1, wishlistID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Wish tmp = new Wish(
                        rs.getString("Product_name"),
                        rs.getInt("Price"),
                        rs.getString("Narrative")
                );
                wishlist.add(tmp);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return wishlist;
    }

    public User checkUser(String username, String password) {

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Member");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString(1).equals(username)) {
                    if (rs.getString(3).equals(password)) {
                        User user = new User(
                                rs.getString(1),
                                rs.getInt(3));

                        System.out.println(rs.getString(1));

                        return user;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());

        }
        return null;
    }

    public void createWish(String productName, int price, String narrative) {

        try {
            boolean res = false;
            int ID = 2;
            int Wishlist_ID = 1;
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Wishlist (ID, Wishlist_ID,Product_name,Price,Narrative,Reserved) VALUES (?,?,?,?,?,?)");
            pstmt.setInt(1, ID);
            pstmt.setInt(2, Wishlist_ID);
            pstmt.setString(3, productName);
            pstmt.setInt(4, price);
            pstmt.setString(5, narrative);
            pstmt.setBoolean(6, res);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProductName(String newProductName, String productName, int wishlist_ID) {

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");

            PreparedStatement pstmt = conn.prepareStatement("UPDATE Wishlist SET Product_name = ? WHERE Wishlist_ID =" + wishlist_ID + " AND Product_name ='" + productName +"'");
            pstmt.setString(1, newProductName);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteWish(String productName, int wishlist_ID) { //Men hvordan får vi fat i wishlist id uden at skrive det i parameter??

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM Wishlist WHERE Product_name = ? AND Wishlist_ID = ?");

            pstm.setString(1,productName);
            pstm.setInt(2,wishlist_ID);
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
    }

    public void createUser(String username, String password){

        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Member (name,wishlist_ID,password) VALUES ('Tove',4,345);")

        }


    }

    public static void main(String[] args) {

        DatabaseRep test = new DatabaseRep();

        test.updateProductName("glas","hals",2);

    }
}