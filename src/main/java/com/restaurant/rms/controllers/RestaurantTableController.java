package com.restaurant.rms.controllers;

import com.restaurant.rms.models.RestaurantTable;
import com.restaurant.rms.models.DTO.RestaurantTableDTO;
import com.restaurant.rms.repository.RestaurantTableRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tables")
public class RestaurantTableController {
    @Autowired
    private RestaurantTableRepo tableRepo;
    // --------------------------------

    // GET ALL //
    @RequestMapping("")
    public String tables(Model model) {
        List<RestaurantTable> tables = tableRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("restaurantTables", tables);
        return "restaurant-table/restaurant-tables";
    }


    // ADD //
    @GetMapping("/add")
    public String tableAdd(Model model) {
        RestaurantTableDTO tableDTO = new RestaurantTableDTO();
        model.addAttribute("tableDTO", tableDTO);
        return "restaurant-table/restaurant-tables-add";
    }
    @PostMapping("/add")
    public String createTable(
            @Valid @ModelAttribute RestaurantTableDTO tDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "restaurant-table/restaurant-tables-add";
        }

        Date lastCleaned = new Date();
        RestaurantTable tables = new RestaurantTable(
                tDTO.getTableNumber(),
                tDTO.getCapacity(),
                tDTO.getStatus(),
                lastCleaned
        );

        // database save
        this.tableRepo.save(tables);
        System.out.println("\n***Table-object \n(" + tables + ") \nadded successfully***");
        return "redirect:/tables";
    }


    // EDIT //
    @GetMapping("/edit")
    public String showEditForm(@RequestParam(value = "id")int id, Model model)  {
        System.out.println("\n***Getting...");

        try {
            RestaurantTableDTO table1DTO = new RestaurantTableDTO();
            RestaurantTable table = tableRepo.findById(id).get();

            // assert table != null;
            table1DTO.setTableNumber(table.getTableNumber());
            table1DTO.setCapacity(table.getCapacity());
            table1DTO.setStatus(table.getStatus());


            model.addAttribute("restaurantTableDTO", table1DTO);
            model.addAttribute("id", id);
            return "restaurant-table/restaurant-tables-edit";
        }
        catch (Exception er) {
            System.out.println("Error: " + er.getMessage());
            return "redirect:/tables";
        }
    }
    @PostMapping("/edit")
    public String updateTable(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute("restaurantTableDTO") RestaurantTableDTO tDTO,
            BindingResult result
    ) {
        System.out.println("\n***Putting...");

        try {
            RestaurantTable table = tableRepo.findById(id).orElse(null);

            if (table == null) {
                System.out.println("Table with id " + id + " not found.");
                return "redirect:/tables";
            }

            if (result.hasErrors()) {
                System.out.println(result.getAllErrors());
                model.addAttribute("id", id);
                return "restaurant-table/restaurant-tables-edit";
            }

            table.setTableNumber(tDTO.getTableNumber());
            table.setCapacity(tDTO.getCapacity());
            table.setStatus(tDTO.getStatus());

            this.tableRepo.save(table);
            System.out.println("\n***Table-object \n(" + table + ") \nupdated successfully***");
            return "redirect:/tables";
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "redirect:/tables";
    }


    // DELETE //
    @GetMapping("/delete")
    public String deleteTable(@RequestParam int id) {
        try {
            RestaurantTable tables = tableRepo.findById(id).get();

            // database delete
            tableRepo.delete(tables);
            System.out.println("\n***Successful deletion of:");
            System.out.println(tables);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("\n***Table with id " + id + " deleted successfully***");
        return "redirect:/tables";
    }

}
