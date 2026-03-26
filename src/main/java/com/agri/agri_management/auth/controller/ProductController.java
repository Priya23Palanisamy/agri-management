package com.agri.agri_management.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.agri.agri_management.product.entity.Product;
import com.agri.agri_management.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping("/category")
public List<Product> getByCategory(@RequestParam String category) {
    return productService.getProductsByCategory(category);
}

@DeleteMapping("/delete/{id}")
public String deleteProduct(@PathVariable Long id) {
    return productService.deleteProduct(id);
}
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @PostMapping("/stock-in")
public String stockIn(@RequestParam Long productId,
                      @RequestParam int quantity) {
    return productService.stockIn(productId, quantity);
}

@PostMapping("/stock-out")
public String stockOut(@RequestParam Long productId,
                       @RequestParam int quantity) {
    return productService.stockOut(productId, quantity);
}
}