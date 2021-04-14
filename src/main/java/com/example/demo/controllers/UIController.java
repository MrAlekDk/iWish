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
        if(session.getAttribute("logged-in")==null){
            session.setAttribute("logged-in",false);
        }
        else if ((boolean)session.getAttribute("logged-in")) {
            return "redirect:/userpage";
        }

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

            user.addAttribute("username", wishlist.getUsername());
            user.addAttribute("wishlist", wishlist.getWishlist());

        return "userpage.html";
    }

    @GetMapping("/logging-out")
    public String logout(){
        session.setAttribute("logged-in",false);
        return "redirect:/frontpage";
    }
}
