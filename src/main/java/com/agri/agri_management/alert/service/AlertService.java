package com.agri.agri_management.alert.service;

import com.agri.agri_management.alert.entity.*;
import com.agri.agri_management.alert.repository.AlertRepository;
import com.agri.agri_management.product.entity.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final AlertValidationService alertValidationService;
    private final NotificationService notificationService;

    public AlertService(AlertRepository alertRepository,
                        AlertValidationService alertValidationService,
                        NotificationService notificationService) {
        this.alertRepository = alertRepository;
        this.alertValidationService = alertValidationService;
        this.notificationService = notificationService;
    }

    // Main Method (called from ProductService)
    public void evaluateStockAlert(Product product) {

        String validation = alertValidationService.validateProductForAlert(product);

        if (!"VALID".equals(validation)) {
            return;
        }

        // OUT OF STOCK
        if (product.getQuantity() <= 0) {
            createAlert(product, AlertType.OUT_OF_STOCK, Severity.HIGH);
        }

        // LOW STOCK
        else if (product.getQuantity() < product.getMinStockLevel()) {
            createAlert(product, AlertType.LOW_STOCK, Severity.MEDIUM);
        }
    }

    //CREATE ALERT METHOD
    private void createAlert(Product product, AlertType type, Severity severity) {

        boolean exists = alertRepository
                .existsByProductIdAndAlertTypeAndStatus(
                        product.getProductId(),
                        type,
                        AlertStatus.ACTIVE
                );

        //Prevent duplicate alerts
        if (exists) {
            return;
        }

        // Create new alert

        Alert alert = new Alert();
        alert.setProductId(product.getProductId());
        alert.setAlertType(type);
        alert.setSeverity(severity);
        alert.setMessage(generateMessage(product, type));
        alert.setCreatedDate(LocalDateTime.now());
        alert.setStatus(AlertStatus.ACTIVE);

        alertRepository.save(alert);

        // Send notification
        notificationService.sendAlert(alert);
    }

    // MESSAGE GENERATOR
    private String generateMessage(Product product, AlertType type) {

        if (type == AlertType.OUT_OF_STOCK) {
            return "Product '" + product.getName() + "' is OUT OF STOCK!";
        }

        if (type == AlertType.LOW_STOCK) {
            return "Product '" + product.getName() + "' is LOW on stock!";
        }

        return "Alert generated for product: " + product.getName();
    }
}