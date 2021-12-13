package com.musala.product.controllers;

import com.musala.product.model.Product;
import com.musala.product.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    @Autowired
    ProductRepo productRepo;

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

    @GetMapping("/showCreateProduct")
    public String showCreateProduct() {
        return "createProduct";
    }

    @PostMapping("/createProduct")
    public String saveProduct(Product product) {
        productRepo.save(product);
        return "createResponse";
    }

    @GetMapping("/showGetProduct")
    public String showGetProduct() {
        return "getProduct";
    }

    @PostMapping("/getProduct")
    public ModelAndView getProduct(Long id) {
        ModelAndView productDetails = new ModelAndView("productDetails");
        productDetails.addObject(productRepo.findById(id).get());
        return productDetails;
    }
}
