package com.agri.agri_management.report.service;
import com.agri.agri_management.product.entity.Product;
import com.agri.agri_management.report.dto.ReportRequest;
import com.agri.agri_management.transaction.entity.Transaction;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReportFilteringService {
    public List<Product> filterProducts(List<Product> products, ReportRequest request) {
        return products.stream().filter(p -> {
            if (request.getCategory() != null && !request.getCategory().isEmpty())
                return p.getCategory() != null && p.getCategory().equalsIgnoreCase(request.getCategory());
            return true;
        }).collect(Collectors.toList());
    }
    public List<Transaction> filterTransactions(List<Transaction> transactions, ReportRequest request) {
        return transactions.stream().filter(t -> {
            if (request.getTransactionType() != null && !request.getTransactionType().isEmpty())
                if (t.getTransactionType() == null || !t.getTransactionType().name().equalsIgnoreCase(request.getTransactionType()))
                    return false;
            if (request.getStartDate() != null && t.getTransactionDate() != null)
                if (t.getTransactionDate().toLocalDate().isBefore(request.getStartDate())) return false;
            if (request.getEndDate() != null && t.getTransactionDate() != null)
                if (t.getTransactionDate().toLocalDate().isAfter(request.getEndDate())) return false;
            return true;
        }).collect(Collectors.toList());
    }
}