package com.restaurant.rms.controllers;

import com.restaurant.rms.models.Employee;
import com.restaurant.rms.models.Employee1DTO;
//import com.restaurant.rms.repository.Employee1Repo;
import com.restaurant.rms.repository.EmployeeRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

// EMPLOYEE

/**
 * Used for operations related to the Employee entity
 * */
@Controller
//@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepo empRepo;

    // add constructor?
    // --------------------------------------------

    // CREATE
    @RequestMapping("/employees-add")
//    @RequestMapping("/post/")
//    @GetMapping("/post/")
    public String employeeAdd(Model model) {
//    public String createReqGet(Model model){
        Employee1DTO empDTO = new Employee1DTO();
        model.addAttribute("employee1DTO", empDTO);
        return "employee/employees-add";
    }

    @PostMapping("/employees-add")
//    @RequestMapping("/post/")
    public String createEmployee(
//    public String createReqPost(
            @Valid @ModelAttribute Employee1DTO eDTO,
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
                eDTO.getRole()
        );

        // saving to database
        this.empRepo.save(emp);
        System.out.println("\n***Employee-object \n(" + emp + ") \nadded successfully***");

        // re-directing
        return "redirect:/employees";
    }
    // --------------------------------------------

    // READ
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
//                Employee1DTO empDTO = new Employee1DTO();
            Employee employee = empRepo.findById(id).get();
//                model.addAttribute("employee1DTO", empDTO);
//                return "employee/employees-add";

            Employee1DTO empDTO = new Employee1DTO();
            empDTO.setEmpFirstName(employee.getEmpFirstName());
            empDTO.setEmpLastName(employee.getEmpLastName());
            empDTO.setRole(employee.getRole());

//                Product product = pRepo.findById(id).get();
//                model.addAttribute("product", product);
//
//                ProductDTO productDTO = new ProductDTO();
//                productDTO.setName(product.getName());
//                productDTO.setBrand(product.getBrand());
//                productDTO.setCategory(product.getCategory());
//                productDTO.setPrice(product.getPrice());
//                productDTO.setDescription(product.getDescription());

            model.addAttribute("employee", employee);

            model.addAttribute("employee1DTO", empDTO);
//                model.addAttribute("productDTO", productDTO);
        } catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
            return "redirect:/employee";

//                return "redirect:/products";
        }
//        return "editEmp";
        return "employee/editEmp";
//            return "employee/employees-add";

//            return "products/editProduct";
    }

    // Updating an existing record

        @PostMapping("/edit")
//    @PutMapping("/edit")
//        @PutMapping("/put")
    public String updateProduct(
//        public String updateReqPut(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute Employee1DTO employee1DTO,
//                @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult result
    ) {
        System.out.println("\n***Putting...");

        try {
//                Employee1DTO empDTO = new Employee1DTO();

            Employee employee = empRepo.findById(id).get();
            model.addAttribute("employee1DTO", employee);

//                Employee1DTO empDTO =  new Employee1DTO();
//                empDTO.setEmpFirstName(employee.getEmpFirstName());
//                empDTO.setEmpLastName(employee.getEmpLastName());
//                empDTO.setRole(employee.getRole());

//                Product product = pRepo.findById(id).get();
//                model.addAttribute("product", product);

            if (result.hasErrors()) {
                return "employee/editEmp";
//                    return "products/editProduct";
            }

//                if (!productDTO.getImageFile().isEmpty()){
//
//                    // delete old image
////            String uploadDir = "public/images/";
//                    String uploadDir = "src/main/resources/static/images/";
//                    Path oldPath = Paths.get(uploadDir + product.getImageFileName());
//
//                    try{
//                        Files.delete(oldPath);
//                    }
//                    catch(Exception ex){
//                        System.out.println(ex.getMessage());
//                    }
//
//                    // save new image file
//                    MultipartFile image = productDTO.getImageFile();
//                    Date createdAt = new Date();
//                    String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
//
//
//                    try (InputStream inputStream = image.getInputStream()){
//                        Files.copy(
//                                inputStream,
//                                Paths.get(uploadDir + storageFileName),
//                                StandardCopyOption.REPLACE_EXISTING
//                        );
//                    }
//
//                    // update image file name
//                    product.setImageFileName(storageFileName);
//
//                }

            // updating other details
            employee.setRole(employee1DTO.getRole());
            employee.setEmpFirstName(employee1DTO.getEmpFirstName());
            employee.setEmpLastName(employee1DTO.getEmpLastName());
//
//                product.setName(productDTO.getName());
//                product.setBrand(productDTO.getBrand());
//                product.setCategory(productDTO.getCategory());
//                product.setPrice(productDTO.getPrice());
//                product.setDescription(productDTO.getDescription());

            // save to database
//                pRepo.save(product);

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
//                Product product = pRepo.findById(id).get();
            Employee employee = empRepo.findById(id).get();

            // delete product image
//            String path = "public/images/";
//                String path = "src/main/resources/static/images/";
//                Path imagePath = Paths.get(path + product.getImageFileName());
//
//                try{
//                    Files.delete(imagePath);
//                }
//                catch(Exception ex){
//                    System.out.println(ex.getMessage());
//                }

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
    @RequestMapping("/employees")
//    @RequestMapping("/get/all")
//    public String getAll(){
    public String employees(Model model) {
//        return "employee/employees";

        List<Employee> employees = empRepo.findAll(Sort.by(Sort.Direction.DESC, "empId"));

        model.addAttribute("employees", employees);
        return "employee/employees";
    }

    //    @RequestMapping("/employees-list")
    @GetMapping("/employees-list")
    public String employeesList(Model model) {
//        List<Employee1> employees = empRepo.findAll();
        // or
        // reverse order
        List<Employee> employees = empRepo.findAll(Sort.by(Sort.Direction.DESC, "empId"));

        model.addAttribute("employees", employees);
        return "employee/employees-list";
    }
    // --------------------------------------------

    // **************************
    // **************************


    // Other
    @RequestMapping("/employees-pay")
    public String employeesPay() {
        return "employee/employees-pay";
    }


//    @Controller
//    @RequestMapping("/products")
//    public class ProductController {
//        @Autowired
//        private ProductRepo pRepo;
//
//        @GetMapping({"", "/"})
//        public String showProductList(Model model){
////         List<Product> products = pRepo.findAll();
//
//            // reverse order
//            List<Product> products = pRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
//
//
//            model.addAttribute("products", products);
//            return "products/index";
//        }
//
//        // used to create a new product
//        @GetMapping("/create")
//        public String showCreatePage(Model model){
//            ProductDTO productDTO = new ProductDTO();
//
//            model.addAttribute("productDTO", productDTO);
//            return "products/createProduct";
//        }
//
//        @PostMapping("/create")
//        public String createProduct(
//                @Valid @ModelAttribute ProductDTO productDTO,
//                BindingResult result
//        ){
//
//            if (productDTO.getImageFile().isEmpty()){
//                result.addError(new FieldError("productDTO", "imageFile", "The image file is"));
//            }
//
//            if (result.hasErrors()){
//                return "products/CreateProduct";
//            }
//
//            // save image file
//            MultipartFile image = productDTO.getImageFile();
//            Date createdAt = new Date();
//            String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
//
//            try{
////            String uploadDir = "public/images/";
//                String uploadDir = "src/main/resources/static/images/";
//                Path uploadPath = Paths.get(uploadDir);
////            C:\Users\jcbrm\Documents\code_more\springboot\lesson2_website\maven\demo\src\main\resources\static\images
//
//                if (!Files.exists(uploadPath)){
//                    Files.createDirectories(uploadPath);
//                }
//
//                try (InputStream inputStream = image.getInputStream()){
//                    Files.copy(inputStream, Paths.get(
//                                    uploadDir + storageFileName),
//                            StandardCopyOption.REPLACE_EXISTING
//                    );
//                }
//            }
//
//            catch(Exception ex){
//                System.out.println("Exception" + ex.getMessage());
//            }
//
//            // creating a product object to save to the database
////        Product product = new Product(
////                productDTO.getName(),
////                productDTO.getBrand(),
////                productDTO.getCategory(),
////                productDTO.getPrice(),
////                productDTO.getDescription(),
////                createdAt,
////                storageFileName
////        );
//
//            Product product1 = new Product();
//            product1.setName(productDTO.getName());
//            product1.setBrand(productDTO.getBrand());
//            product1.setCategory(productDTO.getCategory());
////        product1.setPrice(productDTO.getPrice());
////        product1.setDescription(productDTO.getDescription());
//            product1.setCreatedAt(createdAt);
//            product1.setImageFileName(storageFileName);
//
//            // saving to database
//            pRepo.save(product1);
//
//            return "redirect:/products";
//        }
//
//        // update details
//        @GetMapping("/edit")
//        public String showEditPage(
//                Model model,
//                @RequestParam int id
//        ){
//            try{
//                Product product = pRepo.findById(id).get();
//                model.addAttribute("product", product);
//
//                ProductDTO productDTO = new ProductDTO();
//                productDTO.setName(product.getName());
//                productDTO.setBrand(product.getBrand());
//                productDTO.setCategory(product.getCategory());
//                productDTO.setPrice(product.getPrice());
//                productDTO.setDescription(product.getDescription());
//
//                model.addAttribute("productDTO", productDTO);
//            }
//            catch(Exception ex) {
//                System.out.println("Exception" + ex.getMessage());
//                return "redirect:/products";
//            }
//
//            return "products/editProduct";
//        }
//
//        @PostMapping("/edit")
//        public String updateProduct(
//                Model model,
//                @RequestParam int id,
//                @Valid @ModelAttribute ProductDTO productDTO,
//                BindingResult result
//        ){
//            try{
//                Product product = pRepo.findById(id).get();
//                model.addAttribute("product", product);
//
//                if (result.hasErrors()){
//                    return "products/editProduct";
//                }
//
//                if (!productDTO.getImageFile().isEmpty()){
//
//                    // delete old image
////            String uploadDir = "public/images/";
//                    String uploadDir = "src/main/resources/static/images/";
//                    Path oldPath = Paths.get(uploadDir + product.getImageFileName());
//
//                    try{
//                        Files.delete(oldPath);
//                    }
//                    catch(Exception ex){
//                        System.out.println(ex.getMessage());
//                    }
//
//                    // save new image file
//                    MultipartFile image = productDTO.getImageFile();
//                    Date createdAt = new Date();
//                    String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
//
//
//                    try (InputStream inputStream = image.getInputStream()){
//                        Files.copy(
//                                inputStream,
//                                Paths.get(uploadDir + storageFileName),
//                                StandardCopyOption.REPLACE_EXISTING
//                        );
//                    }
//
//                    // update image file name
//                    product.setImageFileName(storageFileName);
//
//                }
//
//                // updating other details
//                product.setName(productDTO.getName());
//                product.setBrand(productDTO.getBrand());
//                product.setCategory(productDTO.getCategory());
//                product.setPrice(productDTO.getPrice());
//                product.setDescription(productDTO.getDescription());
//
//                // save to database
//                pRepo.save(product);
//            }
//            catch(Exception ex){
//                System.out.println(ex.getMessage());
//            }
//
//            return "redirect:/products";
//        }
//
//        // deleting
//        @GetMapping("/delete")
//        public String deleteProduct(@RequestParam int id){
//            try{
//                Product product = pRepo.findById(id).get();
//
//                // delete product image
////            String path = "public/images/";
//                String path = "src/main/resources/static/images/";
//                Path imagePath = Paths.get(path + product.getImageFileName());
//
//                try{
//                    Files.delete(imagePath);
//                }
//                catch(Exception ex){
//                    System.out.println(ex.getMessage());
//                }
//
//                // delete from database
//                pRepo.delete(product);
//            }
//            catch(Exception ex){
//                System.out.println(ex.getMessage());
//            }
//
//            return "redirect:/products";
//        }
//    }

}
