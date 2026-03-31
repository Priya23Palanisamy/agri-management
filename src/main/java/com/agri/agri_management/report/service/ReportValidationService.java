package com.agri.agri_management.report.service;
import com.agri.agri_management.report.dto.ReportRequest;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
@Service
public class ReportValidationService {
    private static final List<String> VALID_TYPES = Arrays.asList("STOCK","ANALYTICS");
    public void validate(ReportRequest request) {
        if (request.getReportType() == null || request.getReportType().isEmpty())
            throw new IllegalArgumentException("Report type is required.");
        if (!VALID_TYPES.contains(request.getReportType().toUpperCase()))
            throw new IllegalArgumentException("Invalid report type. Valid: STOCK, TRANSACTION, ANALYTICS");
        if (request.getStartDate() != null && request.getEndDate() != null)
            if (request.getStartDate().isAfter(request.getEndDate()))
                throw new IllegalArgumentException("Start date cannot be after end date.");
    }
}