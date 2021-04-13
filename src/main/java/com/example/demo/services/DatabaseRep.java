package com.example.demo.services;

import com.example.demo.models.Wish;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseRep {

    public DatabaseRep(){

    }

    public ArrayList<Wish> getWishlist() {
        ArrayList<Wish> wishlist = new ArrayList<Wish>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employes_and_departments", "root", "hey");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM emp");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Wish tmp = new Wish(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getString(3)
                );
                wishlist.add(tmp);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return wishlist;
    }

    public boolean checkInformation(String username,String password) {

        return true;
    }
}
