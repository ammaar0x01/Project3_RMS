package com.restaurant.rms.controllers;

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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/salaries")
public class EmployeeSalaryController {

    private final EmployeeSalaryService salaryService;
    private final EmployeeRepo employeeRepo;
    private final EmployeeSalaryRepo salaryRepo;

    @Autowired
    public EmployeeSalaryController(EmployeeSalaryService salaryService,
                                    EmployeeRepo employeeRepo,
                                    EmployeeSalaryRepo salaryRepo) {
        this.salaryService = salaryService;
        this.employeeRepo = employeeRepo;
        this.salaryRepo = salaryRepo;
    }

    // LIST
    @GetMapping({"", "/"})
    public String list(Model model) {
        List<EmployeeSalary> salaries = salaryRepo.findAll(Sort.by(Sort.Direction.DESC, "employeePaymentId"));
        model.addAttribute("salaries", salaries);
        return "salary/salaries";
    }

    // SHOW ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("salaryDTO", new EmployeeSalaryDTO());
        model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "salary/salaries-add";
    }

    // HANDLE ADD POST (form)
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("salaryDTO") EmployeeSalaryDTO dto,
                      BindingResult bindingResult,
                      Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
            return "salary/salaries-add";
        }

        salaryService.createFromDto(dto);
        return "redirect:/salaries";
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Optional<EmployeeSalary> opt = salaryService.getById(id);
        if (opt.isEmpty()) return "redirect:/salaries";

        EmployeeSalary salary = opt.get();
        EmployeeSalaryDTO dto = new EmployeeSalaryDTO();
        dto.setEmployeePaymentId(salary.getEmployeePaymentId());
        dto.setEmployeeId(salary.getEmployee() != null ? salary.getEmployee().getId() : null);
        dto.setAmount(salary.getEmployeePaymentAmount());
        dto.setMethod(salary.getEmployeePaymentMethod());
        dto.setLastPayment(salary.getLastPayment());

        model.addAttribute("salaryDTO", dto);
        model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "salary/salaries-edit";
    }

    // HANDLE EDIT POST
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("salaryDTO") EmployeeSalaryDTO dto,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
            return "salary/salaries-edit";
        }

        salaryService.updateFromDto(dto);
        return "redirect:/salaries";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        salaryService.delete(id);
        return "redirect:/salaries";
    }
}
