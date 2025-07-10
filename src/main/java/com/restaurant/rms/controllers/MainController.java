package com.restaurant.rms.controllers;

import com.restaurant.rms.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MainController
 * <br>
 * Number of routes: 4
 */
@Controller
public class MainController {

    @Autowired
    private EmployeeRepo empRepo;
    // **************************

    @RequestMapping("/")
    public String signIn(){
//        return "sign-in";
        return "dashboard";
    }

    @RequestMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

    @RequestMapping("/profile")
    public String profile(){
        return "profile";
    }

    @RequestMapping("/create-account")
    public String createAccount(){
        return "create-account";
    }
    // **************************

    // temp
    // MENU
    @RequestMapping("/menu")
    public String menu(){
        return "menu/menu";
    }

    @RequestMapping("/menu-add")
    public String menuAdd(){
        return "menu/menu-add";
    }
    // **************************
}
