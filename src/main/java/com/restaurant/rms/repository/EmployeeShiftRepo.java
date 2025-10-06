package com.restaurant.rms.repository;

import com.restaurant.rms.models.EmployeeShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeShiftRepo extends JpaRepository<EmployeeShift, Integer>{
}
