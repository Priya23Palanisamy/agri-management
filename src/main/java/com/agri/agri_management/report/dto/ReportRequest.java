package com.agri.agri_management.report.dto;
import java.time.LocalDate;
public class ReportRequest {
    private String reportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String category;
    private String transactionType;
    public String getReportType() { return reportType; }
    public void setReportType(String r) { this.reportType = r; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate d) { this.startDate = d; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate d) { this.endDate = d; }
    public String getCategory() { return category; }
    public void setCategory(String c) { this.category = c; }
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String t) { this.transactionType = t; }
}