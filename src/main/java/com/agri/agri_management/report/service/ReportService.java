package com.agri.agri_management.report.service;
import com.agri.agri_management.product.repository.ProductRepository;
import com.agri.agri_management.report.dto.ReportRequest;
import com.agri.agri_management.report.dto.ReportResponse;
import com.agri.agri_management.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class ReportService {
    @Autowired private ProductRepository productRepository;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private ReportValidationService validationService;
    @Autowired private ReportFilteringService filteringService;
    @Autowired private AnalyticsService analyticsService;
    @Autowired private ChartService chartService;
    @Autowired private RecommendationService recommendationService;
    public ReportResponse generateReport(ReportRequest request) {
        validationService.validate(request);
        var products = filteringService.filterProducts(productRepository.findAll(), request);
        var transactions = filteringService.filterTransactions(transactionRepository.findAll(), request);
        ReportResponse response = new ReportResponse();
        response.setReportType(request.getReportType().toUpperCase());
        response.setGeneratedAt(LocalDateTime.now().toString());
        analyticsService.analyze(response, products, transactions);
        chartService.generateCharts(response, products, transactions);
        recommendationService.generateRecommendations(response, products);
        return response;
    }
}