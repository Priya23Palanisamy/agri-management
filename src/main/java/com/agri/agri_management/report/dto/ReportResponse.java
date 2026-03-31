package com.agri.agri_management.report.dto;
import java.util.List;
import java.util.Map;
public class ReportResponse {
    private String reportType;
    private String generatedAt;
    private int totalStock;
    private double totalStockValue;
    private int totalTransactions;
    private double totalRevenue;
    private List<String> fastMovingItems;
    private List<String> slowMovingItems;
    private List<String> stockIssues;
    private Map<String, Integer> barChartData;
    private Map<String, Double> lineChartData;
    private Map<String, Integer> pieChartData;
    private List<String> recommendations;
    public String getReportType() { return reportType; }
    public void setReportType(String r) { this.reportType = r; }
    public String getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(String g) { this.generatedAt = g; }
    public int getTotalStock() { return totalStock; }
    public void setTotalStock(int t) { this.totalStock = t; }
    public double getTotalStockValue() { return totalStockValue; }
    public void setTotalStockValue(double t) { this.totalStockValue = t; }
    public int getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(int t) { this.totalTransactions = t; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double t) { this.totalRevenue = t; }
    public List<String> getFastMovingItems() { return fastMovingItems; }
    public void setFastMovingItems(List<String> f) { this.fastMovingItems = f; }
    public List<String> getSlowMovingItems() { return slowMovingItems; }
    public void setSlowMovingItems(List<String> s) { this.slowMovingItems = s; }
    public List<String> getStockIssues() { return stockIssues; }
    public void setStockIssues(List<String> s) { this.stockIssues = s; }
    public Map<String, Integer> getBarChartData() { return barChartData; }
    public void setBarChartData(Map<String, Integer> b) { this.barChartData = b; }
    public Map<String, Double> getLineChartData() { return lineChartData; }
    public void setLineChartData(Map<String, Double> l) { this.lineChartData = l; }
    public Map<String, Integer> getPieChartData() { return pieChartData; }
    public void setPieChartData(Map<String, Integer> p) { this.pieChartData = p; }
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> r) { this.recommendations = r; }
}