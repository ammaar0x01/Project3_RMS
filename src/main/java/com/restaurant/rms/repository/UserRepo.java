package com.restaurant.rms.repository;

import com.restaurant.rms.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

//public interface UserRepo extends JpaRepository<User, Integer> {
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
