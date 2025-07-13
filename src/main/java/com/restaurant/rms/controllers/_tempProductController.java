package com.restaurant.rms.controllers;


import com.restaurant.rms.models.DTO._tempProductDTO;
import com.restaurant.rms.models._tempProduct;
import com.restaurant.rms.repository._tempProductRepo;
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

@Controller
@RequestMapping("/products")
//@RequestMapping("/_temp_products")
public class _tempProductController {
    @Autowired
    private _tempProductRepo pRepo;
    // -----------------------------

    // GET
    @GetMapping({"", "/"})
    public String showProductList(Model model){
//         List<Product> products = pRepo.findAll();

        // reverse order
        List<_tempProduct> products = pRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));


        model.addAttribute("products", products);
        return "_temp/index";
    }
    // -----------------------------

    // POST
    // used to create a new product
    @GetMapping("/create")
    public String showCreatePage(Model model){
        _tempProductDTO productDTO = new _tempProductDTO();

        model.addAttribute("productDTO", productDTO);
        return "_temp/createProduct";
    }

    @PostMapping("/create")
    public String createProduct(
        @Valid @ModelAttribute _tempProductDTO productDTO,
        BindingResult result
    ){

        if (productDTO.getImageFile().isEmpty()){
            result.addError(new FieldError("productDTO", "imageFile", "The image file is"));
        }

        if (result.hasErrors()){
            return "_temp/createProduct";
        }

        // save image file
        MultipartFile image = productDTO.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try{
//            String uploadDir = "public/images/";
//            String uploadDir = "src/main/resources/static/images/";
            String uploadDir = "public/images/";

            Path uploadPath = Paths.get(uploadDir);
//            C:\Users\jcbrm\Documents\code_more\springboot\lesson2_website\maven\demo\src\main\resources\static\images

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(
                        uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
        }

        catch(Exception ex){
            System.out.println("Exception" + ex.getMessage());
        }

        // creating a product object to save to the database
//        Product product = new Product(
//                productDTO.getName(),
//                productDTO.getBrand(),
//                productDTO.getCategory(),
//                productDTO.getPrice(),
//                productDTO.getDescription(),
//                createdAt,
//                storageFileName
//        );

        _tempProduct product1 = new _tempProduct();
        product1.setName(productDTO.getName());
        product1.setBrand(productDTO.getBrand());
        product1.setCategory(productDTO.getCategory());
//        product1.setPrice(productDTO.getPrice());
//        product1.setDescription(productDTO.getDescription());
        product1.setCreatedAt(createdAt);
        product1.setImageFileName(storageFileName);

        // saving to database
        pRepo.save(product1);

        return "redirect:/products";
    }
    // -----------------------------

    // UPDATE
    // update details
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ){
        try{
            _tempProduct product = pRepo.findById(id).get();
            model.addAttribute("product", product);

            _tempProductDTO productDTO = new _tempProductDTO();
            productDTO.setName(product.getName());
            productDTO.setBrand(product.getBrand());
            productDTO.setCategory(product.getCategory());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription());

            model.addAttribute("productDTO", productDTO);
        }
        catch(Exception ex) {
            System.out.println("Exception" + ex.getMessage());
            return "redirect:/products";
        }

        return "_temp/editProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute _tempProductDTO productDTO,
            BindingResult result
    ){
        try{
            _tempProduct product = pRepo.findById(id).get();
            model.addAttribute("product", product);

            if (result.hasErrors()){
                return "_temp/editProduct";
            }

            if (!productDTO.getImageFile().isEmpty()){

                // delete old image
//            String uploadDir = "public/images/";
                String uploadDir = "src/main/resources/static/images/";
                Path oldPath = Paths.get(uploadDir + product.getImageFileName());

                try{
                    Files.delete(oldPath);
                }
                catch(Exception ex){
                    System.out.println(ex.getMessage());
                }

                // save new image file
                MultipartFile image = productDTO.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();


                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(
                            inputStream,
                            Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                }

                // update image file name
                product.setImageFileName(storageFileName);

            }

            // updating other details
            product.setName(productDTO.getName());
            product.setBrand(productDTO.getBrand());
            product.setCategory(productDTO.getCategory());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());

            // save to database
            pRepo.save(product);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return "redirect:/products";
    }
    // -----------------------------

    // DELETE
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id){
        try{
            _tempProduct product = pRepo.findById(id).get();

            // delete product image
//            String path = "public/images/";
            String path = "src/main/resources/static/images/";
            Path imagePath = Paths.get(path + product.getImageFileName());

            try{
                Files.delete(imagePath);
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }

            // delete from database
            pRepo.delete(product);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return "redirect:/products";
    }
}
