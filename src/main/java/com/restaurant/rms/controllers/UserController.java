//package com.restaurant.rms.controllers;
//
//import com.restaurant.rms.models.User;
//import com.restaurant.rms.models.DTO.UserDTO;
//import com.restaurant.rms.repository.EmployeeRepo;
//import com.restaurant.rms.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import jakarta.validation.Valid;
//import java.util.List;
//
//@Controller
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private EmployeeRepo employeeRepo;
//
//    // List all users
//    @GetMapping
//    public String listUsers(Model model) {
//        model.addAttribute("users", userRepo.findAll());
//        return "user/users";
//    }
//
//    // Show add form
//    @GetMapping("/add")
//    public String showAddForm(Model model) {
//        model.addAttribute("userDTO", new UserDTO());
//        model.addAttribute("employees", employeeRepo.findAll());
//        return "user/users-add";
//    }
//
//    // Save new user
//    @PostMapping("/save")
//    public String saveUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
//                           BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("employees", employeeRepo.findAll());
//            return "user/users-add";
//        }
//        var emp = employeeRepo.findById(userDTO.getEmployeeId());
//        if (emp.isEmpty()) {
//            result.rejectValue("employeeId", "NotFound", "Employee not found");
//            model.addAttribute("employees", employeeRepo.findAll());
//            return "user/users-add";
//        }
//        User user = new User(userDTO.getUsername(), userDTO.getPassword(),
//                userDTO.getRole(), emp.get());
//        userRepo.save(user);
//        return "redirect:/users";
//    }
//
//    // Show edit form
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable("id") Integer id, Model model) {
//        var opt = userRepo.findById(id);
//        if (opt.isEmpty()) {
//            return "redirect:/users";
//        }
//        User u = opt.get();
//        UserDTO dto = new UserDTO();
//        dto.setUserId(u.getUserId());
//        dto.setUsername(u.getUsername());
//        dto.setPassword(u.getPassword());
//        dto.setRole(u.getRole());
//        dto.setEmployeeId(u.getEmployee().getId());
//        model.addAttribute("userDTO", dto);
//        model.addAttribute("employees", employeeRepo.findAll());
//        return "user/users-edit";
//    }
//
//    // Update user
//    @PostMapping("/update")
//    public String updateUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
//                             BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("employees", employeeRepo.findAll());
//            return "user/users-edit";
//        }
//        var opt = userRepo.findById(userDTO.getUserId());
//        var empOpt = employeeRepo.findById(userDTO.getEmployeeId());
//        if (opt.isEmpty() || empOpt.isEmpty()) {
//            return "redirect:/users";
//        }
//        User user = opt.get();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(userDTO.getPassword());
//        user.setRole(userDTO.getRole());
//        user.setEmployee(empOpt.get());
//        userRepo.save(user);
//        return "redirect:/users";
//    }
//
//    // Delete user
//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id") Integer id) {
//        userRepo.deleteById(id);
//        return "redirect:/users";
//    }
//}

package com.restaurant.rms.controllers;

import com.restaurant.rms.models.User;
import com.restaurant.rms.models.DTO.UserDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.repository.UserRepo;
import com.restaurant.rms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepo userRepo;
    private final EmployeeRepo employeeRepo;
    private final UserService userService;

    public UserController(UserRepo userRepo, EmployeeRepo employeeRepo, UserService userService) {
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
        this.userService = userService;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "user/users";
    }

    // ADD FORM
    @GetMapping("/add")
    public String showAdd(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
        return "user/users-add";
    }

    // CREATE
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("userDTO") UserDTO dto,
                      BindingResult result,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "user/users-add";
        }
        userRepo.findByUsername(dto.getUsername()).ifPresent(u -> {
            result.rejectValue("username", "Duplicate", "Username already exists");
        });
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "user/users-add";
        }

        try {
            userService.createFromDto(dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("employeeId", "NotFound", ex.getMessage());
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "user/users-add";
        }
        return "redirect:/users";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") int id, Model model) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) return "redirect:/users";

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setEmployeeId(user.getEmployee() != null ? user.getEmployee().getId() : null);

        model.addAttribute("userDTO", dto);
        model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
        return "user/users-edit";
    }

    // UPDATE
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,
                       @Valid @ModelAttribute("userDTO") UserDTO dto,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "user/users-edit";
        }
        userRepo.findByUsername(dto.getUsername()).ifPresent(existing -> {
            if (!existing.getUserId().equals(id)) {
                result.rejectValue("username", "Duplicate", "Username already exists");
            }
        });
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "user/users-edit";
        }

        try {
            userService.updateFromDto(id, dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("employeeId", "NotFound", ex.getMessage());
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "user/users-edit";
        }
        return "redirect:/users";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
