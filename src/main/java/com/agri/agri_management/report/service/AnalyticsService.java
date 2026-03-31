package com.agri.agri_management.report.service;
import com.agri.agri_management.product.entity.Product;
import com.agri.agri_management.report.dto.ReportResponse;
import com.agri.agri_management.transaction.entity.Transaction;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class AnalyticsService {
    public void analyze(ReportResponse response, List<Product> products, List<Transaction> transactions) {
        response.setTotalStock(products.stream().mapToInt(Product::getQuantity).sum());
        response.setTotalStockValue(Math.round(products.stream().mapToDouble(p -> p.getQuantity() * p.getPrice()).sum() * 100.0) / 100.0);
        response.setTotalTransactions(transactions.size());
        response.setTotalRevenue(Math.round(transactions.stream().mapToDouble(t -> t.getQuantity() * t.getPrice()).sum() * 100.0) / 100.0);
        Map<String, Integer> sales = new HashMap<>();
        for (Transaction t : transactions)
            if (t.getProductName() != null) sales.merge(t.getProductName(), t.getQuantity(), Integer::sum);
        response.setFastMovingItems(sales.entrySet().stream().filter(e -> e.getValue() > 50).map(e -> e.getKey() + " (" + e.getValue() + " units)").collect(Collectors.toList()));
        response.setSlowMovingItems(sales.entrySet().stream().filter(e -> e.getValue() <= 10).map(e -> e.getKey() + " (" + e.getValue() + " units)").collect(Collectors.toList()));
        response.setStockIssues(products.stream().filter(p -> p.getQuantity() < p.getMinStockLevel()).map(p -> p.getName() + " (Stock: " + p.getQuantity() + ", Min: " + p.getMinStockLevel() + ")").collect(Collectors.toList()));
    }
}