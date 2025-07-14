package com.restaurant.rms.controllers;

import com.restaurant.rms.models.Employee;
import com.restaurant.rms.models.EmployeeSalary;
import com.restaurant.rms.models.DTO.EmployeeSalaryDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.repository.EmployeeSalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employee-salary")
public class EmployeeSalaryController {
    @Autowired
    private EmployeeSalaryRepo employeeSalaryRepository;

    @Autowired
    private EmployeeRepo employeeRepository;


    @GetMapping("")
    public List<EmployeeSalary> getAllSalaries() {
        return employeeSalaryRepository.findAll();
    }

    @PostMapping("/add")
    public EmployeeSalary addSalary(@RequestBody EmployeeSalaryDTO models) {
        Employee employee = employeeRepository.findById(models.getEmployeeId()).orElse(null);
        EmployeeSalary salary = new EmployeeSalary();

        salary.setEmployee(employee);
        salary.setEmployeePaymentAmount(models.getAmount());
        salary.setEmployeePaymentMethod(models.getMethod());
        salary.setLastPayment(LocalDate.now());
        return employeeSalaryRepository.save(salary);
    }
}
