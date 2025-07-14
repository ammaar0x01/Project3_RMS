//package com.restaurant.rms.controllers;
//
//import com.restaurant.rms.models.Employee;
//import com.restaurant.rms.models.User;
//import com.restaurant.rms.models.DTO.UserDTO;
//import com.restaurant.rms.repository.EmployeeRepo;
//import com.restaurant.rms.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserRepo userRepository;
//
//    @Autowired
//    private EmployeeRepo employeeRepository;
//
//
////    @GetMapping("/all")
//    @GetMapping("")
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @PostMapping("/add")
//    public User createUser(@RequestBody UserDTO userDTO) {
////        Employee employee = employeeRepository.findById(userDTO.getEmployeeId()).orElse(null);
//        Employee employee = employeeRepository.findById(userDTO.getEmployeeId()).orElse(null);
//
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(userDTO.getPassword());
//        user.setRole(userDTO.getRole());
//        user.setEmployee(employee);
//        return userRepository.save(user);
//    }
//}
