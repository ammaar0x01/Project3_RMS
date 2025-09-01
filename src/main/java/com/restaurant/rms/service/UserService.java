//package com.restaurant.rms.service;
//
//import com.restaurant.rms.models.User;
//import com.restaurant.rms.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepo repo;
//
//    public UserService(UserRepo userRepo) {
//        this.repo = userRepo;
//    }
//
//    // ----------------------------------
//    public User add(User user) {
//        return repo.save(user);
//    }
//
//    public User read(int id) {
//        return repo.findById(id).orElse(null);
//    }
//
//    public List<User> getAll() {
//        return repo.findAll();
//    }
//
//    public User update(User user) {
//        return repo.save(user);
//    }
//
//    public boolean delete(int id) {
//        repo.deleteById(id);
//        return true;
//    }
//}

package com.restaurant.rms.service;

import com.restaurant.rms.models.Employee;
import com.restaurant.rms.models.User;
import com.restaurant.rms.models.DTO.UserDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final EmployeeRepo employeeRepo;

    public UserService(UserRepo userRepo, EmployeeRepo employeeRepo) {
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User createFromDto(UserDTO dto) {
        Employee emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        // Optionally encode password here if you have a PasswordEncoder
        User user = new User(dto.getUsername(), dto.getPassword(), dto.getRole(), emp);
        return userRepo.save(user);
    }

    public User updateFromDto(Integer id, UserDTO dto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepo.findByUsername(dto.getUsername()).ifPresent(existing -> {
            if (!existing.getUserId().equals(id)) {
                throw new IllegalArgumentException("Username already exists");
            }
        });
        Employee emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword()); // add encoder if available
        user.setRole(dto.getRole());
        user.setEmployee(emp);
        return userRepo.save(user);
    }

    public void delete(int id) {
        userRepo.deleteById(id);
    }
}
