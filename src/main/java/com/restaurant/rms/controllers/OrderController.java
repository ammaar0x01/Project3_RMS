package com.restaurant.rms.controllers;

import com.restaurant.rms.models.DTO.OrderDTO;
import com.restaurant.rms.models.Order;
import com.restaurant.rms.repository.OrderRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepo orderRepo;
    // --------------------------------------------

    // GET ALL
//    @RequestMapping("/get/all")
    @RequestMapping("")
    public String orders(){
        return "order/orders";
    }
    // --------------------------------------------

    // ADD
    @RequestMapping("/add")
    public String ordersAdd(){
        return "order/orders-add";
    }
    @GetMapping("/add")
//    @RequestMapping("/post/")
//    @GetMapping("/post/")
    public String getPageToCreate(Model model) {
//    public String createReqGet(Model model){
        System.out.println("attempting to add to database...");
        OrderDTO orderDTO = new OrderDTO();
        model.addAttribute("orderDTO", orderDTO);
        return "order/orders-add";

//        EmployeeDTO empDTO = new EmployeeDTO();
//        model.addAttribute("employeeDTO", empDTO);
//        return "employee/employees-add";
    }

    //    @PostMapping("/employees-add")
    @PostMapping("/add")
//    @RequestMapping("/post/")
    public String create(
//    public String createReqPost(
            @Valid @ModelAttribute OrderDTO orderDTO,
            BindingResult result
    ) {
        // checking for errors from the form
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "order/orders-add";
//            return "employee/employees-add";
        }
        System.out.println("\n***Trying to add data...");

//        Date currentDate = new Date();

        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime a = LocalDateTime.now();
        Order order = new Order(
                now,
//                orderDTO.getOrderTime(),
//                orderDTO.getEstimatedWaitTime(),
                Double.valueOf(orderDTO.getEstimatedWaitTime()),

//                orderDTO.getTotalPrice(),
                Double.valueOf(orderDTO.getTotalPrice()),

                orderDTO.getStatus()
        );

        // creating the object
//        Employee emp = new Employee(
//                eDTO.getEmpFirstName(),
//                eDTO.getEmpLastName(),
//                currentDate,
//                eDTO.getRole(),
//                eDTO.getEmpContactNum(),
//                eDTO.getEmpEmail()
//        );
//
//        // saving to database
        this.orderRepo.save(order);
        System.out.println("\n***Order-object \n(" + order + ") \nadded successfully***");

//        this.empRepo.save(emp);
//        System.out.println("\n***Employee-object \n(" + emp + ") \nadded successfully***");

        // re-directing
        return "redirect:/orders";
//        return "redirect:/employees";
    }
    // --------------------------------------------
    // --------------------------------------------

    // primarily for waiters and kitchen-staff
    @RequestMapping("/simple-mode")
//    @RequestMapping("/orders-only")
    public String ordersOnly(){
        return "order/orders-only";
    }


    @RequestMapping("/simple-mode/add")
//    @RequestMapping("/orders-only-add")
    public String ordersOnyAdd(){
        return "order/orders-only-add";
    }
    // --------------------------------------------




}
