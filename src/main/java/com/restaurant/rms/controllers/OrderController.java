package com.restaurant.rms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/orders")
public class OrderController {

//    @RequestMapping("/get/all")
    @RequestMapping("/orders")
    public String orders(){
        return "order/orders";
    }

    @RequestMapping("/orders-add")
    public String ordersAdd(){
        return "order/orders-add";
    }

    // primarily for waiters and kitchen-staff
    @RequestMapping("/orders-only")
    public String ordersOnly(){
        return "order/orders-only";
    }

    @RequestMapping("/orders-only-add")
    public String ordersOnyAdd(){
        return "order/orders-only-add";
    }

    // TODO - add a route for a specific order
    // ...


    // **************************

}
