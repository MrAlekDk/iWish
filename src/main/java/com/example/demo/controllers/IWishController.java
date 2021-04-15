package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IWishController {

    @GetMapping(value = "/createWish")
    public String renderWishCreatorPage(){

        return "wishcreator.html";
    }

    @PostMapping(value="addNewWish")
    public String addNewWish(){

        return "redirect:/userpage";
    }

}
