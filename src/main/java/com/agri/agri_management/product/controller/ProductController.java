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

    // ADD PRODUCT
    @PostMapping("/add")
    public String addProduct(@RequestBody Product product,@RequestParam Long userId) {
        return productService.addProduct(product,userId);
    }

    // GET ALL
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/farmer")
    public List<Product> getFarmerProducts(@RequestParam Long userId) {
        return productService.getProductsByUser(userId);
    }

    // GET BY CATEGORY
    @GetMapping("/category")
    public List<Product> getByCategory(@RequestParam String category) {
        return productService.getProductsByCategory(category);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    // STOCK IN
    @PostMapping("/stock-in")
    public String stockIn(@RequestParam Long productId,
                          @RequestParam int quantity) {
        return productService.stockIn(productId, quantity);
    }

    // STOCK OUT
    @PostMapping("/stock-out")
    public String stockOut(@RequestParam Long productId,
                           @RequestParam int quantity) {
        return productService.stockOut(productId, quantity);
    }
}