package com.restaurant.rms.controllers;

//import com.restaurant.rms.repository.Employee1Repo;
import com.restaurant.rms.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Number of routes: 10
 *
 */
@Controller
public class MainController {

    @Autowired
    private EmployeeRepo empRepo;

    @RequestMapping("/")
    public String signIn(){
        return "sign-in";
    }
//    @RequestMapping("/sign-in")
//    public String signIn1(){
//        return "sign-in";
//    }

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




    // RESERVATION
    @RequestMapping("/reservations")
    public String reservations(){
        return "reservation/reservations";
    }

    @RequestMapping("/reservations-add")
    public String reservationAdd(){
        return "reservation/reservations-add";
    }
    // **************************

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


    // ----------------------------------------

    // more ...
    // ----------------------------------------

}
