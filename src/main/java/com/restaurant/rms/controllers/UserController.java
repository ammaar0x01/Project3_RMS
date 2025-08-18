package com.restaurant.rms.controllers;

import com.restaurant.rms.models.User;
import com.restaurant.rms.models.DTO.UserDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    // List all users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "user/users";
    }

    // Show add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("employees", employeeRepo.findAll());
        return "user/users-add";
    }

    // Save new user
    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll());
            return "user/users-add";
        }
        var emp = employeeRepo.findById(userDTO.getEmployeeId());
        if (emp.isEmpty()) {
            result.rejectValue("employeeId", "NotFound", "Employee not found");
            model.addAttribute("employees", employeeRepo.findAll());
            return "user/users-add";
        }
        User user = new User(userDTO.getUsername(), userDTO.getPassword(),
                userDTO.getRole(), emp.get());
        userRepo.save(user);
        return "redirect:/users";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        var opt = userRepo.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/users";
        }
        User u = opt.get();
        UserDTO dto = new UserDTO();
        dto.setUserId(u.getUserId());
        dto.setUsername(u.getUsername());
        dto.setPassword(u.getPassword());
        dto.setRole(u.getRole());
        dto.setEmployeeId(u.getEmployee().getId());
        model.addAttribute("userDTO", dto);
        model.addAttribute("employees", employeeRepo.findAll());
        return "user/users-edit";
    }

    // Update user
    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll());
            return "user/users-edit";
        }
        var opt = userRepo.findById(userDTO.getUserId());
        var empOpt = employeeRepo.findById(userDTO.getEmployeeId());
        if (opt.isEmpty() || empOpt.isEmpty()) {
            return "redirect:/users";
        }
        User user = opt.get();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setEmployee(empOpt.get());
        userRepo.save(user);
        return "redirect:/users";
    }

    // Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}
