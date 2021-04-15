package com.example.demo.services;

import com.example.demo.models.Wish;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseRep {

    public DatabaseRep() {

    }

    public ArrayList<Wish> getWishlist() {
        ArrayList<Wish> wishlist = new ArrayList<Wish>();
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://18.196.36.77:3306/iWish", "remote", "1234");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM wishlist");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Wish tmp = new Wish(
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4)
                );
                System.out.println(tmp.toString());
                wishlist.add(tmp) ;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return wishlist;
    }

    public boolean checkInformation(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employes_and_departments", "root", "hey");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM emp");
            ResultSet rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return true;
    }
}
