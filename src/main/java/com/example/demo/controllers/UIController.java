package com.example.demo.controllers;

import com.example.demo.services.Wishlist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class UIController {
    private Wishlist wishlist = new Wishlist();
    private HttpSession session;

    @GetMapping(value = "/login")
    public String renderLoginPage(HttpServletRequest request) {
        session = request.getSession();
        if (!session.isNew() && session.getAttribute("username") != null) {
            return "redirect:/userpage";
        }
        return "login.html";
    }

    @PostMapping(value = "/check-login")
    public String checkLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {

        if (wishlist.checkInformation(username, password)) {
            session = request.getSession();
            session.setAttribute("username", username);
            return "redirect:/userpage";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/userpage")
    public String renderUserpage(Model user, HttpServletRequest request) {
        if (session.isNew()) {
            return "redirect:/login";
        }
        user.addAttribute("username", wishlist.getUsername());
        user.addAttribute("wishlist", wishlist.getWishlist());

        return "userpage.html";
    }

    @GetMapping("/logging-out")
    public String logout() {
        if (session == null) {
            return "redirect:/frontpage";
        }
        session.invalidate();
        session=null;
        return "redirect:/frontpage";
    }

    @PostMapping(value = "addNewWish")
    public String addNewWish(@RequestParam("wishName") String wishName, @RequestParam("price") int price, @RequestParam("description") String desc) {
        wishlist.addWish(wishName, price, desc);
        return "redirect:/userpage";
    }

    @GetMapping(value="/create-user")
    public String createNewUser(){
        if (session!= null) {
            return "redirect:/frontpage";
        }
        return "createUser.html";
    }

    @PostMapping(value = "upload-user")
    public String uploadUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        boolean userCreated = wishlist.createNewUser(username, password);
        if (userCreated == true) {
            return "redirect:/login";
        } else {
            return "redirect:/create-user";
        }
    }

    @PostMapping(value = "/share-wishlist")
    public String shareWishlist(@RequestParam("username") String sharedUser) {
        wishlist.shareWishlist(sharedUser);
        return "redirect:/userpage";
    }

    @GetMapping(value = "/shared-wishlists")
    public String renderSharedWishlists(Model user) {
        if (session.isNew()) {
            return "redirect:/login";
        }
        user.addAttribute("username", wishlist.getUsername());
        user.addAttribute("wishlists", wishlist.getSharedwishlists());

        return "sharedWishlists.html";
    }

    @PostMapping(value = "/delete-wish")
    public String deleteWish(@RequestParam("wish-id") int wishID) {
        wishlist.removeWish(wishID);
        System.out.println(wishID);
        return "redirect:/userpage";
    }

    @GetMapping(value = "/selected-wishlist")
    public String getSharedWishlist(@RequestParam("selected-wishlist") String username,Model wish) {

        wish.addAttribute("username",username);
        wish.addAttribute("wishlist", wishlist.getSharedWishlist(username));
        System.out.println(username);
        return "sharedWishlist.html";
    }


}
