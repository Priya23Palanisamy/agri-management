package com.agri.agri_management.alert.controller;

import com.agri.agri_management.alert.dto.AlertFilterRequest;
import com.agri.agri_management.alert.entity.Alert;
import com.agri.agri_management.alert.service.AlertFilteringService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertFilteringService alertFilteringService;

    public AlertController(AlertFilteringService alertFilteringService) {
        this.alertFilteringService = alertFilteringService;
    }

    // GET ALERTS WITH FILTER
    @PostMapping("/filter")
    public List<Alert> getFilteredAlerts(@RequestBody AlertFilterRequest filterRequest) {
        return alertFilteringService.getFilteredAlerts(filterRequest);
    }

    //GET ALL ALERTS (simple)
    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertFilteringService.getFilteredAlerts(new AlertFilterRequest());
    }
}
