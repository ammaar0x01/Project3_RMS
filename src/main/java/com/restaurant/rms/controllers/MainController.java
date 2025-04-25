package com.restaurant.rms.controllers;

import com.restaurant.rms.models.Employee1;
import com.restaurant.rms.models.Employee1DTO;
import com.restaurant.rms.repository.Employee1Repo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * Number of routes: 10
 *
 */
@Controller
public class MainController {

    @Autowired
    private Employee1Repo empRepo;

    @RequestMapping("/")
    public String signIn(){
        return "sign-in";
    }
//    @RequestMapping("/sign-in")
//    public String signIn1(){
//        return "sign-in";
//    }

    @RequestMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

    @RequestMapping("/profile")
    public String profile(){
        return "profile";
    }
    // **************************


    // EMPLOYEE
    // @RequestMapping("/dashboard-employees")
    @RequestMapping("/employees")
    public String employees(){
        return "employee/employees";
    }

//    @RequestMapping("/employees-list")
    @GetMapping("/employees-list")
    public String employeesList(Model model){
//        List<Employee1> employees = empRepo.findAll();
        // or
        // reverse order
        List<Employee1> employees = empRepo.findAll(Sort.by(Sort.Direction.DESC, "empId"));

        model.addAttribute("employees", employees);
        return "employee/employees-list";
    }

    @RequestMapping("/employees-add")
    public String employeeAdd(Model model){
        Employee1DTO empDTO = new Employee1DTO();
        model.addAttribute("employee1DTO", empDTO);
        return "employee/employees-add";
    }
    @PostMapping("/employees-add")
    public String createEmployee(
            @Valid @ModelAttribute Employee1DTO eDTO,
            BindingResult result
    ){
        // checking for errors from the form
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "employee/employees-add";
        }

        Date currentDate = new Date();

        // creating the object
        Employee1 emp = new Employee1(
                eDTO.getEmpFirstName(),
                eDTO.getEmpLastName(),
                currentDate,
                eDTO.getRole()
        );

        // saving to database
        this.empRepo.save(emp);
        System.out.println("\n***Employee-object (" + emp + ") added successfully***");

        // re-directing
        return "redirect:/employees";
    }

    @RequestMapping("/employees-pay")
    public String employeesPay(){
        return "employee/employees-pay";
    }
    // **************************


    // ORDER
    @RequestMapping("/orders")
    public String orders(){
        return "order/orders";
    }

    @RequestMapping("/orders-add")
    public String ordersAdd(){
        return "order/orders-add";
    }

    // primarily for waiters and kitchen-staff
    @RequestMapping("/orders-only")
    public String ordersOnly(){
        return "order/orders-only";
    }

    @RequestMapping("/orders-only-add")
    public String ordersOnyAdd(){
        return "order/orders-only-add";
    }

    // TODO - add a route for a specific order
    // ...


    // **************************


    // RESERVATION
    @RequestMapping("/reservations")
    public String reservations(){
        return "reservation/reservations";
    }

    @RequestMapping("/reservations-add")
    public String reservationAdd(){
        return "reservation/reservations-add";
    }
    // **************************

    // MENU
    @RequestMapping("/menu")
    public String menu(){
        return "menu/menu";
    }

    @RequestMapping("/menu-add")
    public String menuAdd(){
        return "menu/menu-add";
    }
    // **************************


    // ----------------------------------------

    // more ...
    // ----------------------------------------

}
