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
import com.agri.agri_management.transaction.service.TransactionService;
import com.agri.agri_management.transaction.entity.TransactionType;

import java.util.List;

@Service
public class ProductService {

    @Autowired private ProductRepository productRepo;
    @Autowired private WarehouseRepository warehouseRepo;
    @Autowired private AlertService alertService;
    @Autowired private UserRepository userRepo;
    @Autowired private TransactionService transactionService;

    public String addProduct(Product product, Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        product.setUser(user);

        if (product.getName() == null || product.getName().trim().isEmpty())
            return "Product name is required";

        if (product.getQuantity() <= 0)
            return "Quantity must be greater than 0";

        String prefix = product.getName().substring(0, Math.min(3, product.getName().length())).toUpperCase();
        product.setSkuCode(prefix + "-" + System.currentTimeMillis());

        if (product.getMinStockLevel() <= 0) product.setMinStockLevel(10);

        List<Warehouse> warehouses = warehouseRepo.findAll();
        Warehouse selected = null;
        for (Warehouse w : warehouses) {
            if ((w.getCapacity() - w.getCurrentStock()) >= product.getQuantity()) {
                selected = w; break;
            }
        }

        if (selected == null) return "No warehouse space available";

        product.setWarehouseId(selected.getWarehouseId());
        product.setStatus("AVAILABLE");
        productRepo.save(product);
        alertService.evaluateStockAlert(product);

        selected.setCurrentStock(selected.getCurrentStock() + product.getQuantity());
        warehouseRepo.save(selected);

        // RECORD TRANSACTION
        transactionService.record(product, user, TransactionType.PRODUCT_ADDED,
                product.getQuantity(), "New product added");

        return "Product added successfully";
    }

    public List<Product> getAllProducts() { return productRepo.findAll(); }

    public List<Product> getProductsByUser(Long userId) { return productRepo.findByUserUserId(userId); }

    public String deleteProduct(Long id) { productRepo.deleteById(id); return "Deleted"; }

    public List<Product> getProductsByCategory(String category) { return productRepo.findByCategory(category); }

    public String stockIn(Long productId, int quantity, Long userId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Warehouse warehouse = warehouseRepo.findById(product.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        if (quantity > (warehouse.getCapacity() - warehouse.getCurrentStock()))
            return "Not enough space in warehouse";

        product.setQuantity(product.getQuantity() + quantity);
        product.setStatus("AVAILABLE");
        productRepo.save(product);
        alertService.evaluateStockAlert(product);

        warehouse.setCurrentStock(warehouse.getCurrentStock() + quantity);
        warehouseRepo.save(warehouse);

        // RECORD TRANSACTION
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        transactionService.record(product, user, TransactionType.STOCK_IN, quantity, "Stock added");

        return "Stock added successfully";
    }

    public String stockOut(Long productId, int quantity, Long userId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Warehouse warehouse = warehouseRepo.findById(product.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        if (quantity > product.getQuantity()) return "Not enough stock available";

        int newQty = product.getQuantity() - quantity;
        product.setQuantity(newQty);
        if (newQty == 0) product.setStatus("OUT_OF_STOCK");

        productRepo.save(product);
        alertService.evaluateStockAlert(product);

        warehouse.setCurrentStock(warehouse.getCurrentStock() - quantity);
        warehouseRepo.save(warehouse);

        // RECORD TRANSACTION
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        TransactionType type = (user.getRole() != null && user.getRole().name().equals("BUYER"))
                ? TransactionType.PURCHASE : TransactionType.STOCK_OUT;
        transactionService.record(product, user, type, quantity,
                type == TransactionType.PURCHASE ? "Buyer purchase" : "Stock removed");

        return "Stock removed successfully";
    }
}