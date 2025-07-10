package com.restaurant.rms.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MController {
//    @GetMapping("/")
//    public String index(){
//        return "Hello user";
//    }

    @GetMapping("/restricted")
    public String auth(){
        return "You now have access to this page";
    }

    // returns details about a user who logged in
    @RequestMapping("/user")
    public Principal user(Principal principalUser){
        return principalUser;
    }
}
