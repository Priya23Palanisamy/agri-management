package com.agri.agri_management.report.service;
import com.agri.agri_management.report.dto.ReportResponse;
import org.springframework.stereotype.Service;
import java.io.*;
@Service
public class ExportService {
    public byte[] exportToCsv(ReportResponse report) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter w = new PrintWriter(out);
        w.println("AGRI MANAGEMENT REPORT");
        w.println("Report Type," + report.getReportType());
        w.println("Generated At," + report.getGeneratedAt());
        w.println("Total Stock," + report.getTotalStock());
        w.println("Total Stock Value," + report.getTotalStockValue());
        w.println("Total Transactions," + report.getTotalTransactions());
        w.println("Total Revenue," + report.getTotalRevenue());
        w.println("RECOMMENDATIONS");
        if (report.getRecommendations() != null) report.getRecommendations().forEach(w::println);
        w.flush();
        return out.toByteArray();
    }
    public byte[] exportToPdf(ReportResponse report) {
        StringBuilder sb = new StringBuilder();
        sb.append("AGRI MANAGEMENT REPORT\n\n");
        sb.append("Report Type: ").append(report.getReportType()).append("\n");
        sb.append("Generated At: ").append(report.getGeneratedAt()).append("\n");
        sb.append("Total Stock: ").append(report.getTotalStock()).append("\n");
        sb.append("Total Revenue: ").append(report.getTotalRevenue()).append("\n\n");
        sb.append("Recommendations:\n");
        if (report.getRecommendations() != null) report.getRecommendations().forEach(r -> sb.append("- ").append(r).append("\n"));
        return sb.toString().getBytes();
    }
}