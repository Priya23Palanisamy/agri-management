package com.agri.agri_management.alert.dto;

import com.agri.agri_management.alert.entity.AlertStatus;
import com.agri.agri_management.alert.entity.AlertType;
import com.agri.agri_management.alert.entity.Severity;

public class AlertFilterRequest {

    private Long productId;
    private AlertType alertType;
    private Severity severity;
    private AlertStatus status;

    // Getters & Setters

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }
}