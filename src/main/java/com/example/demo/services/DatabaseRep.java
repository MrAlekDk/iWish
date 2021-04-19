package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.Wish;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseRep {

    private String url;
    private String user;
    private String password;

    public DatabaseRep() {
        Properties prop = new Properties();
        try{
            prop.load(new FileInputStream("src/main/resources/application.properties"));
            user = prop.getProperty("user");
            password = prop.getProperty("password");
            url = prop.getProperty("url");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Wish> getWishlist(int wishlistID) {
        ArrayList<Wish> wishlist = new ArrayList<Wish>();

        try {
            Connection conn = DriverManager.getConnection(url, user,password);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Wishlist WHERE Wishlist_ID=?");
            stmt.setInt(1, wishlistID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Wish tmp = new Wish(
                        rs.getInt("ID"),
                        rs.getString("Product_name"),
                        rs.getInt("Price"),
                        rs.getString("Narrative"),
                        rs.getBoolean("Reserved")
                );
                wishlist.add(tmp);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return wishlist;
    }

    public User checkUser(String username, String password1) {

        try {
            Connection conn = DriverManager.getConnection(url,user, this.password);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Member");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString(2).equals(username)) {
                    if (rs.getString(3).equals(password1)) {
                        User user = new User(
                                rs.getString(2),
                                rs.getInt(1));
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

    public void createWish(String productName, int price, String description, int wishlist_ID) {
        try {
            Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Wishlist (Wishlist_ID,Product_name,Price,Narrative,Reserved) VALUES (?,?,?,?,?)");

            pstmt.setInt(1, wishlist_ID);
            pstmt.setString(2, productName);
            pstmt.setInt(3, price);
            pstmt.setString(4, description);
            pstmt.setBoolean(5, false);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateWish(int wishID, String newProductName, int newPrice, String newNarrative) {
        try {
            Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Wishlist");
            ResultSet st = pstmt.executeQuery();

            while (st.next()) {
                if (st.getInt("ID") == wishID) {
                    PreparedStatement pstmt1 = conn.prepareStatement("UPDATE Wishlist SET Product_name = ?, Price = ?, Narrative = ? WHERE ID =" + wishID);
                    pstmt1.setString(1, newProductName);
                    pstmt1.setInt(2, newPrice);
                    pstmt1.setString(3, newNarrative);
                    pstmt1.executeUpdate();

                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void reserveWish(int wishID){

        try{
            Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Wishlist");
            ResultSet st = stmt.executeQuery();

            while (st.next()) {
                if (st.getInt("Reserved") == 0) {
                    PreparedStatement stmt1 = conn.prepareStatement("UPDATE Wishlist SET Reserved = 1 WHERE ID =" + wishID);
                    stmt1.executeUpdate();
                } else {
                    PreparedStatement stmt2 = conn.prepareStatement("UPDATE Wishlist SET Reserved = 0 WHERE ID =" + wishID);
                    stmt2.executeUpdate();
                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteWish(int productID, int wishlist_ID) {

        try {
            Connection conn = DriverManager.getConnection(url , user, password);
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM Wishlist WHERE ID = ? AND Wishlist_ID = ?");

            pstm.setInt(1, productID);
            pstm.setInt(2, wishlist_ID);
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
    }

    public boolean createUser(String username, String password1) {
        try {
            Connection conn = DriverManager.getConnection(url ,user, this.password);
            PreparedStatement stmt = conn.prepareStatement("Select Name From Member");
            ResultSet rs1 = stmt.executeQuery();
            while (rs1.next()) {
                if (rs1.getString(1) == username) {
                    return false;
                } else if (rs1.getString(1) != username) {
                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO Member (Name,Password) VALUES (?,?)");
                    stmt3.setString(1, username);
                    stmt3.setString(2, password1);
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

    public void shareWishlist(int currentUserWishlistID, String sharedUser) {
        try {
            Connection conn = DriverManager.getConnection(url ,user,password);

            PreparedStatement stmt2 = conn.prepareStatement("Insert into Shared_wishlists (Username,wishlist_ID) VALUES (?,?)");
            stmt2.setString(1, sharedUser);
            stmt2.setInt(2, currentUserWishlistID);
            System.out.println("Shared the wishlist");
            stmt2.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<String> getSharedWishlists(String username) {

        ArrayList<String> names = new ArrayList<String>();
        try {
            Connection conn = DriverManager.getConnection(url , user, password);


            PreparedStatement stmt = conn.prepareStatement("SELECT Shared_wishlists.Username,Member.NAME " +
                    "from Member " +
                    "INNER JOIN Shared_wishlists on Member.Wishlist_ID=Shared_wishlists.wishlist_ID");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equals(username)) {
                    names.add(rs.getString(2));
                }
            }
            return names;

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }

        return names;
    }

    public ArrayList<Wish> getSharedWishlist(String username) {
        ArrayList<Wish> wishlist = new ArrayList<Wish>();
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT Wishlist.*, Member.Name\n" +
                    "from Wishlist \n" +
                    "Inner JOIN Member on Wishlist.Wishlist_ID=Member.Wishlist_ID;");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString(7).equals(username)) {
                    Wish tmp = new Wish(
                            rs.getInt("ID"),
                            rs.getString("Product_name"),
                            rs.getInt("Price"),
                            rs.getString("Narrative"),
                            rs.getBoolean("Reserved")
                    );
                    wishlist.add(tmp);
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return wishlist;
    }

    public static void main(String[] args) {

        DatabaseRep test = new DatabaseRep();

        test.reserveWish(18);

    }



}