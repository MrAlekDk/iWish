package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.Wishlist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class IwishController {
    private Wishlist wishlist = new Wishlist();


    @GetMapping("/frontpage")
    public String frontpage() {
        return "frontpage.html";
    }


    @GetMapping(value = "/login")
    public String renderLoginPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!session.isNew() && session.getAttribute("username") != null) {
            return "redirect:/userpage";
        }
        return "login.html";
    }

    @PostMapping(value = "/check-login")
    public String checkLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        User tmpUser = wishlist.checkInformation(username, password);
        HttpSession session = request.getSession();
        if (tmpUser!=null) {
            session = request.getSession();
            session.setAttribute("username", tmpUser.getName());
            session.setAttribute("ID", tmpUser.getWishlistID());
            return "redirect:/userpage";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/userpage")
    public String renderUserpage(Model user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.isNew()) {
            return "redirect:/login";
        }
        user.addAttribute("username", session.getAttribute("username"));
        user.addAttribute("wishlist", wishlist.getWishlist((String) session.getAttribute("username"),(Integer)session.getAttribute("ID")));

        return "userpage.html";
    }

    @GetMapping("/logging-out")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            return "redirect:/frontpage";
        }
        session.invalidate();
        session=null;
        return "redirect:/frontpage";
    }


    @GetMapping(value = "/createWish")
    public String renderWishCreatorPage(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("username") ==null){
            return "redirect:/login";
        }
        return "wishcreator.html";
    }

    @PostMapping(value = "addNewWish")
    public String addNewWish(@RequestParam("wishName") String wishName, @RequestParam("price") int price, @RequestParam("description") String desc,HttpServletRequest request) {
        HttpSession session = request.getSession();
        wishlist.addWish((String)session.getAttribute("username"),wishName, price, desc,(Integer)session.getAttribute("ID"));
        return "redirect:/userpage";
    }

    @GetMapping(value="/create-user")
    public String createNewUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        session = request.getSession();
        if (session.getAttribute("username")!=null) {
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
    public String shareWishlist(@RequestParam("username") String sharedUser,HttpServletRequest request) {
        HttpSession session = request.getSession();
        wishlist.shareWishlist((Integer) session.getAttribute("ID"),sharedUser);
        return "redirect:/userpage";
    }

    @GetMapping(value = "/shared-wishlists")
    public String renderSharedWishlists(Model user,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.isNew()) {
            return "redirect:/login";
        }
        user.addAttribute("username", session.getAttribute("username"));
        user.addAttribute("wishlists", wishlist.getSharedwishlists((String)session.getAttribute("username")));

        return "sharedWishlists.html";
    }

    @PostMapping(value = "/delete-wish")
    public String deleteWish(@RequestParam("wish-id") int wishID,HttpServletRequest request) {
        HttpSession session = request.getSession();
        wishlist.removeWish((String)session.getAttribute("username"),wishID,(Integer)session.getAttribute("ID"));
        return "redirect:/userpage";
    }

    @GetMapping(value = "/selected-wishlist")
    public String getSharedWishlist(@RequestParam("selected-wishlist") String username,Model wish) {

        wish.addAttribute("username",username);
        wish.addAttribute("wishlist", wishlist.getSharedWishlist(username));
        return "sharedWishlist.html";
    }

    @PostMapping(value = "/reserve-wish")
    public String reserveWish(@RequestParam("wish-id") int wishID) {
        wishlist.reserveWish(wishID);
        return "redirect:/shared-wishlists";
    }
}
