package com.restaurant.rms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Number of routes: 10
 *
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String signIn(){
        return "sign-in";
    }
    @RequestMapping("/sign-in")
    public String signIn1(){
        return "sign-in";
    }

    @RequestMapping("/sign-up")
    public String signUp(){
        return "sign-up";
    }

    // @RequestMapping("/dashboard-home")
    @RequestMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

    // @RequestMapping("/dashboard-employees")
    @RequestMapping("/employees")
    public String employees(){
        return "employee/employees";
    }

    @RequestMapping("/orders")
    public String orders(){
        return "orders";
    }

    @RequestMapping("/reservations")
    public String reservations(){
        return "reservation/reservations";
    }

    @RequestMapping("/profile")
    public String profile(){
        return "profile";
    }
    // ----------------------------------------

    @RequestMapping("/orders-only")
    public String ordersOnly(){
        return "orders-only";
    }

    @RequestMapping("/employees-list")
    public String employeesList(){
        return "employee/employees-list";
    }
//    @RequestMapping("/employees-list1")
//    public String employeesList1(){
//        return "employees-list-old";
//    }

    @RequestMapping("/employees-pay")
    public String employeesPay(){
        return "employee/employees-pay";
    }
    @RequestMapping("/employees-pay1")
    public String employeesPay1(){
        return "employee/employees-pay1";
    }

    @RequestMapping("/employees-add")
    public String employeeAdd(){
        return "employee/employees-add";
    }
    // ---

    @RequestMapping("/reservations-add")
    public String reservationAdd(){
        return "reservation/reservations-add";
    }
    // ----------------------------------------

}
