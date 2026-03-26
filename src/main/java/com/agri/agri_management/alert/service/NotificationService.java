package com.agri.agri_management.alert.service;


import com.agri.agri_management.alert.entity.Alert;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendAlert(Alert alert) {

        // For now just print (can upgrade to Email/SMS later)
        System.out.println(" ALERT NOTIFICATION:");
        System.out.println("Type: " + alert.getAlertType());
        System.out.println("Message: " + alert.getMessage());
        System.out.println("Severity: " + alert.getSeverity());
    }
}
