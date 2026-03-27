package com.agri.agri_management.alert.service;

import com.agri.agri_management.product.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class AlertValidationService {

    public String validateProductForAlert(Product product) {

        if (product == null) {
            return "Invalid product";
        }

        if (!"AVAILABLE".equalsIgnoreCase(product.getStatus()) &&
        	    !"OUT_OF_STOCK".equalsIgnoreCase(product.getStatus())) {
        	    return "Inactive product";
        	}

        if (product.getMinStockLevel() < 0) {
            return "Invalid stock threshold";
        }

        return "VALID";
    }
}