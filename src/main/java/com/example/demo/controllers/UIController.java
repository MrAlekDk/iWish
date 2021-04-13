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
    private HttpSession session;
    private Wishlist wishlist = new Wishlist();

    @GetMapping(value = "/login")
    public String renderLoginPage(){
        return "login.html";
    }

    @PostMapping(value = "checkLogin")
    public String checkLogin(@RequestParam("username") String username, @RequestParam("password") String password,
                             HttpServletRequest request) {
        session = request.getSession();
        System.out.println(username + password);
        boolean correctInformation = wishlist.checkInformation(username,password);
        if (correctInformation==true) {
            session.setAttribute("user-logged-in", "true");
            return "redirect:/userpage";
        } else {
            session.setAttribute("user-logged-in", "false");
            return "redirect:/login";
        }

    }

    @GetMapping(value = "userpage")
    public String renderUserpage(Model user) {
        if(session.getAttribute("user-logged-in").equals("true")){
            user.addAttribute("username",wishlist.getUsername());
            return "userpage.html";
        }
        else{
            return "redirect:/login.html";
        }
    }

    @GetMapping("/frontpage")
    public String frontpage(){
        return "frontpage.html";
    }
}
