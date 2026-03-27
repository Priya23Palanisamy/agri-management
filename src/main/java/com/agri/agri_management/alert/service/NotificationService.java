package com.agri.agri_management.alert.service;

import com.agri.agri_management.alert.entity.Alert;
import com.agri.agri_management.alert.entity.Severity;
import com.agri.agri_management.auth.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    public void sendAlert(Alert alert) {

        System.out.println(" ALERT: " + alert.getMessage());

        // 🔥 Only HIGH → Email
        if (alert.getSeverity() == Severity.HIGH) {
            sendHighSeverityEmail(alert);
        }

        // 🟡 MEDIUM → Console only
        else if (alert.getSeverity() == Severity.MEDIUM) {
            System.out.println("⚠ MEDIUM ALERT: " + alert.getMessage());
        }
    }

    private void sendHighSeverityEmail(Alert alert) {

        String to = "receiver_email@gmail.com"; // 🔥 change later dynamically

        String subject = "🚨 HIGH ALERT - Agri System";

        String body =
                "Alert Type: " + alert.getAlertType() + "\n" +
                "Severity: " + alert.getSeverity() + "\n" +
                "Message: " + alert.getMessage();

        emailService.sendEmail(to, subject, body);

        System.out.println("📧 Email sent!");
    }
}