package com.restaurant.rms.repository;

import com.restaurant.rms.models.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerGroupRepo extends JpaRepository<CustomerGroup, Integer> {
    Optional<CustomerGroup> findByPhoneNumber(String phoneNumber);
}
