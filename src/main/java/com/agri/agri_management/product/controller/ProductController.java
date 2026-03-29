package com.agri.agri_management.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.agri.agri_management.product.entity.Product;
import com.agri.agri_management.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String addProduct(@RequestBody Product product, @RequestParam Long userId) {
        return productService.addProduct(product, userId);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() { return productService.getAllProducts(); }

    @GetMapping("/farmer")
    public List<Product> getFarmerProducts(@RequestParam Long userId) {
        return productService.getProductsByUser(userId);
    }

    @GetMapping("/category")
    public List<Product> getByCategory(@RequestParam String category) {
        return productService.getProductsByCategory(category);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) { return productService.deleteProduct(id); }

    @PostMapping("/stock-in")
    public String stockIn(@RequestParam Long productId,
                          @RequestParam int quantity,
                          @RequestParam(required = false, defaultValue = "0") Long userId) {
        return productService.stockIn(productId, quantity, userId);
    }

    @PostMapping("/stock-out")
    public String stockOut(@RequestParam Long productId,
                           @RequestParam int quantity,
                           @RequestParam(required = false, defaultValue = "0") Long userId) {
        return productService.stockOut(productId, quantity, userId);
    }
}