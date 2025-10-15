package com.restaurant.rms.controllers;

import com.restaurant.rms.models.DTO.EmployeeSalaryDTO;
import com.restaurant.rms.models.DTO.ExpenseDTO;
import com.restaurant.rms.models.EmployeeSalary;
import com.restaurant.rms.models.Expense;
import com.restaurant.rms.repository.ExpenseRepo;
import jakarta.validation.Valid;
//import models.Expense;
//import models.DTO.ExpenseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import services.ExpensesRepo;

import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseRepo expenseRepo;
    // -----------------------------

    // READ //
    @GetMapping("")
    public String showExpenses(Model model) {
        System.out.println("\nEmployee-expense. get all");

        List<Expense> expenses = expenseRepo.findAll();

        for(Expense e : expenses){
            System.out.println("- " + e);
        }

        model.addAttribute("Expenses", expenses);
        return "expense/expenses";
    }

    // ADD //
    @GetMapping("/add")
    public String showCreatePage(Model model) {
        System.out.println("Employee-expense. add page. get");

        ExpenseDTO expenseDTO = new ExpenseDTO();
        model.addAttribute("expenseDTO", expenseDTO);
        return "expense/expenses-add";
    }
    @PostMapping("/add")
    public String createExpense(
            @Valid @ModelAttribute ExpenseDTO expensesDTO,
            BindingResult result
    ) {
        System.out.println("Employee-expense. add page. post");

        if (result.hasErrors()) {
            return "expense/expenses-add";
//            return "Expenses/createExpense";
        }

        System.out.println("\nDTO values received:");
        System.out.println("Name: " + expensesDTO.getExpenseName());
        System.out.println("Amount: " + expensesDTO.getExpenseAmount());
        System.out.println("Date: " + expensesDTO.getExpenseDate());


//        Expense expense = new Expense();
//        expense.setExpenseName(expensesDTO.getExpenseName());

//        Date date = new Date();
//
////        Expense.setExpenseDate(date.toString());
////        Expense.setExpenseDate(LocalDateTime.now());
//        expense.setExpenseDate(expensesDTO.getExpenseDate());
//
//        expense.setExpenseAmount(expensesDTO.getExpenseAmount());



        Expense expense = new Expense(
                expensesDTO.getExpenseName(),
                expensesDTO.getExpenseDate(),
                expensesDTO.getExpenseAmount()
        );


        System.out.println("\nRecord:");
        System.out.println(expense);
        expenseRepo.save(expense);
        return "redirect:/expenses";
    }

    // EDIT //
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ) {
        System.out.println("Employee-expense. edit page. get");

        try {
            Expense expense = expenseRepo.findById(id).get();
            ExpenseDTO expenseDTO = new ExpenseDTO();

            expenseDTO.setExpenseName(expense.getExpenseName());
//            Date date = new Date();
//            Expense.setExpenseDate(LocalDateTime.now());
//            Expense.setExpenseDate(date.toString());

            expenseDTO.setExpenseAmount(expense.getExpenseAmount());
            expenseDTO.setExpenseDate(expense.getExpenseDate());

//            model.addAttribute("Expense", expense);
            model.addAttribute("expenseDTO", expenseDTO);
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/expenses";
//            return "redirect:/Expenses";

        }
        return "expense/expenses-edit";

    }
//    @PostMapping("/edit")
//    public String updateExpense(
//            Model model,
//            @RequestParam int id,
//            @Valid @ModelAttribute ExpenseDTO expenseDTO,
//            BindingResult result
//    ) {
//        System.out.println("Employee-expense. edit page. post");
//
//        try {
//            Expense expense = expenseRepo.findById(id).get();
//            model.addAttribute("expenseDTO", expense);
////            model.addAttribute("Expense", expense);
//
//            if (result.hasErrors()) {
//                return "expense/expenses-edit";
//            }
//
//            System.out.println("\nCurrent object:");
//            System.out.println(expense);
//
//            System.out.println("\nDTO data");
//            System.out.println(expenseDTO.getExpenseName());
//            System.out.println(expenseDTO.getExpenseAmount());
//            System.out.println(expenseDTO.getExpenseDate());
//
//            expense.setExpenseName(expenseDTO.getExpenseName());
//            expense.setExpenseAmount(expenseDTO.getExpenseAmount());
//            expense.setExpenseDate(expenseDTO.getExpenseDate());
//
//            System.out.println(expense);
//            expenseRepo.save(expense);
//            System.out.println("\n***updated (expense)");
//
//        }
//        catch (Exception ex) {
//            System.out.println("Exception: " + ex.getMessage());
//        }
//        return "redirect:/expenses";
//    }



    // newer //
//    @PostMapping("/edit")
//    public String updateExpense(
//            Model model,
//            @Valid @ModelAttribute ExpenseDTO expenseDTO,
//            BindingResult result
//    ) {
//        System.out.println("Employee-expense. edit page. post");
//
//        if (result.hasErrors()) {
//            return "expense/expenses-edit";
//        }
//
//        try {
//            Expense expense = expenseRepo.findById(expenseDTO.getId()).get();
//
//
//            System.out.println("\nCurrent object:");
//            System.out.println(expense);
//
//            System.out.println("\nDTO data");
//            System.out.println(expenseDTO.getExpenseName());
//            System.out.println(expenseDTO.getExpenseAmount());
//            System.out.println(expenseDTO.getExpenseDate());
//
//            expense.setExpenseName(expenseDTO.getExpenseName());
//            expense.setExpenseAmount(expenseDTO.getExpenseAmount());
//            expense.setExpenseDate(expenseDTO.getExpenseDate());
//
//            expenseRepo.save(expense);
//            System.out.println("Expense: \n" + expense);
//            System.out.println("*** Expense updated ***");
//        } catch (Exception ex) {
//            System.out.println("Exception: " + ex.getMessage());
//        }
//
//        return "redirect:/expenses";
//    }


    // copy paste //
    @PostMapping("/edit")
    public String editRecord(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ExpenseDTO expenseDTO,
//            @Valid @ModelAttribute("salaryDTO") EmployeeSalaryDTO employeeSalaryDTO,
            BindingResult bindingResult
    ) {
        System.out.println("\n***Expense (EDIT) Putting...");

        try {
            Expense expense = expenseRepo.findById(expenseDTO.getId()).get();
            model.addAttribute("expenseDTO", expense);
//            model.addAttribute("expenseDTO", expense);
////            model.addAttribute("Expense", expense);
            if (bindingResult.hasErrors()) {
                return "expense/expenses-edit";
            }

            System.out.println("\nCurrent object:");
            System.out.println(expense);

            System.out.println("\nDTO data");
            System.out.println(expenseDTO.getExpenseName());
            System.out.println(expenseDTO.getExpenseAmount());
            System.out.println(expenseDTO.getExpenseDate());

            expense.setExpenseName(expenseDTO.getExpenseName());
            expense.setExpenseAmount(expenseDTO.getExpenseAmount());
            expense.setExpenseDate(expenseDTO.getExpenseDate());

            expenseRepo.save(expense);
            System.out.println("Expense: \n" + expense);
            System.out.println("*** Expense updated ***");


//            EmployeeSalary employeeSalary = salaryRepo.findById(id).get();
////            EmployeeSalary employeeSalary = salaryRepo.findById(id).orElse(null);
//            model.addAttribute("employeeSalaryDTO", employeeSalary);
//
//            if (bindingResult.hasErrors()) {
//                return "salary/salaries-edit";
//            }
//            employeeSalary.setLastPayment(employeeSalaryDTO.getLastPayment());
//            employeeSalary.setEmployeePaymentMethod(employeeSalaryDTO.getMethod());
//            employeeSalary.setEmployeePaymentAmount(employeeSalaryDTO.getAmount());
//
//            salaryRepo.save(employeeSalary);
//            System.out.println("\n***Updated employee-salary successfully");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return "redirect:/expenses";
    }

}
