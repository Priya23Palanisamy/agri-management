package com.agri.agri_management.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    // ✅ Only return HTML page (NOT API)
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

    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }
}