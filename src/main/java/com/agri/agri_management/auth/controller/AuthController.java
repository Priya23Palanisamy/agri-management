package com.agri.agri_management.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.agri.agri_management.auth.dto.AuthResponse;
import org.springframework.web.bind.annotation.*;

import com.agri.agri_management.auth.entity.*;
import com.agri.agri_management.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/farmer/register")
    public String registerFarmer(@RequestBody FarmerRequest req) {
        return authService.registerFarmer(req);
    }

    @PostMapping("/buyer/register")
    public String registerBuyer(@RequestBody User user) {
    	System.out.println("NAME FROM FRONTEND: " + user.getName()); // DEBUG
        return authService.registerBuyer(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        try {
            AuthResponse response = authService.login(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        return authService.sendResetOtp(email);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> data) {

        return authService.resetPassword(
                data.get("email"),
                data.get("otp"),
                data.get("newPassword")
        );
    }
}