package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.Wish;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseRep {

    public DatabaseRep() {
    }
    public ArrayList<Wish> getWishlist(int wishlistID) {
        ArrayList<Wish> wishlist = new ArrayList<Wish>();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Wishlist WHERE Wishlist_ID=?");
            stmt.setInt(1, wishlistID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Wish tmp = new Wish(
                        rs.getInt("ID"),
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
                if (rs.getString(2).equals(username)) {
                    if (rs.getString(3).equals(password)) {
                        User user = new User(
                                rs.getString(2),
                                rs.getInt(3));
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

    public void createWish(String productname, int price, String description ,int wishlist_ID) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Wishlist (Wishlist_ID,Product_name,Price,Narrative,Reserved) VALUES (?,?,?,?,?)");

            pstmt.setInt(1, wishlist_ID);
            pstmt.setString(2, productname);
            pstmt.setInt(3, price);
            pstmt.setString(4, description);
            pstmt.setBoolean(5, false);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProductName(String newProductName, String productName, int wishlist_ID) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");
            PreparedStatement pstmt = conn.prepareStatement("UPDATE Wishlist SET Product_name = ? WHERE Wishlist_ID =" + wishlist_ID + " AND Product_name ='" + productName + "'");
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

            pstm.setString(1, productName);
            pstm.setInt(2, wishlist_ID);
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
    }

    public boolean createUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.139.62.205:3306/iWish", "iWish", "1234");
            PreparedStatement stmt = conn.prepareStatement("Select Name From Member");
            ResultSet rs1 = stmt.executeQuery();
            while (rs1.next()) {
                if (rs1.getString(1) == username) {
                    return false;
                } else if (rs1.getString(1) != username) {
                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO Member (name,password) VALUES (?,?)");
                    stmt3.setString(1, username);
                    stmt3.setString(2, password);
                    stmt3.executeUpdate();
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {

        DatabaseRep test = new DatabaseRep();

        test.updateProductName("glas", "hals", 2);

    }
}