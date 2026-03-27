package com.agri.agri_management.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agri.agri_management.product.entity.Product;
import com.agri.agri_management.auth.entity.Warehouse;
import com.agri.agri_management.product.repository.ProductRepository;
import com.agri.agri_management.auth.repository.WarehouseRepository;
import com.agri.agri_management.alert.service.AlertService;
import com.agri.agri_management.auth.repository.*;
import com.agri.agri_management.auth.entity.*;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private WarehouseRepository warehouseRepo;

    @Autowired
    private AlertService alertService;
    @Autowired
    private UserRepository userRepo;

    // ADD PRODUCT
    public String addProduct(Product product, Long userId) {
    	
    	 // 🔥 SET USER (IMPORTANT)
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        product.setUser(user);

        // ✅ FIXED VALIDATION
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            return "Product name is required";
        }

        if (product.getQuantity() <= 0) {
            return "Quantity must be greater than 0";
        }

        // SKU
        String prefix = product.getName().substring(0, Math.min(3, product.getName().length())).toUpperCase();
        product.setSkuCode(prefix + "-" + System.currentTimeMillis());

        if (product.getMinStockLevel() <= 0) {
            product.setMinStockLevel(10);
        }

        // Warehouse allocation
        List<Warehouse> warehouses = warehouseRepo.findAll();
        Warehouse selected = null;

        for (Warehouse w : warehouses) {
            if ((w.getCapacity() - w.getCurrentStock()) >= product.getQuantity()) {
                selected = w;
                break;
            }
        }

        if (selected == null) {
            return "No warehouse space available";
        }

        product.setWarehouseId(selected.getWarehouseId());
        product.setStatus("AVAILABLE");

        productRepo.save(product);

        alertService.evaluateStockAlert(product);

        selected.setCurrentStock(selected.getCurrentStock() + product.getQuantity());
        warehouseRepo.save(selected);

        return "Product added successfully";
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    
    
    public List<Product> getProductsByUser(Long userId) {
        return productRepo.findByUserUserId(userId);
    }

    public String deleteProduct(Long id) {
        productRepo.deleteById(id);
        return "Deleted";
    }
    
 // GET BY CATEGORY
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategory(category);
    }

    // STOCK IN
    public String stockIn(Long productId, int quantity) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Warehouse warehouse = warehouseRepo.findById(product.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        int freeSpace = warehouse.getCapacity() - warehouse.getCurrentStock();

        if (quantity > freeSpace) {
            return "Not enough space in warehouse";
        }

        product.setQuantity(product.getQuantity() + quantity);
        product.setStatus("AVAILABLE");

        productRepo.save(product);

        // 🔔 ALERT CHECK
        alertService.evaluateStockAlert(product);

        warehouse.setCurrentStock(warehouse.getCurrentStock() + quantity);
        warehouseRepo.save(warehouse);

        return "Stock added successfully";
    }

    // STOCK OUT
    public String stockOut(Long productId, int quantity) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Warehouse warehouse = warehouseRepo.findById(product.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        if (quantity > product.getQuantity()) {
            return "Not enough stock available";
        }

        int newQty = product.getQuantity() - quantity;
        product.setQuantity(newQty);

        if (newQty == 0) {
            product.setStatus("OUT_OF_STOCK");
        }

        productRepo.save(product);

        // 🔔 ALERT CHECK
        alertService.evaluateStockAlert(product);

        warehouse.setCurrentStock(warehouse.getCurrentStock() - quantity);
        warehouseRepo.save(warehouse);

        return "Stock removed successfully";
    }
}