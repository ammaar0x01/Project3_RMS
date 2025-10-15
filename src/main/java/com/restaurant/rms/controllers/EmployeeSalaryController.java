package com.restaurant.rms.controllers;

import com.restaurant.rms.models.DTO.EmployeeDTO;
import com.restaurant.rms.models.Employee;
import com.restaurant.rms.models.EmployeeSalary;
import com.restaurant.rms.models.DTO.EmployeeSalaryDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.repository.EmployeeSalaryRepo;
import com.restaurant.rms.service.EmployeeSalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee-salaries")
public class EmployeeSalaryController {
    @Autowired
    private final EmployeeRepo employeeRepo;

    @Autowired
    private final EmployeeSalaryRepo salaryRepo;

    @Autowired
    public EmployeeSalaryController(
            EmployeeRepo employeeRepo,
            EmployeeSalaryRepo salaryRepo
    ) {
        this.employeeRepo = employeeRepo;
        this.salaryRepo = salaryRepo;
    }
    // -------------------------------------

    // GET ALL //
    @GetMapping({"", "/"})
    public String getAll(Model model) {
        System.out.println("Employee-salary, get all");
        List<EmployeeSalary> salaries = salaryRepo.findAll(Sort.by(Sort.Direction.DESC, "employeePaymentId"));
        for (EmployeeSalary e : salaries) {
            System.out.println("-" + e);
        }

        model.addAttribute("salaries", salaries);
        return "salary/salaries";
    }

    // ADD //
    @GetMapping("/add")
    public String showAddForm(Model model) {
        System.out.println("Employee-salary, add page. get");
        model.addAttribute("salaryDTO", new EmployeeSalaryDTO());
        return "salary/salaries-add";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("salaryDTO") EmployeeSalaryDTO dto,
            BindingResult bindingResult,
            Model model
    ) {
        System.out.println("\nEmployee-salary, add page. post");

        if (bindingResult.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
            return "salary/salaries-add";
        }

//        Employee employee = new Employee(
//                "Fred",
//                "Aimes",
//                new Date(),
//                "manager",
//                "000 000 000",
//                "fredaimas@gmail.com"
//        );
//        Employee employee = new Employee(
//                "first_name",
//                "last_name",
//                new Date(),
//                "manager"
//        );

        Employee employee = employeeRepo.findById(101).get();

        System.out.println("Employee: " + employee);
        employeeRepo.save(employee);

//        EmployeeSalary salary = new EmployeeSalary(
//                dto.getAmount(),
//                dto.getMethod(),
//                dto.getLastPayment()
//        );
        EmployeeSalary salary = new EmployeeSalary(
                dto.getAmount(),
                dto.getMethod(),
                dto.getLastPayment(),
                employee
        );

        System.out.println("\nRecord: " + salary);
        salaryRepo.save(salary);
        return "redirect:/employee-salaries";
    }

    // EDIT //
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ) {
        System.out.println("\n*** Salaries(EDIT) Getting...");
        try {
            EmployeeSalary employeeSalary = salaryRepo.findById(id).get();
            EmployeeSalaryDTO salaryDTO = new EmployeeSalaryDTO();

            System.out.println("\nUpdate. employee-salary");
            System.out.println(employeeSalary);

            salaryDTO.setAmount(employeeSalary.getEmployeePaymentAmount());
            salaryDTO.setMethod(employeeSalary.getEmployeePaymentMethod());
            salaryDTO.setLastPayment(employeeSalary.getLastPayment());

            System.out.println("\nDTO: \n" + salaryDTO);

            model.addAttribute("employeeSalary", employeeSalary);
            model.addAttribute("DTO", salaryDTO);
        }
        catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
            return "redirect:/employee-salaries";
        }
        return "salary/salaries-edit";
    }
    @PostMapping("/edit")
    public String editRecord(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute("salaryDTO") EmployeeSalaryDTO employeeSalaryDTO,
            BindingResult bindingResult
    ) {
        System.out.println("\n***Employee salary (EDIT) Putting...");

        try {
            EmployeeSalary employeeSalary = salaryRepo.findById(id).get();
//            EmployeeSalary employeeSalary = salaryRepo.findById(id).orElse(null);
            model.addAttribute("employeeSalaryDTO", employeeSalary);

            if (bindingResult.hasErrors()) {
                return "salary/salaries-edit";
            }
            employeeSalary.setLastPayment(employeeSalaryDTO.getLastPayment());
            employeeSalary.setEmployeePaymentMethod(employeeSalaryDTO.getMethod());
            employeeSalary.setEmployeePaymentAmount(employeeSalaryDTO.getAmount());

            salaryRepo.save(employeeSalary);
            System.out.println("\n***Updated employee-salary successfully");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return "redirect:/employee-salaries";
    }

    // DELETE //
    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam int id) {
        try {
//            salaryRepo.deleteById(id);
            // or
            EmployeeSalary employeeSalary = salaryRepo.findById(id).get();

            // delete from database
            salaryRepo.delete(employeeSalary);
            System.out.println("\n***Successful deletion of:");
            System.out.println(employeeSalary);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return "redirect:/employee-salaries";
    }
}
