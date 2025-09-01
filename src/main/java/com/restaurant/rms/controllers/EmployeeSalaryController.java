//package com.restaurant.rms.controllers;
//
//import com.restaurant.rms.models.Employee;
//import com.restaurant.rms.models.EmployeeSalary;
//import com.restaurant.rms.models.DTO.EmployeeSalaryDTO;
//import com.restaurant.rms.repository.EmployeeRepo;
//import com.restaurant.rms.repository.EmployeeSalaryRepo;
//import com.restaurant.rms.service.EmployeeSalaryService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/salaries")
//public class EmployeeSalaryController {
//
//    private final EmployeeSalaryService salaryService;
//    private final EmployeeRepo employeeRepo;
//    private final EmployeeSalaryRepo salaryRepo;
//
//    @Autowired
//    public EmployeeSalaryController(EmployeeSalaryService salaryService,
//                                    EmployeeRepo employeeRepo,
//                                    EmployeeSalaryRepo salaryRepo) {
//        this.salaryService = salaryService;
//        this.employeeRepo = employeeRepo;
//        this.salaryRepo = salaryRepo;
//    }
//
//    // LIST
//    @GetMapping({"", "/"})
//    public String list(Model model) {
//        List<EmployeeSalary> salaries = salaryRepo.findAll(Sort.by(Sort.Direction.DESC, "employeePaymentId"));
//        model.addAttribute("salaries", salaries);
//        return "salary/salaries";
//    }
//
//    // SHOW ADD FORM
//    @GetMapping("/add")
//    public String showAddForm(Model model) {
//        model.addAttribute("salaryDTO", new EmployeeSalaryDTO());
//        model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
//        return "salary/salaries-add";
//    }
//
//    // HANDLE ADD POST (form)
//    @PostMapping("/add")
//    public String add(@Valid @ModelAttribute("salaryDTO") EmployeeSalaryDTO dto,
//                      BindingResult bindingResult,
//                      Model model) {
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
//            return "salary/salaries-add";
//        }
//
//        salaryService.createFromDto(dto);
//        return "redirect:/salaries";
//    }
//
//    // SHOW EDIT FORM
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable("id") int id, Model model) {
//        Optional<EmployeeSalary> opt = salaryService.getById(id);
//        if (opt.isEmpty()) return "redirect:/salaries";
//
//        EmployeeSalary salary = opt.get();
//        EmployeeSalaryDTO dto = new EmployeeSalaryDTO();
//        dto.setEmployeePaymentId(salary.getEmployeePaymentId());
//        dto.setEmployeeId(salary.getEmployee() != null ? salary.getEmployee().getId() : null);
//        dto.setAmount(salary.getEmployeePaymentAmount());
//        dto.setMethod(salary.getEmployeePaymentMethod());
//        dto.setLastPayment(salary.getLastPayment());
//
//        model.addAttribute("salaryDTO", dto);
//        model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
//        return "salary/salaries-edit";
//    }
//
//    // HANDLE EDIT POST
//    @PostMapping("/edit")
//    public String edit(@Valid @ModelAttribute("salaryDTO") EmployeeSalaryDTO dto,
//                       BindingResult bindingResult,
//                       Model model) {
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
//            return "salary/salaries-edit";
//        }
//
//        salaryService.updateFromDto(dto);
//        return "redirect:/salaries";
//    }
//
//    // DELETE
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable("id") int id) {
//        salaryService.delete(id);
//        return "redirect:/salaries";
//    }
//}

package com.restaurant.rms.controllers;

import com.restaurant.rms.models.EmployeeSalary;
import com.restaurant.rms.models.DTO.EmployeeSalaryDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import com.restaurant.rms.service.EmployeeSalaryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salaries")
public class EmployeeSalaryController {

    private final EmployeeSalaryService salaryService;
    private final EmployeeRepo employeeRepo;

    public EmployeeSalaryController(EmployeeSalaryService salaryService, EmployeeRepo employeeRepo) {
        this.salaryService = salaryService;
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("salaries", salaryService.getAll());
        return "salary/salaries";
    }

    // ADD
    @GetMapping("/add")
    public String showAdd(Model model) {
        model.addAttribute("salaryDTO", new EmployeeSalaryDTO());
        model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
        return "salary/salaries-add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("salaryDTO") EmployeeSalaryDTO dto,
                      BindingResult result,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "salary/salaries-add";
        }
        try {
            salaryService.createFromDto(dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("employeeId", "NotFound", ex.getMessage());
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "salary/salaries-add";
        }
        return "redirect:/salaries";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") int id, Model model) {
        EmployeeSalary s;
        try {
            s = salaryService.getByIdOrThrow(id);
        } catch (IllegalArgumentException ex) {
            return "redirect:/salaries";
        }

        EmployeeSalaryDTO dto = new EmployeeSalaryDTO();
        dto.setEmployeePaymentId(s.getEmployeePaymentId());
        dto.setEmployeeId(s.getEmployee() != null ? s.getEmployee().getId() : null);
        dto.setAmount(s.getEmployeePaymentAmount());
        dto.setMethod(s.getEmployeePaymentMethod());
        dto.setLastPayment(s.getLastPayment());

        model.addAttribute("salaryDTO", dto);
        model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
        return "salary/salaries-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,
                       @Valid @ModelAttribute("salaryDTO") EmployeeSalaryDTO dto,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "salary/salaries-edit";
        }
        // Ensure DTO has the ID from path
        dto.setEmployeePaymentId(id);

        try {
            salaryService.updateFromDto(dto);
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage().toLowerCase().contains("employee")) {
                result.rejectValue("employeeId", "NotFound", ex.getMessage());
            } else {
                result.reject("Error", ex.getMessage());
            }
            model.addAttribute("employees", employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empFirstName")));
            return "salary/salaries-edit";
        }
        return "redirect:/salaries";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        salaryService.delete(id);
        return "redirect:/salaries";
    }
}

