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
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, out);

            document.open();

            document.add(new com.itextpdf.text.Paragraph("AGRI MANAGEMENT REPORT"));
            document.add(new com.itextpdf.text.Paragraph(" "));
            document.add(new com.itextpdf.text.Paragraph("Report Type: " + report.getReportType()));
            document.add(new com.itextpdf.text.Paragraph("Generated At: " + report.getGeneratedAt()));
            document.add(new com.itextpdf.text.Paragraph("Total Stock: " + report.getTotalStock()));
            document.add(new com.itextpdf.text.Paragraph("Total Revenue: " + report.getTotalRevenue()));

            document.add(new com.itextpdf.text.Paragraph(" "));
            document.add(new com.itextpdf.text.Paragraph("Recommendations:"));

            if (report.getRecommendations() != null) {
                for (String r : report.getRecommendations()) {
                    document.add(new com.itextpdf.text.Paragraph("- " + r));
                }
            }

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}