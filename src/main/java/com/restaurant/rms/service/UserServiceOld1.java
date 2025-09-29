//package com.restaurant.rms.service;
//
//import com.restaurant.rms.models.Users;
//import com.restaurant.rms.repository.UsersRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UsersRepo repo;
//
//
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//    public Users register(Users user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        repo.save(user);
//        return user;
//    }
//
//
//    public List<Users> getAll() {
//        return repo.findAll();
//    }
//
//}
