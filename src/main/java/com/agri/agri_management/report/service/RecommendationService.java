package com.agri.agri_management.report.service;
import com.agri.agri_management.product.entity.Product;
import com.agri.agri_management.report.dto.ReportResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class RecommendationService {
    public void generateRecommendations(ReportResponse response, List<Product> products) {
        List<String> recommendations = new ArrayList<>();
        for (Product p : products) {
            if (p.getQuantity() < p.getMinStockLevel())
                recommendations.add("Restock " + p.getName() + " - Current: " + p.getQuantity() + ", Min: " + p.getMinStockLevel());
            if (p.getQuantity() == 0)
                recommendations.add(p.getName() + " is OUT OF STOCK. Immediate restocking needed!");
            if (p.getExpiryDate() != null) {
                long days = LocalDate.now().until(p.getExpiryDate()).getDays();
                if (days >= 0 && days <= 30)
                    recommendations.add(p.getName() + " expires in " + days + " days. Sell fast!");
                if (days < 0)
                    recommendations.add(p.getName() + " has EXPIRED. Remove immediately!");
            }
        }
        if (response.getSlowMovingItems() != null && !response.getSlowMovingItems().isEmpty())
            recommendations.add("Reduce stock for slow moving: " + response.getSlowMovingItems());
        if (response.getFastMovingItems() != null && !response.getFastMovingItems().isEmpty())
            recommendations.add("Increase stock for high demand: " + response.getFastMovingItems());
        if (recommendations.isEmpty())
            recommendations.add("All stock levels are healthy. No action required.");
        response.setRecommendations(recommendations);
    }
}