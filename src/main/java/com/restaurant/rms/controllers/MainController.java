package com.restaurant.rms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        // Send users to the dashboard on landing
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        // If you later add authentication, this will show the logged-in name.
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "dashboard";
    }

    @GetMapping("/profile-page")
    public String profilePage() {
        return "profile-page";
    }

    @GetMapping("/create-account")
    public String createAccount() {
        return "create-account";
    }

    // ----- Utility endpoints (no OAuth2) -----

    // Lightweight info endpoint for debugging auth state (works even without security)
    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        Map<String, Object> info = new HashMap<>();
        info.put("authenticated", principal != null);
        if (principal != null) {
            info.put("name", principal.getName());
        }
        return info;
    }

    // Keep a custom login page if you use one (plain view, no OAuth2 hooks)
    @GetMapping("/login")
    public String login() {
        return "custom-login"; // not "./custom-login"
    }

    // ----- Menu pages -----
    @GetMapping("/menu")
    public String menu() {
        return "menu/menu";
    }

    @GetMapping("/menu-add")
    public String menuAdd() {
        return "menu/menu-add";
    }
}
