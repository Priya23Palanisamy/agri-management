package com.agri.agri_management.report.controller;

import com.agri.agri_management.report.dto.ReportRequest;
import com.agri.agri_management.report.dto.ReportResponse;
import com.agri.agri_management.report.service.ExportService;
import com.agri.agri_management.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ExportService exportService;

    @PostMapping("/generate")
    public ResponseEntity<ReportResponse> generate(@RequestBody ReportRequest request) {
        return ResponseEntity.ok(reportService.generateReport(request));
    }

    @PostMapping("/export/csv")
    public ResponseEntity<byte[]> csv(@RequestBody ReportRequest request) {
        byte[] data = exportService.exportToCsv(
                reportService.generateReport(request)
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(data);
    }

    @PostMapping("/export/pdf")
    public ResponseEntity<byte[]> pdf(@RequestBody ReportRequest request) {
        byte[] data = exportService.exportToPdf(
                reportService.generateReport(request)
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleError(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}