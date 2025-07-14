package com.restaurant.rms.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class MainController {

    @GetMapping("/")
    public String signIn(){
//        return "sign-in";
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

    @GetMapping("/profile-page")
    public String profile(){
        return "profile-page";
    }

    @GetMapping("/create-account")
    public String createAccount(){
        return "create-account";
    }
    // **************************

    // NEWER
    // returns details about a user who logged in
    @GetMapping("/user")
    public Principal user(Principal principalUser){
        return principalUser;
    }

    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model){
        model.addAttribute("name", token.getPrincipal().getAttribute("name"));
        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));
        return "./user-page";
    }

    @GetMapping("/login")
    public String login(){
        return "./custom-login";
    }
    // **************************

    // temp
    // MENU
    @GetMapping("/menu")
    public String menu(){
        return "menu/menu";
    }

    @GetMapping("/menu-add")
    public String menuAdd(){
        return "menu/menu-add";
    }
    // **************************
}
