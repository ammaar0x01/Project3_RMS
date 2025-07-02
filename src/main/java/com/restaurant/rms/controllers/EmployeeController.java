package com.restaurant.rms.controllers;

import com.restaurant.rms.models.Employee;
import com.restaurant.rms.models.DTO.EmployeeDTO;
import com.restaurant.rms.repository.EmployeeRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;


/**
 * EMPLOYEE
 * Used for operations related to the Employee entity
 * */
@Controller
@RequestMapping("/employees")
//@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepo empRepo;

    // add constructor?
    // --------------------------------------------

    // CREATE
//    @GetMapping("/employees-add")
//    @RequestMapping("/employees-add")
    @GetMapping("/add")
//    @RequestMapping("/post/")
//    @GetMapping("/post/")
    public String employeeAdd(Model model) {
//    public String createReqGet(Model model){
        EmployeeDTO empDTO = new EmployeeDTO();
        model.addAttribute("employeeDTO", empDTO);
        return "employee/employees-add";
    }

//    @PostMapping("/employees-add")
    @PostMapping("/add")
//    @RequestMapping("/post/")
    public String createEmployee(
//    public String createReqPost(
            @Valid @ModelAttribute EmployeeDTO eDTO,
            BindingResult result
    ) {
        // checking for errors from the form
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "employee/employees-add";
        }

        Date currentDate = new Date();

        // creating the object
        Employee emp = new Employee(
                eDTO.getEmpFirstName(),
                eDTO.getEmpLastName(),
                currentDate,
                eDTO.getRole(),
                eDTO.getEmpContactNum(),
                eDTO.getEmpEmail()
        );

        // saving to database
        this.empRepo.save(emp);
        System.out.println("\n***Employee-object \n(" + emp + ") \nadded successfully***");

        // re-directing
        return "redirect:/employees";
    }
    // --------------------------------------------

    // READ
//    @GetMapping("/get")
    // --------------------------------------------

    // UPDATE
    // getting existing data of the product and displaying it on the update page
    // show edit page
    @GetMapping("/edit")
//        @GetMapping("/put")
//        @PutMapping("/edit")
    public String showEditPage(
//        public String updateReqGet(
            Model model,
            @RequestParam int id
    ) {
        System.out.println("\n***Getting...");
        try {
            Employee employee = empRepo.findById(id).get();
            EmployeeDTO empDTO = new EmployeeDTO();

            empDTO.setEmpFirstName(employee.getEmpFirstName());
            empDTO.setEmpLastName(employee.getEmpLastName());
            empDTO.setRole(employee.getRole());

            model.addAttribute("employee", employee);
            model.addAttribute("employeeDTO", empDTO);
        }
        catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
            return "redirect:/employee";
        }
        return "employee/editEmp";
    }

    // Updating an existing record

        @PostMapping("/edit")
//    @PutMapping("/edit")
//        @PutMapping("/put")
    public String updateProduct(
//        public String updateReqPut(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute EmployeeDTO EmployeeDTO,
//                @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult result
    ) {
        System.out.println("\n***Putting...");

        try {
            Employee employee = empRepo.findById(id).get();
            model.addAttribute("employeeDTO", employee);

            if (result.hasErrors()) {
                return "employee/editEmp";
            }

            // updating other details
            employee.setRole(EmployeeDTO.getRole());
            employee.setEmpFirstName(EmployeeDTO.getEmpFirstName());
            employee.setEmpLastName(EmployeeDTO.getEmpLastName());

            empRepo.save(employee);
            System.out.println("\n***Updated employee successfully");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return "redirect:/employees";
//        return "redirect:/employee/employees";
    }
    // --------------------------------------------


    // DELETE
    @GetMapping("/delete")
//        @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
//        public String delete(@RequestParam int id){
        try {
            Employee employee = empRepo.findById(id).get();

            // delete from database
            empRepo.delete(employee);
            System.out.println("\n***Successful deletion of:");
            System.out.println(employee);

            //                pRepo.delete(product);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return "redirect:/employees";
//            return "redirect:/products";
    }
    // --------------------------------------------


    // GET ALL
    // @RequestMapping("/dashboard-employees")
//    @RequestMapping("/employees")
//    @RequestMapping("/get/all")
//    @RequestMapping("/all")
    @RequestMapping("")

//    public String getAll(){
    public String employees(Model model) {
//        return "employee/employees";

        List<Employee> employees = empRepo.findAll(Sort.by(Sort.Direction.DESC, "empId"));

        model.addAttribute("employees", employees);
        return "employee/employees";
    }

    //    @RequestMapping("/employees-list")
//    @GetMapping("/employees-list")
    @GetMapping("/list")
    public String employeesList(Model model) {
//        List<Employee1> employees = empRepo.findAll();
        // or
        // reverse order
        List<Employee> employees = empRepo.findAll(Sort.by(Sort.Direction.DESC, "empId"));

        model.addAttribute("employees", employees);
        return "employee/employees-list";
    }
    // --------------------------------------------


    // Other
    @RequestMapping("/pay")
//    @RequestMapping("/employees-pay")
    public String employeesPay() {
        return "employee/employees-pay";
    }


}
