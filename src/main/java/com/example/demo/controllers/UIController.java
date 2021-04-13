package com.example.demo.controllers;

import com.example.demo.services.Wishlist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UIController {
    private HttpServletRequest request;
    private Wishlist wishlist = new Wishlist();

    @GetMapping(value = "/login")
    public String renderLoginPage(){
        return "login.html";
    }

    @PostMapping(value = "checkLogin")
    public String checkLogin(@RequestParam() String username, String password) {
        HttpSession session = request.getSession();

        if (true) {
            return "redirect:/userpage";
            session.setAttribute("user-logged-in", "true");
        } else {
            session.setAttribute("user-logged-in", "false");
            return "redirect:/login";
        }

    }

    @GetMapping(value = "userpage")
    public String renderUserpage() {

        return "userpage.html";
    }
}
