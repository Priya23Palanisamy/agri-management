package com.agri.agri_management.report.service;
import com.agri.agri_management.product.entity.Product;
import com.agri.agri_management.report.dto.ReportResponse;
import com.agri.agri_management.transaction.entity.Transaction;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ChartService {
    public void generateCharts(ReportResponse response, List<Product> products, List<Transaction> transactions) {
        Map<String, Integer> bar = new LinkedHashMap<>();
        for (Product p : products) bar.merge(p.getCategory() != null ? p.getCategory() : "Unknown", p.getQuantity(), Integer::sum);
        response.setBarChartData(bar);
        Map<String, Double> line = new LinkedHashMap<>();
        for (Transaction t : transactions)
            if (t.getTransactionDate() != null) {
                String month = t.getTransactionDate().getMonth().name().substring(0,3) + " " + t.getTransactionDate().getYear();
                line.merge(month, t.getQuantity() * t.getPrice(), Double::sum);
            }
        response.setLineChartData(line);
        Map<String, Integer> pie = new LinkedHashMap<>();
        for (Transaction t : transactions)
            if (t.getTransactionType() != null) pie.merge(t.getTransactionType().name(), 1, Integer::sum);
        response.setPieChartData(pie);
    }
}