package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UIController {


    @GetMapping(value="/login")
    public String renderLoginPage(){
        return "login.html";
    }

    @PostMapping(value="checkLogin")
    public String checkLogin(){


        if(true){
            return "redirect:/userpage";
        }
        else{
            return "redirect:/login";
        }

    }

    @GetMapping(value="userpage")
    public String renderUserpage(){

        return "userpage.html";
    }
}
