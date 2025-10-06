package com.restaurant.rms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class _MainController {
    @RequestMapping({"", "/"})
    public String defaultRoute(){
        return "_general/home";
    }
    @RequestMapping("/home")
    public String home(){
        return "_general/home";
    }

}
