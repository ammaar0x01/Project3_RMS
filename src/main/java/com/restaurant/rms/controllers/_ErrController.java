package com.restaurant.rms.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class _ErrController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(){
        return "404";
    }

    // some other error codes
    // ...
}
