package com.restaurant.rms.service;

import com.restaurant.rms.models.Employee;
import com.restaurant.rms.models.EmployeeSalary;
import com.restaurant.rms.models.DTO.EmployeeSalaryDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.repository.EmployeeSalaryRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSalaryService {

    private final EmployeeSalaryRepo salaryRepo;
    private final EmployeeRepo employeeRepo;

    public EmployeeSalaryService(EmployeeSalaryRepo salaryRepo, EmployeeRepo employeeRepo) {
        this.salaryRepo = salaryRepo;
        this.employeeRepo = employeeRepo;
    }

    public List<EmployeeSalary> getAll() {
        return salaryRepo.findAll();
    }

    public Optional<EmployeeSalary> getById(int id) {
        return salaryRepo.findById(id);
    }

    public EmployeeSalary createFromDto(EmployeeSalaryDTO dto) {
        Employee emp = employeeRepo.findById(dto.getEmployeeId()).orElse(null);
        EmployeeSalary salary = new EmployeeSalary();
        salary.setEmployee(emp);
        salary.setEmployeePaymentAmount(dto.getAmount());
        salary.setEmployeePaymentMethod(dto.getMethod());
        salary.setLastPayment(dto.getLastPayment() != null ? dto.getLastPayment() : LocalDate.now());
        return salaryRepo.save(salary);
    }

    public EmployeeSalary updateFromDto(EmployeeSalaryDTO dto) {
        if (dto.getEmployeePaymentId() == null) {
            throw new IllegalArgumentException("employeePaymentId required for update");
        }
        Optional<EmployeeSalary> opt = salaryRepo.findById(dto.getEmployeePaymentId());
        if (opt.isEmpty()) throw new IllegalArgumentException("Salary not found");
        EmployeeSalary salary = opt.get();
        Employee emp = employeeRepo.findById(dto.getEmployeeId()).orElse(null);
        salary.setEmployee(emp);
        salary.setEmployeePaymentAmount(dto.getAmount());
        salary.setEmployeePaymentMethod(dto.getMethod());
        salary.setLastPayment(dto.getLastPayment() != null ? dto.getLastPayment() : LocalDate.now());
        return salaryRepo.save(salary);
    }

    public void delete(int id) {
        salaryRepo.deleteById(id);
    }
}
