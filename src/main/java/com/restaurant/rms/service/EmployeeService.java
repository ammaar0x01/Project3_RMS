//package com.restaurant.rms.service;
//
//import com.restaurant.rms.models.Employee;
//import com.restaurant.rms.repository.EmployeeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//// is this necessary?
//@Service
//public class EmployeeService {
//    @Autowired
//    EmployeeRepo repo;
//
//    public EmployeeService(EmployeeRepo employeeRepo){
//        this.repo = employeeRepo;
//    }
//    // ----------------------------------
//
//    // CRUD operations
//    public Employee addEmployee(Employee employee){
//        return repo.save(employee);
//    }
//}
