package com.agri.agri_management.alert.service;

import com.agri.agri_management.alert.dto.AlertFilterRequest;
import com.agri.agri_management.alert.entity.Alert;
import com.agri.agri_management.alert.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertFilteringService {

    private final AlertRepository alertRepository;

    public AlertFilteringService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> getFilteredAlerts(AlertFilterRequest filter) {

        List<Alert> alerts = alertRepository.findAll();

        if (filter.getAlertType() != null) {
            alerts = alerts.stream()
                    .filter(a -> a.getAlertType() == filter.getAlertType())
                    .collect(Collectors.toList());
        }

        if (filter.getSeverity() != null) {
            alerts = alerts.stream()
                    .filter(a -> a.getSeverity() == filter.getSeverity())
                    .collect(Collectors.toList());
        }

        if (filter.getProductId() != null) {
            alerts = alerts.stream()
                    .filter(a -> a.getProductId().equals(filter.getProductId()))
                    .collect(Collectors.toList());
        }

        if (filter.getStatus() != null) {
            alerts = alerts.stream()
                    .filter(a -> a.getStatus() == filter.getStatus())
                    .collect(Collectors.toList());
        }

        return alerts;
    }
}
