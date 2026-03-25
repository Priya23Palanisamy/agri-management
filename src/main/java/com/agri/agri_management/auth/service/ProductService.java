package com.agri.agri_management.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agri.agri_management.auth.entity.Product;
import com.agri.agri_management.auth.entity.Warehouse;
import com.agri.agri_management.auth.entity.User;
import com.agri.agri_management.auth.repository.ProductRepository;
import com.agri.agri_management.auth.repository.UserRepository;
import com.agri.agri_management.auth.repository.WarehouseRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private WarehouseRepository warehouseRepo;

    @Autowired
    private UserRepository userRepo;

    // ✅ ADD PRODUCT (FINAL VERSION)
    public String addProduct(Product product) {

        // ✅ Step 1: Validate inputs
        if (product.getName() == null || product.getName().isEmpty()) {
            return "Product name is required";
        }

        if (product.getQuantity() <= 0) {
            return "Quantity must be greater than 0";
        }

        // 🔥 Step 2: Generate SKU safely
        String name = product.getName();

        String prefix = name.length() >= 3
                ? name.substring(0, 3).toUpperCase()
                : name.toUpperCase();

        String sku = prefix + "-" + System.currentTimeMillis();
        product.setSkuCode(sku);


        // 🔥 Step 4: Find warehouse with space
        List<Warehouse> warehouses = warehouseRepo.findAll();

        Warehouse selected = null;

        for (Warehouse w : warehouses) {
            int freeSpace = w.getCapacity() - w.getCurrentStock();

            if (freeSpace >= product.getQuantity()) {
                selected = w;
                break;
            }
        }

        if (selected == null) {
            return "No warehouse space available";
        }

        // ✅ Step 5: Assign warehouse
        product.setWarehouseId(selected.getWarehouseId());
        product.setStatus("AVAILABLE");

        // ✅ Step 6: Save product
        productRepo.save(product);

        // ✅ Step 7: Update warehouse stock
        selected.setCurrentStock(
                selected.getCurrentStock() + product.getQuantity()
        );

        warehouseRepo.save(selected);

        return "Product added successfully";
    }

    // ✅ GET ALL PRODUCTS
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
    return productRepo.findByCategory(category);
}

public String deleteProduct(Long id) {
    productRepo.deleteById(id);
    return "Product deleted successfully";
}
    // ✅ GET PRODUCTS BY FARMER
    public List<Product> getProductsByFarmer(Long farmerId) {
        return productRepo.findByFarmerId(farmerId);
    }

    // ✅ STOCK IN
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

        warehouse.setCurrentStock(warehouse.getCurrentStock() + quantity);
        warehouseRepo.save(warehouse);

        return "Stock added successfully";
    }

    // ✅ STOCK OUT
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

        warehouse.setCurrentStock(warehouse.getCurrentStock() - quantity);
        warehouseRepo.save(warehouse);

        return "Stock removed successfully";
    }
}