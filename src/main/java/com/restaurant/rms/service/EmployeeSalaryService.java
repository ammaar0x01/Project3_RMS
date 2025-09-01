//package com.restaurant.rms.service;
//
//import com.restaurant.rms.models.Employee;
//import com.restaurant.rms.models.EmployeeSalary;
//import com.restaurant.rms.models.DTO.EmployeeSalaryDTO;
//import com.restaurant.rms.repository.EmployeeRepo;
//import com.restaurant.rms.repository.EmployeeSalaryRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class EmployeeSalaryService {
//
//    private final EmployeeSalaryRepo repo;
//    private final EmployeeRepo employeeRepo;
//
//    @Autowired
//    public EmployeeSalaryService(EmployeeSalaryRepo employeeSalaryRepo, EmployeeRepo employeeRepo) {
//        this.repo = employeeSalaryRepo;
//        this.employeeRepo = employeeRepo;
//    }
//
//    // -------------------- Standard CRUD --------------------
//    public EmployeeSalary add(EmployeeSalary employeeSalary) {
//        return repo.save(employeeSalary);
//    }
//
//    public EmployeeSalary read(int id) {
//        return repo.findById(id).orElse(null);
//    }
//
//    public List<EmployeeSalary> getAll() {
//        return repo.findAll();
//    }
//
//    public EmployeeSalary update(EmployeeSalary employeeSalary) {
//        return repo.save(employeeSalary);
//    }
//
//    public boolean delete(int id) {
//        repo.deleteById(id);
//        return true;
//    }
//
//    // -------------------- DTO Methods --------------------
//    public EmployeeSalary createFromDto(EmployeeSalaryDTO dto) {
//        EmployeeSalary salary = new EmployeeSalary();
//        if (dto.getEmployeeId() != null) {
//            Employee employee = employeeRepo.findById(dto.getEmployeeId()).orElse(null);
//            salary.setEmployee(employee);
//        }
//        salary.setEmployeePaymentAmount(dto.getAmount());
//        salary.setEmployeePaymentMethod(dto.getMethod());
//        salary.setLastPayment(dto.getLastPayment());
//        return repo.save(salary);
//    }
//
//    public Optional<EmployeeSalary> getById(int id) {
//        return repo.findById(id);
//    }
//
//    public EmployeeSalary updateFromDto(EmployeeSalaryDTO dto) {
//        Optional<EmployeeSalary> optSalary = repo.findById(dto.getEmployeePaymentId());
//        if (optSalary.isEmpty()) {
//            return null;
//        }
//        EmployeeSalary salary = optSalary.get();
//
//        if (dto.getEmployeeId() != null) {
//            Employee employee = employeeRepo.findById(dto.getEmployeeId()).orElse(null);
//            salary.setEmployee(employee);
//        }
//        salary.setEmployeePaymentAmount(dto.getAmount());
//        salary.setEmployeePaymentMethod(dto.getMethod());
//        salary.setLastPayment(dto.getLastPayment());
//
//        return repo.save(salary);
//    }
//}


package com.restaurant.rms.service;

import com.restaurant.rms.models.Employee;
import com.restaurant.rms.models.EmployeeSalary;
import com.restaurant.rms.models.DTO.EmployeeSalaryDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.repository.EmployeeSalaryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSalaryService {

    private final EmployeeSalaryRepo repo;
    private final EmployeeRepo employeeRepo;

    public EmployeeSalaryService(EmployeeSalaryRepo repo, EmployeeRepo employeeRepo) {
        this.repo = repo;
        this.employeeRepo = employeeRepo;
    }

    public List<EmployeeSalary> getAll() {
        return repo.findAll();
    }

    public EmployeeSalary getByIdOrThrow(int id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Salary not found"));
    }

    public EmployeeSalary createFromDto(EmployeeSalaryDTO dto) {
        Employee emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        EmployeeSalary s = new EmployeeSalary(emp, dto.getAmount(), dto.getMethod(), dto.getLastPayment());
        return repo.save(s);
    }

    public EmployeeSalary updateFromDto(EmployeeSalaryDTO dto) {
        if (dto.getEmployeePaymentId() == null) {
            throw new IllegalArgumentException("Salary ID is required for update");
        }
        EmployeeSalary s = getByIdOrThrow(dto.getEmployeePaymentId());
        Employee emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        s.setEmployee(emp);
        s.setEmployeePaymentAmount(dto.getAmount());
        s.setEmployeePaymentMethod(dto.getMethod());
        s.setLastPayment(dto.getLastPayment());

        return repo.save(s);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}

