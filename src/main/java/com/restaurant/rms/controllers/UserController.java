//package com.restaurant.rms.controllers;
//
//import com.restaurant.rms.models.Users;
//import com.restaurant.rms.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class UserController {
//
//    @Autowired
//    private UserService service;
//
//
//    @PostMapping("/register")
//    public Users register(@RequestBody Users user) {
//        return service.register(user);
//
//    }
//
//
//    // GET
//    @GetMapping({"", "/"})
//    public String listUsers(Model model) {
//        System.out.println("GET. Attempting to get main-page...");
//
////        model.addAttribute("users", userRepo.findAll());
//        model.addAttribute("users", service.getAll());
//        for (Users u : service.getAll()){
//            System.out.println("-" + u);
//        }
//
//        return "user/users";
//    }
//}
