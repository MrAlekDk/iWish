package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IWishController {

    @GetMapping(value = "/createWish")
    public String renderWishCreatorPage(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("username") ==null){
            return "redirect:/login";
        }
        return "wishcreator.html";
    }


    @GetMapping("/frontpage")
    public String frontpage() {
        return "frontpage.html";
    }

    @GetMapping(value="/create-user")
    public String createNewUser(){

        return "createUser.html";
    }


}
