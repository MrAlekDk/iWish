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
        return "login.html";
    }

    @PostMapping(value = "/check-login")
    public String checkLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (wishlist.checkInformation(username, password)) {
            session.setAttribute("logged-in",true);
            return "redirect:/userpage";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/userpage")
    public String renderUserpage(Model user,HttpServletRequest request) {
        boolean loggedIn = (boolean) session.getAttribute("logged-in");
        if (!loggedIn) {
            return "redirect:/login";
        }

        user.addAttribute("username", wishlist.getUsername());
        user.addAttribute("wishlist", wishlist.getWishlist());
        return "userpage.html";
    }

    @GetMapping("/frontpage")
    public String frontpage() {
        return "frontpage.html";
    }
}
