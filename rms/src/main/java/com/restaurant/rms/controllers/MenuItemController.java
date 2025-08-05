package com.restaurant.rms.controllers;




import com.restaurant.rms.models.DTO.MenuItemDTO;
import com.restaurant.rms.models.MenuItem;
import com.restaurant.rms.repository.MenuItemRepo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

// Create
@Controller
@RequestMapping("/items")
public class MenuItemController {
    @Autowired
    private MenuItemRepo menuItemRepo;

    //add menu item form
    @GetMapping("/add")
    public String MenuItemAdd(Model model) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        model.addAttribute("menuItemDTO", menuItemDTO);
//        model.addAttribute("menuItems", menuItems);
        return "menu-item/menu-items-add";
    }

    /// ///////////////////////////////////////////////////////////////////////
    //create new menu item
    @PostMapping("/add")
    public String createMenuItem(
            @Valid @ModelAttribute MenuItemDTO mDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "menu-item/menu-items-add";

        }


        MenuItem menuItem = new MenuItem(
                mDTO.getMenuItemName(),
                mDTO.getMenuItemNum(),
                mDTO.getMenuItemPrice(),
                mDTO.getMenuItemCategory(),
                mDTO.getMenuItemEstServeTime()
        );


        // database save
        this.menuItemRepo.save(menuItem);
        System.out.println("\n***MenuItem-object \n(" + menuItem + ") \nadded successfully***");


        //go-to
        return "redirect:/items";

    }

    //view existing for update
    @GetMapping("/edit")
    public String showEditForm(@RequestParam(value = "id")int id, Model model) {
        System.out.println("\n***Getting...");
        try {

            MenuItemDTO menuItemDTO = new MenuItemDTO();
            MenuItem item = menuItemRepo.findById(id).get();


            menuItemDTO.setMenuItemName(item.getMenuItemName());
            menuItemDTO.setMenuItemNum(item.getMenuItemNum());
            menuItemDTO.setMenuItemPrice(item.getMenuItemPrice());
            menuItemDTO.setMenuItemCategory(item.getMenuItemCategory());
            menuItemDTO.setMenuItemEstServeTime(item.getMenuItemEstServeTime());

//            model.addAttribute("tableDTO", table1DTO);
            model.addAttribute("menuItemDTO", menuItemDTO);
            model.addAttribute("id", id);
            return "menu-item/menu-items-edit";


        } catch (Exception er) {
            System.out.println("Error: " + er.getMessage());
            return "redirect:/items";
        }
    }

    //update existing table no
    @PostMapping("/edit")
    public String updateTable(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute("menuItemDTO") MenuItemDTO menuItemDTO,
            BindingResult result
    ) {
        System.out.println("\n***Putting...");

        try {
            MenuItem item = menuItemRepo.findById(id).orElse(null);

            if (item == null) {
                System.out.println("Table with id " + id + " not found.");
                return "redirect:/items";
            }
            if (result.hasErrors()) {
                System.out.println(result.getAllErrors());
                model.addAttribute("id", id);
                return "menu-item/menu-items-edit";
            }



            item.setMenuItemName(menuItemDTO.getMenuItemName());
            item.setMenuItemNum(menuItemDTO.getMenuItemNum());
            item.setMenuItemPrice(menuItemDTO.getMenuItemPrice());
            item.setMenuItemCategory(menuItemDTO.getMenuItemCategory());
           item.setMenuItemEstServeTime(menuItemDTO.getMenuItemEstServeTime());




            this.menuItemRepo.save(item);
            System.out.println("\n***Table-object \n(" + item + ") \nupdated successfully***");
            return "redirect:/items";
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "redirect:/items";

    }
    /// ///////////////////////////////////////////////////////////////////////////

    // Delete
    @GetMapping("/delete")
    public String deleteTable(@RequestParam int id) {
        try {
            MenuItem menuItems = menuItemRepo.findById(id).get();

            // database delete
            menuItemRepo.delete(menuItems);
            System.out.println("\n***Successful deletion of:");
            System.out.println(menuItems);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("\n***Menu Item with id " + id + " deleted successfully***");
        return "redirect:/items";
    }
    /// ///////////////////////////////////////////////////////////////////////////

    //'getAll' method
    @RequestMapping("")
//
    public String items(Model model) {
        List<MenuItem> items = menuItemRepo.findAll(Sort.by(Sort.Direction.DESC, "menuItemId"));
//        List<RestaurantTable> tables = tableRepo.findAll(Sort.by(Sort.Direction.DESC, "table_id"));
        model.addAttribute("menuItems", items);
        return "menu-item/menu-items";
    }
    /// ///////////////////////////////////////////////////////////////////////////


}




