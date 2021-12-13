package com.musala.product.controllers;

import com.musala.product.dtos.Coupon;
import com.musala.product.model.Product;
import com.musala.product.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/productapi")
public class ProductRestController {

    @Autowired
    ProductRepo repo;

    @Autowired
    RestTemplate restTemplate;

    @Value("${couponService.url}")
    private String couponServiceURL;

    @PostMapping("/products")
    public Product create(@RequestBody Product product){
      // Coupon coupon =  restTemplate.getForObject(couponServiceURL+product.getCouponCode(),Coupon.class);
       // product.setPrice(product.getPrice().subtract(coupon.getDiscount()));

        return repo.save(product);
    }
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return repo.findById(id).get();
    }
}
