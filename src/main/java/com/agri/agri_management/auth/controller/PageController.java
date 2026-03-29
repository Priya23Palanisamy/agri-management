package com.agri.agri_management.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/farmer")
    public String farmer() {
        return "farmer";
    }

    @GetMapping("/buyer")
    public String buyer() {
        return "buyer";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/farmer-dashboard")
    public String farmerDashboard() {
        return "farmer-dashboard";
    }

    @GetMapping("/buyer-dashboard")
    public String buyerDashboard() {
        return "buyer-dashboard";
    }

    // ✅ FIXED HERE
    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/transaction-history")
    public String transactionHistory() {
        return "transaction-history";
    }

    @GetMapping("/admin-transactions")
    public String adminTransactions() {
        return "admin-transactions";
    }
}