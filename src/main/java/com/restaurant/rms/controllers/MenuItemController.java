package com.restaurant.rms.controllers;

import com.restaurant.rms.models.DTO.MenuItemDTO;
import com.restaurant.rms.models.MenuItem;
import com.restaurant.rms.repository.MenuItemRepo;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/MenuItem")
//@RequestMapping("/menuItems")
public class MenuItemController {
    @Autowired
    private MenuItemRepo menuItemRepo;

    //add menu item form
    @RequestMapping("/add")
//    @RequestMapping("/menuItem-add")
    public String addMenuItem(Model model) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        model.addAttribute("menuItemDTO", menuItemDTO);
        return "menuItem/menuItem-add";
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
            return "menuItem/menuItems-add";

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
        System.out.println("\n**MenuItem-object \n(" + menuItem + ") \nadded successfully");


        //go-to
        return "redirect:/menuItems";

    }

    //view existing for update
    @GetMapping("/edit")
    public String showEditForm(@RequestParam Model model, int id) {
        System.out.println("\n***Getting...");
        try {

            MenuItem menuItems = menuItemRepo.findById(id).get();
            MenuItemDTO menuItemDTO = new MenuItemDTO();

            menuItemDTO.setMenuItemName(menuItems.getMenuItemName());
            menuItemDTO.setMenuItemName(menuItems.getMenuItemNum());
            menuItemDTO.setMenuItemPrice(menuItems.getMenuItemPrice());
            menuItemDTO.setMenuItemCategory(menuItems.getMenuItemCategory());
            menuItemDTO.setMenuItemEstServeTime(menuItems.getMenuItemEstServeTime());

//            model.addAttribute("tableDTO", table1DTO);
            model.addAttribute("menuItemDTO", menuItemDTO);


        } catch (Exception evil) {
            System.out.println("Error: " + evil.getMessage());
            return "redirect:/menuItems";
        }

        return "menuItem/edit-menuItem";
    }

    //update existing table no
    @PostMapping("/edit")
    public String updateTable(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute MenuItemDTO menuITemDTO,
            BindingResult result
    ) {
        System.out.println("\n***Putting...");

        try {
            MenuItem menuItem = menuItemRepo.findById(id).get();
            model.addAttribute("menuItemDTO", menuItem);

            if (result.hasErrors()) {
                System.out.println(result.getAllErrors());
                return "menuItems/edit-menuItem";
            }



            menuItem.setMenuItemName(menuITemDTO.getMenuItemName());
            menuItem.setMenuItemNum(menuITemDTO.getMenuItemNum());
            menuItem.setMenuItemPrice(menuITemDTO.getMenuItemPrice());
            menuItem.setMenuItemCategory(menuITemDTO.getMenuItemCategory());
            menuItem.setMenuItemEstServeTime(menuITemDTO.getMenuItemEstServeTime());




            this.menuItemRepo.save(menuItem);
            System.out.println("\n**Table-object \n(" + menuItem + ") \nupdated successfully");
            return "redirect:/menuItems";
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "redirect:/menuItems";
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

        System.out.println("\n**Table with id " + id + " deleted successfully");
        return "redirect:/restaurant-menuItems";
    }
    /// ///////////////////////////////////////////////////////////////////////////

    //'getAll' method
    @RequestMapping("")
    public String menuItems(Model model) {
        List<MenuItem> menuItems = menuItemRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("menuItems", menuItems);
        return "menuItem/menuItems";
    }
    /// ///////////////////////////////////////////////////////////////////////////


}
